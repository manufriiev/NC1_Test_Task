package com.example.nc1_test_task;

import com.example.nc1_test_task.services.NewsParsingService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledNewsCheck {
    private final NewsParsingService newsParsingService;

    public ScheduledNewsCheck(NewsParsingService newsParsingService) {
        this.newsParsingService = newsParsingService;
    }

    @Scheduled(cron = "0 */20 * * * *")
    @PostConstruct
    public void scanForNews() {
        System.out.println("News check started");
        newsParsingService.fetchAndParseNews();
    }
}
