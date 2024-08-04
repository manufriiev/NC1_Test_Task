package com.example.nc1_test_task.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;


import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter
@Setter
public class NewsStory { //main entity class, creates a db for us and is very good and great

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Unique
    private String headline;
    @Column(length = 1024)
    private String description;
    private LocalDateTime publicationTime;
    private String imageUrl;
    private String link;

    public NewsStory() {
    }

    public NewsStory(String headline, String description, LocalDateTime publicationTime) {
        this.headline = headline;
        this.description = description;
        this.publicationTime = publicationTime;
    }

    public NewsStory(String headline, String description, LocalDateTime publicationTime, String imageUrl, String link) {
        this.headline = headline;
        this.description = description;
        this.publicationTime = publicationTime;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    @Override
    public String toString() {
        return headline + "\n" + description + "\n" + publicationTime + "\n" + "\n";
    }
}