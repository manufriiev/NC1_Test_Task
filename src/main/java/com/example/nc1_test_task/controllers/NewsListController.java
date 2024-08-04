package com.example.nc1_test_task.controllers;

import com.example.nc1_test_task.entity.NewsStory;
import com.example.nc1_test_task.services.NewsService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsListController { //handling news list
    private final NewsService newsService;
    private final ApplicationContext applicationContext;
    @FXML
    private Label newsCountLabel;
    @FXML
    private ListView<NewsStory> newsListView;
    private List<NewsStory> allNews;
    private Stage newsDetailStage;

    public NewsListController(NewsService newsService, ApplicationContext applicationContext) {
        this.newsService = newsService;
        this.applicationContext = applicationContext;
    }

    @FXML
    private void initialize() {
        allNews = newsService.getAllNews(); //getting all news from news service
        allNews.sort((news1, news2) -> news2.getPublicationTime().compareTo(news1.getPublicationTime()));
        newsListView.getItems().addAll(allNews); //loading them to view
        newsCountLabel.setText("Today - " + allNews.size());

        newsListView.setCellFactory(new Callback<ListView<NewsStory>, ListCell<NewsStory>>() {
            @Override
            public ListCell<NewsStory> call(ListView<NewsStory> param) {
                return new ListCell<NewsStory>() {
                    @Override
                    protected void updateItem(NewsStory item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            VBox vbox = new VBox(5);
                            Label headlineLabel = new Label(item.getHeadline());
                            Label timeLabel = new Label(item.getPublicationTime().format(DateTimeFormatter.ofPattern("HH-mm")));

                            vbox.getChildren().addAll(timeLabel, headlineLabel);
                            setGraphic(vbox);

                            setOnMouseClicked(event -> {
                                showNewsDetail(item);
                            });
                        }
                    }
                };
            }
        });
    }
    private void showNewsDetail(NewsStory newsStory) {
        if (newsDetailStage != null && newsDetailStage.isShowing()) {
            newsDetailStage.close(); //making sure only one detail window can be open at once
        }
        try { //opening individual news window on correct news article with correct news list
            File fxmlFile = new File("src/main/resources/fxml/news-article.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            loader.setControllerFactory(applicationContext::getBean);

            Parent root = loader.load();

            NewsArticleController controller = loader.getController(); //setting detail window data and preparing it accordingly
            controller.setCurrentIndex(allNews.indexOf(newsStory));
            controller.setNewsItems((ArrayList<NewsStory>) allNews);
            controller.updateNewsDisplay();


            Platform.runLater(() -> {
                newsDetailStage = new Stage();
                newsDetailStage.setScene(new Scene(root,1000,400));
                newsDetailStage.setTitle("News Article");
                newsDetailStage.show();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void filterAllNews() {
        filterNewsByTimeRange(LocalTime.of(0, 0), LocalTime.of(23, 59));
        newsCountLabel.setText("Today - " + newsListView.getItems().size());
    }

    @FXML
    private void filterMorningNews() {
        filterNewsByTimeRange(LocalTime.of(0, 0), LocalTime.of(11, 59));
        newsCountLabel.setText("Morning - " + newsListView.getItems().size());
    }

    @FXML
    private void filterDayNews() {
        filterNewsByTimeRange(LocalTime.of(12, 0), LocalTime.of(17, 59));
        newsCountLabel.setText("Afternoon - " + newsListView.getItems().size());
    }

    @FXML
    private void filterEveningNews() {
        filterNewsByTimeRange(LocalTime.of(18, 0), LocalTime.of(23, 59));
        newsCountLabel.setText("Evening - " + newsListView.getItems().size());
    }

    private void filterNewsByTimeRange(LocalTime startTime, LocalTime endTime) {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = LocalDateTime.of(today, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(today, endTime);

        List<NewsStory> filteredNews = newsService.getNewsStoryByTimeRange(startDateTime, endDateTime);
        allNews = new ArrayList<>(filteredNews);
        allNews.sort((news1, news2) -> news2.getPublicationTime().compareTo(news1.getPublicationTime()));
        newsListView.getItems().setAll(allNews);
    }

}
