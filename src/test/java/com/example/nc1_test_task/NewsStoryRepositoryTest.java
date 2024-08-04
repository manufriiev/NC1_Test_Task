//package com.example.nc1_test_task;
//
//import com.example.nc1_test_task.entity.NewsStory;
//import com.example.nc1_test_task.repository.NewsRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@ActiveProfiles("Ptest")  // Use the 'test' profile for this test
//public class NewsStoryRepositoryTest {
//
//    @Autowired
//    private NewsRepository newsStoryRepository;
//
//    @Test
//    public void testSaveAndFindNewsStory() {
//        NewsStory newsStory = new NewsStory();
//        newsStory.setHeadline("Breaking News");
//        newsStory.setDescription("This is a test description.");
//        newsStory.setLink("http://example.com");
//
//        newsStoryRepository.save(newsStory);
//
//        Iterable<NewsStory> newsStories = newsStoryRepository.findAll();
//        assertThat(newsStories).hasSize(1);
//
//        NewsStory foundNewsStory = newsStories.iterator().next();
//        assertThat(foundNewsStory.getHeadline()).isEqualTo("Breaking News");
//        assertThat(foundNewsStory.getDescription()).isEqualTo("This is a test description.");
//        assertThat(foundNewsStory.getLink()).isEqualTo("http://example.com");
//    }
//}