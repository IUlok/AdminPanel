package com.example.adminpanel;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends GridPane {
    private BorderPane rightPanel = new BorderPane();
    private BorderPane leftPanel = new BorderPane();
    Stage dialogStage = new Stage();

    public Client() {
        // Конфигурация панели меню
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

        newUserButtonPane.setOnMouseClicked(e -> {
            getChildren().remove(rightPanel);
            rightPanel = new BorderPane();
            onNewUserButtonClick(rightPanel);
            newUserButtonPane.getStyleClass().removeAll("newUserButton", "newUserButton1");
            userControlPane.getStyleClass().removeAll("userControl", "userControl1");
            groupControlPane.getStyleClass().removeAll("groupControl", "groupControl1");
            newUserButtonPane.getStyleClass().add("newUserButton1");
            userControlPane.getStyleClass().add("userControl");
            groupControlPane.getStyleClass().add("groupControl");
        });

        userControlPane.setOnMouseClicked(e -> {
            getChildren().remove(rightPanel);
            rightPanel = new BorderPane();
            onUserControlButtonClick(rightPanel);
            newUserButtonPane.getStyleClass().removeAll("newUserButton", "newUserButton1");
            userControlPane.getStyleClass().removeAll("userControl", "userControl1");
            groupControlPane.getStyleClass().removeAll("groupControl", "groupControl1");
            newUserButtonPane.getStyleClass().add("newUserButton");
            userControlPane.getStyleClass().add("userControl1");
            groupControlPane.getStyleClass().add("groupControl");
        });

        groupControlPane.setOnMouseClicked(e -> {
            getChildren().remove(rightPanel);
            rightPanel = new BorderPane();
            onGroupControlButtonClick(rightPanel);
            newUserButtonPane.getStyleClass().removeAll("newUserButton", "newUserButton1");
            userControlPane.getStyleClass().removeAll("userControl", "userControl1");
            groupControlPane.getStyleClass().removeAll("groupControl", "groupControl1");
            newUserButtonPane.getStyleClass().add("newUserButton");
            userControlPane.getStyleClass().add("userControl");
            groupControlPane.getStyleClass().add("groupControl1");
        });

        leftPanel.setCenter(toolbarMenu);
    }

    protected void onNewUserButtonClick(BorderPane rightPanel) {
        rightPanel.setPrefHeight(768);
        rightPanel.setPrefWidth(880);
        FlowPane ChoiceBox = new FlowPane();
        ChoiceBox.setAlignment(Pos.CENTER);
        ChoiceBox.setPadding(new Insets(50,0,0,0));
        FlowPane ChoicePane = new FlowPane();
        ChoicePane.setPrefSize(460,60);
        ChoicePane.setAlignment(Pos.CENTER);
        ChoicePane.getStyleClass().add("choicePane");
        ChoicePane.setHgap(30);
        RadioButton studentChoice = new RadioButton("Студент");
        RadioButton prepodChoice = new RadioButton("Преподаватель");
        prepodChoice.setPrefSize(200,40);
        prepodChoice.setAlignment(Pos.CENTER);
        prepodChoice.getStyleClass().remove("radio-button");
        studentChoice.setPrefSize(200,40);
        studentChoice.setAlignment(Pos.CENTER);
        studentChoice.getStyleClass().remove("radio-button");
        ToggleGroup choiceButtons = new ToggleGroup();
        studentChoice.getStyleClass().add("studentChoice");
        prepodChoice.getStyleClass().add("prepodChoice");
        studentChoice.setToggleGroup(choiceButtons);
        prepodChoice.setToggleGroup(choiceButtons);
        ChoicePane.getChildren().addAll(studentChoice, prepodChoice);
        ChoiceBox.getChildren().add(ChoicePane);
        rightPanel.setTop(ChoiceBox);
        add(rightPanel, 1, 0);

        prepodChoice.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowSelected) {
                GridPane inputForm;
                if(isNowSelected) {
                    // Выбор Преподаватель: Изменение стилей
                    studentChoice.getStyleClass().removeAll("studentChoice", "studentChoice1");
                    prepodChoice.getStyleClass().removeAll("prepodChoice", "prepodChoice1");
                    prepodChoice.getStyleClass().add("prepodChoice1");
                    studentChoice.getStyleClass().add("studentChoice");

                    // Форма заполнения преподавателя
                    inputForm = new GridPane();
                    inputForm = new GridPane();
                    inputForm.setAlignment(Pos.CENTER);
                    inputForm.setHgap(30);
                    inputForm.setVgap(30);
                    inputForm.setPadding(new Insets(25, 25, 25, 25));

                    TextField firstName = new TextField();
                    TextField lastName = new TextField();
                    TextField thirdName = new TextField();
                    DatePicker startingUsingAccountDate = new DatePicker();
                    DatePicker endingUsingAccountDate = new DatePicker();

                    firstName.setPromptText("Имя");
                    lastName.setPromptText("Фамилия");
                    thirdName.setPromptText("Отчество");
                    startingUsingAccountDate.setValue(LocalDate.now());
                    endingUsingAccountDate.setValue(LocalDate.now());

                    ObservableList<String> department = FXCollections.observableArrayList(
                            "Автоматика и телемеханика на железных дорогах", "Высшая математика",
                            "Информационные и вычислительные системы", "Информатика и информационная безопасность",
                            "Электротехника и теплоэнергетика", "Электрическая связь", "Электроснабжение железных дорог",
                            "Архитектурно-строительное проектирование", "Водоснабжение, водоотведение и гидравлика",
                            "Инженерная химия и естествознание", "Основания и фундаменты");
                    ComboBox<String> departmentBox = new ComboBox<String>(department);

                    ObservableList<String> position = FXCollections.observableArrayList(
                            "Профессор", "Старший преподаватель", "Доцент", "Ассистент", "Заведующий кафедрой",
                            "Декан", "Проректор", "Ректор");
                    ComboBox<String> positionBox = new ComboBox<String>(position);

                    ObservableList<String> degree = FXCollections.observableArrayList("Кандидат наук", "Доктор наук");
                    ComboBox<String> degreeBox = new ComboBox<String>(degree);

                    inputForm.add(firstName, 1, 1);
                    inputForm.add(lastName, 1, 2);
                    inputForm.add(thirdName, 1, 3);
                    inputForm.add(startingUsingAccountDate, 1, 4);
                    inputForm.add(endingUsingAccountDate, 1, 5);
                    inputForm.add(departmentBox, 1, 6);
                    inputForm.add(positionBox, 1, 7);
                    inputForm.add(degreeBox, 1, 8);

                    rightPanel.setCenter(inputForm);

                    Button sendButton = new Button("Создать");
                    FlowPane sendPane = new FlowPane(sendButton);
                    sendPane.setAlignment(Pos.CENTER);
                    sendPane.setPadding(new Insets(0, 0, 50, 0));
                    rightPanel.setBottom(sendPane);

                    sendButton.setOnAction(e -> {
                        // Событие для кнопки создать
                    });
                }
            }
        });

        studentChoice.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowSelected) {
                GridPane inputForm;
                if(isNowSelected) {
                    // Выбор студент: Изменение стилей
                    studentChoice.getStyleClass().removeAll("studentChoice", "studentChoice1");
                    prepodChoice.getStyleClass().removeAll("prepodChoice", "prepodChoice1");
                    studentChoice.getStyleClass().add("studentChoice1");
                    prepodChoice.getStyleClass().add("prepodChoice");

                    // Форма заполнения студента
                    inputForm = new GridPane();
                    inputForm.setAlignment(Pos.CENTER);
                    inputForm.setHgap(30);
                    inputForm.setVgap(30);
                    inputForm.setPadding(new Insets(25, 25, 25, 25));

                    TextField firstName = new TextField();
                    TextField lastName = new TextField();
                    TextField thirdName = new TextField();
                    TextField group = new TextField();
                    DatePicker startingUsingAccountDate = new DatePicker();
                    DatePicker endingUsingAccountDate = new DatePicker();

                    firstName.setPromptText("Имя");
                    lastName.setPromptText("Фамилия");
                    thirdName.setPromptText("Отчество");
                    group.setPromptText("Номер группы");
                    startingUsingAccountDate.setValue(LocalDate.now());
                    endingUsingAccountDate.setValue(LocalDate.now());

                    ObservableList<String> compensation = FXCollections.observableArrayList("Бюджет", "Контракт", "Целевое");
                    ComboBox<String> compensationBox = new ComboBox<String>(compensation);

                    inputForm.add(firstName, 1, 1);
                    inputForm.add(lastName, 1, 2);
                    inputForm.add(thirdName, 1, 3);
                    inputForm.add(group, 1, 4);
                    inputForm.add(startingUsingAccountDate, 1, 5);
                    inputForm.add(endingUsingAccountDate, 1, 6);
                    inputForm.add(compensationBox, 1, 7);

                    rightPanel.setCenter(inputForm);

                    Button sendButton = new Button("Создать");
                    FlowPane sendPane = new FlowPane(sendButton);
                    sendPane.setAlignment(Pos.CENTER);
                    sendPane.setPadding(new Insets(0, 0, 50, 0));
                    rightPanel.setBottom(sendPane);

                    sendButton.setOnAction(e -> {
                        // Событие для кнопки создать
                    });

                }
            }
        });
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
