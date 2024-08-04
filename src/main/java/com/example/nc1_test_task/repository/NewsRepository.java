package com.example.nc1_test_task.repository;

import com.example.nc1_test_task.entity.NewsStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsStory, Long> {
    @Override
    List<NewsStory> findAll();

    NewsStory findByHeadline(String title);

    List<NewsStory> findByHeadlineContainsOrDescriptionContains(String title, String description);

    List<NewsStory> findByPublicationTimeBetween(LocalDateTime start, LocalDateTime end);

    NewsStory findByPublicationTime (LocalDateTime publicationTime);

    List<NewsStory> findByPublicationTimeBefore (LocalDateTime today);
}
