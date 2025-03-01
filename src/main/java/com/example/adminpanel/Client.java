package com.example.adminpanel;

import com.example.adminpanel.view.GroupsPane;
import com.example.adminpanel.view.NewUserPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Client extends GridPane {
    private Pane rightPanel = new BorderPane();

    public Client() {
        // Создание и конфигурация панели меню
        BorderPane leftPanel = new BorderPane();
        leftPanel.setPrefHeight(768);
        leftPanel.setPrefWidth(400);
        leftPanel.setStyle("-fx-background-color: #7a7a7a;");
        add(leftPanel, 0, 0);

        // Конфигурация панели параметров
        rightPanel.setPrefHeight(768);
        rightPanel.setPrefWidth(880);
        Pane imageStart = new Pane();
        imageStart.getStyleClass().add("imageStart");
        ((BorderPane) rightPanel).setCenter(imageStart);
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
        FlowPane newUserButtonPane = new FlowPane();
        FlowPane userControlPane = new FlowPane();
        FlowPane groupControlPane = new FlowPane();
        VBox toolbarMenu = new VBox();

        // Кнопка "Новый пользователь"
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

        // Кнопка "Пользователи"
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

        // Кнопка "Группы"
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

        // Группа кнопок
        toolbarMenu.setAlignment(Pos.CENTER);
        toolbarMenu.getChildren().addAll(newUserButtonPane, userControlPane, groupControlPane);

        // Обработка нажатия на кнопку меню "Новый пользователь"
        newUserButtonPane.setOnMouseClicked(e -> {
            getChildren().remove(rightPanel);
            newUserButtonPane.getStyleClass().removeAll("newUserButton", "newUserButton1");
            userControlPane.getStyleClass().removeAll("userControl", "userControl1");
            groupControlPane.getStyleClass().removeAll("groupControl", "groupControl1");
            newUserButtonPane.getStyleClass().add("newUserButton1");
            userControlPane.getStyleClass().add("userControl");
            groupControlPane.getStyleClass().add("groupControl");
            // В правую панель устанавливается панель для создания пользователя
            rightPanel = new NewUserPane();
            add(rightPanel, 1, 0);
        });

        // Обработка нажатия на кнопку меню "Пользователи"
        userControlPane.setOnMouseClicked(e -> {
            getChildren().remove(rightPanel);
            newUserButtonPane.getStyleClass().removeAll("newUserButton", "newUserButton1");
            userControlPane.getStyleClass().removeAll("userControl", "userControl1");
            groupControlPane.getStyleClass().removeAll("groupControl", "groupControl1");
            newUserButtonPane.getStyleClass().add("newUserButton");
            userControlPane.getStyleClass().add("userControl1");
            groupControlPane.getStyleClass().add("groupControl");
            // В правую панель устанавливается панель для работы с пользователями
            rightPanel = new NewUserPane();
            add(rightPanel, 1, 0);
        });

        // Обработка нажатия на кнопку меню "Группы"
        groupControlPane.setOnMouseClicked(e -> {
            getChildren().remove(rightPanel);
            newUserButtonPane.getStyleClass().removeAll("newUserButton", "newUserButton1");
            userControlPane.getStyleClass().removeAll("userControl", "userControl1");
            groupControlPane.getStyleClass().removeAll("groupControl", "groupControl1");
            newUserButtonPane.getStyleClass().add("newUserButton");
            userControlPane.getStyleClass().add("userControl");
            groupControlPane.getStyleClass().add("groupControl1");
            // В правую панель устанавливается панель для работы с группами
            rightPanel = new GroupsPane();
            add(rightPanel, 1, 0);
        });

        leftPanel.setCenter(toolbarMenu);
    }
}
