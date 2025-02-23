package com.example.adminpanel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Client extends GridPane {
    private BorderPane rightPanel = new BorderPane();

    public Client() {
        // Конфигурация панели меню
        BorderPane leftPanel = new BorderPane();
        leftPanel.setPrefHeight(768);
        leftPanel.setPrefWidth(400);
        leftPanel.setStyle("-fx-background-color: #7a7a7a;");
        add(leftPanel, 0, 0);

        // Концигурация панели параметров
        rightPanel.setPrefHeight(768);
        rightPanel.setPrefWidth(880);
        Pane imageStart = new Pane();
        imageStart.getStyleClass().add("imageStart");
        rightPanel.setCenter(imageStart);
        add(rightPanel, 1, 0);

        // Логотип
        FlowPane logoPane = new FlowPane();
        logoPane.setAlignment(Pos.CENTER);
        logoPane.setPadding(new Insets(30,0,30,0));
        Label logo = new Label();
        logo.setMinWidth(375);
        logo.setMinHeight(100);
        logo.getStyleClass().add("logo");
        logoPane.getChildren().add(logo);
        leftPanel.setTop(logoPane);

        // Кнопка "Выйти"
        FlowPane exitButtonPane = new FlowPane();
        exitButtonPane.setPrefSize(0,100);
        exitButtonPane.setAlignment(Pos.CENTER);
        exitButtonPane.getStyleClass().add("exitButton");
        Label exitIcon = new Label();
        exitIcon.getStyleClass().add("exitIcon");
        exitIcon.setMinWidth(50);
        exitIcon.setMinHeight(50);
        Label exitButton = new Label("Выйти");
        exitButton.getStyleClass().add("exitButtonText");
        exitButtonPane.getChildren().add(exitIcon);
        exitButtonPane.getChildren().add(exitButton);
        exitButtonPane.setOnMouseClicked(e -> System.exit(0));
        leftPanel.setBottom(exitButtonPane);

        // Меню кнопок

        // Кнопка "Новый пользователь"
        FlowPane newUserButtonPane = new FlowPane();
        newUserButtonPane.setPadding(new Insets(0,0,0,30));
        newUserButtonPane.setPrefSize(0,50);
        newUserButtonPane.getStyleClass().add("newUserButton");
        Label newUserIcon = new Label();
        newUserIcon.getStyleClass().add("newUserIcon");
        newUserIcon.setMinWidth(50);
        newUserIcon.setMinHeight(50);
        Label newUserButton = new Label("Новый пользователь");
        newUserButton.getStyleClass().add("newUserButtonText");
        newUserButtonPane.getChildren().add(newUserIcon);
        newUserButtonPane.getChildren().add(newUserButton);
        newUserButtonPane.setOnMouseClicked(e -> {getChildren().remove(rightPanel); rightPanel = new BorderPane(); onNewUserButtonClick(rightPanel);});

        // Кнопка "Пользователи"
        FlowPane userControlPane = new FlowPane();
        userControlPane.setPadding(new Insets(0,0,0,30));
        userControlPane.setPrefSize(0,50);
        userControlPane.getStyleClass().add("userControl");
        Label userControlIcon = new Label();
        userControlIcon.getStyleClass().add("userControlIcon");
        userControlIcon.setMinWidth(50);
        userControlIcon.setMinHeight(50);
        Label userControlButton = new Label("Пользователи");
        userControlButton.getStyleClass().add("userControlButtonText");
        userControlPane.getChildren().add(userControlIcon);
        userControlPane.getChildren().add(userControlButton);
        userControlPane.setOnMouseClicked(e -> {getChildren().remove(rightPanel); rightPanel = new BorderPane(); onUserControlButtonClick(rightPanel);});

        // Кнопка "Группы"
        FlowPane groupControlPane = new FlowPane();
        groupControlPane.setPadding(new Insets(0,0,0,30));
        groupControlPane.setPrefSize(0,50);
        groupControlPane.getStyleClass().add("groupControl");
        Label groupControlIcon = new Label();
        groupControlIcon.getStyleClass().add("groupControlIcon");
        groupControlIcon.setMinWidth(50);
        groupControlIcon.setMinHeight(50);
        Label groupControlButton = new Label("Группы");
        groupControlButton.getStyleClass().add("groupControlButtonText");
        groupControlPane.getChildren().add(groupControlIcon);
        groupControlPane.getChildren().add(groupControlButton);
        groupControlPane.setOnMouseClicked(e -> {getChildren().remove(rightPanel); rightPanel = new BorderPane(); onGroupControlButtonClick(rightPanel);});

        // Группа кнопок
        VBox toolbarMenu = new VBox();
        toolbarMenu.setAlignment(Pos.CENTER);
        toolbarMenu.getChildren().add(newUserButtonPane);
        toolbarMenu.getChildren().add(userControlPane);
        toolbarMenu.getChildren().add(groupControlPane);
        leftPanel.setCenter(toolbarMenu);
    }

    protected void onNewUserButtonClick(BorderPane rightPanel) {
        rightPanel.setPrefHeight(768);
        rightPanel.setPrefWidth(880);
        FlowPane ChoicePane = new FlowPane();
        ChoicePane.setAlignment(Pos.CENTER);
        ChoicePane.setPadding(new Insets(30,0,30,0));
        Label logo = new Label();
        logo.setMinWidth(375);
        logo.setMinHeight(100);
        logo.getStyleClass().add("logo");
        ChoicePane.getChildren().add(logo);
        rightPanel.setTop(ChoicePane);
        add(rightPanel, 1, 0);
    }

    protected void onUserControlButtonClick(BorderPane rightPanel) {
        rightPanel.setPrefHeight(768);
        rightPanel.setPrefWidth(880);
        Button gavno = new Button("hello2");
        rightPanel.setCenter(gavno);
        add(rightPanel, 1, 0);
    }

    protected void onGroupControlButtonClick(BorderPane rightPanel) {
        rightPanel.setPrefHeight(768);
        rightPanel.setPrefWidth(880);
        Button gavno = new Button("hello3");
        rightPanel.setCenter(gavno);
        add(rightPanel, 1, 0);
    }

}
