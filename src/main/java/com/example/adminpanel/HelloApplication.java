package com.example.adminpanel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane client = new Client();
        Scene scene = new Scene(client, 1280, 768);
        scene.getStylesheets().add((HelloApplication.class.getResource("assets/style.css")).toString());
        stage.setTitle("EduChat Admin");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}