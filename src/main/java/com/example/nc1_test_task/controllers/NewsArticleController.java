package com.example.nc1_test_task.controllers;

import com.example.nc1_test_task.entity.NewsStory;
import com.example.nc1_test_task.services.NewsService;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
@Setter
@NoArgsConstructor
public class NewsArticleController { //handling individual news detail
    @FXML
    private Label newsIndexLabel;
    @FXML
    private ImageView newsImage;
    @FXML
    private Label newsTime;
    @FXML
    private Label newsHeadline;
    @FXML
    private Label newsDescription;
    @FXML
    private Hyperlink newsLink;

    private int currentIndex;

    private NewsService newsService;
    private ArrayList<NewsStory> newsItems = new ArrayList<>();


    @Autowired
    public NewsArticleController(NewsService newsService) {
        this.newsService = newsService;
    }

    @FXML
    public void initialize() {
        updateNewsDisplay();
    }

    @FXML
    private void handlePreviousNews() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = newsItems.size() - 1;
        }
        updateNewsDisplay();
    }

    @FXML
    private void handleNextNews() {
        if (currentIndex < newsItems.size() - 1) {
            currentIndex++;
        } else {
            currentIndex = 0;
        }
        updateNewsDisplay();

    }

    public void updateNewsDisplay() {
        if (newsItems.isEmpty()) { //handling empty lists
            newsIndexLabel.setText("0");
            newsTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm, dd-MM-yy")));
            newsHeadline.setText("Breaking! No news for this time!");
            newsDescription.setText("It seems the time has not yet come.");
            newsImage.setImage(new Image("https://i.imgur.com/GlZeqPy.jpeg"));
            return;
        }
        newsIndexLabel.setText(currentIndex + 1 + "/" + newsItems.size()); //setting top label ex - 1/12
        NewsStory currentNews = newsItems.get(currentIndex);
        // Setting news info
        newsHeadline.setText(currentNews.getHeadline()); // Headline
        newsDescription.setText(currentNews.getDescription()); // Description
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yy"); // Getting formatted time and date
        newsTime.setText(currentNews.getPublicationTime().format(formatter)); // Setting time and date
        if (currentNews.getImageUrl() != null && !currentNews.getImageUrl().isEmpty()) { // Set news image
            Image image = new Image(currentNews.getImageUrl(), true);
            newsImage.setImage(image);
        } else {
            newsImage.setImage(new Image("https://i.imgur.com/GlZeqPy.jpeg"));  // Bad news, placeholder image
        }

//        if (currentNews.getLink() != null) { // Code for setting link, but can't figure out how to open browser
//            newsLink.setText("See Full");
//            newsLink.setOnAction(event -> {
//                // Open link in browser
//                try {
//                    Desktop.getDesktop().browse(new URI(currentNews.getLink()));
//                } catch (IOException | URISyntaxException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        } else {
//            newsLink.setText("No link it seems :{");
//            newsLink.setOnAction(event -> {
//                // Open link in browser
//                try {
//                    Desktop.getDesktop().browse(URI.create("https://i.imgflip.com/6kkyau.jpg"));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
    }
}

