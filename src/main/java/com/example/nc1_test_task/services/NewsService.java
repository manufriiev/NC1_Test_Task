package com.example.nc1_test_task.services;

import com.example.nc1_test_task.entity.NewsStory;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {
    List<NewsStory> getAllNews();

    Boolean saveNewsStory(NewsStory newsStory);

    Boolean deleteNewsStory(NewsStory newsStory);

    Boolean updateNewsStory(NewsStory newsStory);

    NewsStory getNewsStory(Long id);

    NewsStory getNewsStoryByHeadline(String headline);

    List<NewsStory> getNewsStoryByTimeRange(LocalDateTime start, LocalDateTime end);

    Boolean clearOldNews();
}
