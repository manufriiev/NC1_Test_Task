package com.example.nc1_test_task.services;

import com.example.nc1_test_task.entity.NewsStory;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NewsParsingService {

    private final NewsService newsService;

    @Autowired
    public NewsParsingService(NewsService newsService) {
        this.newsService = newsService;
    }

    public void fetchAndParseNews() {
        String url = "https://www.euronews.com/just-in"; //our news website

        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.BEST_SUPPORTED);
        driver.get(url);
        try {
            List<WebElement> loadMoreButtons = driver.findElements(By.cssSelector("button#justin-load-more-button"));
            if (!loadMoreButtons.isEmpty()) {
                WebElement loadMoreButton = loadMoreButtons.getFirst();
                if (loadMoreButton.isDisplayed() && loadMoreButton.isEnabled()) {
                    loadMoreButton.click(); //trying to make it load more news, failing unfortunately, so we're stuck with base 15
                    Thread.sleep(2000);
                }
        }
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);
            Elements newsElements = doc.select("li.js-timeline-item"); //getting a list of timeline items
            System.out.println(newsElements.size());
            for (Element newsElement : newsElements) {
                String title = newsElement.select("h3.m-object__title").text(); //getting all the data
                String content = newsElement.select("a.m-object__description__link").text();
                String timeString = newsElement.select("p.c-item-time").text();
                String dateString = newsElement.select("p.c-item-date").text();
                String image = newsElement.select("img.m-img").attr("src");
                String link = newsElement.select("a.c-timeline-items__link").attr("href");

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                // Parsing date and time strings
                LocalDate date = LocalDate.parse(dateString, dateFormatter);
                if (date.isBefore(LocalDate.now())) { // Not saving old news, exiting cuz everything after that is old too
                    break;
                }
                LocalTime time = LocalTime.parse(timeString, timeFormatter);
                LocalDateTime dateTime = LocalDateTime.of(date, time);

                NewsStory newNews = new NewsStory(title,content,dateTime,image,link);

                // Checking if we've reached the line at which the news repeat
                if (isDuplicate(newNews)) {
                    System.out.println("!Already exists! Not going further:" + newNews.getHeadline());
                    break;
                }
                newsService.saveNewsStory(newNews);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
        newsService.clearOldNews();
    }

    private boolean isDuplicate(NewsStory news) {
        return newsService.getNewsStoryByHeadline(news.getHeadline()) != null;
    }

}
