package com.example.nc1_test_task;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
@SpringBootApplication

    public class NewsAppMain extends Application {
    private ConfigurableApplicationContext springContext;
    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(NewsAppMain.class).run();
    }
    @Override
    public void start(Stage primaryStage) throws Exception { //opening main window
        File fxmlFile = new File("src/main/resources/fxml/news-list.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        loader.setControllerFactory(springContext::getBean);
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("News Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void stop() { //making the project go past window closing gathering data
       // springContext.close();
    }
}
