package com.example.nc1_test_task.services;

import com.example.nc1_test_task.entity.NewsStory;
import com.example.nc1_test_task.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsStory> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public Boolean saveNewsStory(NewsStory newsStory) {
        try {
            newsRepository.save(newsStory);
            return true;
        } catch (Exception e) {
            System.out.println("Bad news :( - " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteNewsStory(NewsStory newsStory) {
        try {
            newsRepository.delete(newsStory);
            return true;
        } catch (Exception e) {
            System.out.println("Bad news :( - " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean updateNewsStory(NewsStory newsStory) {
        return false;
    }

    @Override
    public NewsStory getNewsStory(Long id) {
        Optional<NewsStory> newsStory = newsRepository.findById(id);
        return newsStory.orElse(null);
    }

    @Override
    public NewsStory getNewsStoryByHeadline(String headline) {
        Optional<NewsStory> newsStory = Optional.ofNullable(newsRepository.findByHeadline(headline));
        return newsStory.orElse(null);
    }

    @Override
    public List<NewsStory> getNewsStoryByTimeRange(LocalDateTime start, LocalDateTime end) {
        return newsRepository.findByPublicationTimeBetween(start, end);
    }

    @Override
    public Boolean clearOldNews() {
        try {
            LocalDate today = LocalDate.now();
            LocalTime time = LocalTime.of(0,0);
            LocalDateTime dateTime = LocalDateTime.of(today, time);
            List<NewsStory> oldNews = newsRepository.findByPublicationTimeBefore(dateTime);
            newsRepository.deleteAll(oldNews);
            System.out.println("News Deleted: " + oldNews.size());
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
