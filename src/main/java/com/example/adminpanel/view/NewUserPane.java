package com.example.adminpanel.view;

import com.example.adminpanel.entity.User;
import com.example.adminpanel.http.HttpUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Панель создания нового пользователя
public class NewUserPane extends BorderPane {

    private final HttpUtil httpUtil = new HttpUtil();

    // Создание элементов ввода
    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField patronymic = new TextField();

    private final DatePicker startingUsingAccountDate = new DatePicker();
    private final DatePicker endingUsingAccountDate = new DatePicker();

    // Элементы ввода преподавателя
    private ComboBox<String> degreeBox;
    private ComboBox<String> departmentBox;
    private ComboBox<String> positionBox;

    // Элементы ввода студента
    private ComboBox<String> compensationBox;
    private ComboBox<String> groupSelect;

    public NewUserPane() {
        setPrefSize(768, 880);

        // Переключение выбора типа пользователя
        FlowPane choiceBox = new FlowPane();
        choiceBox.setAlignment(Pos.CENTER);
        choiceBox.setPadding(new Insets(50, 0, 0, 0));
        FlowPane choicePane = new FlowPane();
        choicePane.setPrefSize(460, 60);
        choicePane.setAlignment(Pos.CENTER);
        choicePane.getStyleClass().add("choicePane");
        choicePane.setHgap(30);

        RadioButton studentChoice = new RadioButton("Студент");
        RadioButton prepodChoice = new RadioButton("Преподаватель");
        prepodChoice.setPrefSize(200, 40);
        prepodChoice.setAlignment(Pos.CENTER);
        prepodChoice.getStyleClass().remove("radio-button");
        studentChoice.setPrefSize(200, 40);
        studentChoice.setAlignment(Pos.CENTER);
        studentChoice.getStyleClass().remove("radio-button");

        ToggleGroup choiceButtons = new ToggleGroup();
        studentChoice.getStyleClass().add("studentChoice");
        prepodChoice.getStyleClass().add("prepodChoice");
        studentChoice.setToggleGroup(choiceButtons);
        prepodChoice.setToggleGroup(choiceButtons);
        choicePane.getChildren().addAll(studentChoice, prepodChoice);
        choiceBox.getChildren().add(choicePane);
        setTop(choiceBox);
        // **************************************************************
        // Создание формы пользовательского ввода
        GridPane inputForm = new GridPane();
        inputForm.setAlignment(Pos.CENTER);
        inputForm.setHgap(30);
        inputForm.setVgap(30);
        inputForm.setPadding(new Insets(25, 25, 25, 25));

        firstName.setPromptText("Имя");
        lastName.setPromptText("Фамилия");
        patronymic.setPromptText("Отчество");
        startingUsingAccountDate.setValue(LocalDate.now());
        endingUsingAccountDate.setValue(LocalDate.now());

        Text errorInfo = new Text();
        errorInfo.setFill(Color.RED);

        // Добавление элементов на форму
        inputForm.add(lastName, 0, 0);
        inputForm.add(firstName, 0, 1);
        inputForm.add(patronymic, 0, 2);
        inputForm.add(startingUsingAccountDate, 0, 3);
        inputForm.add(endingUsingAccountDate, 0, 4);
        inputForm.add(errorInfo, 0, 8);

        // Добавление формы на панель
        setCenter(inputForm);

        prepodChoice.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowSelected) {

                if(isNowSelected) {
                    // Выбор Преподаватель: Изменение стилей
                    studentChoice.getStyleClass().removeAll("studentChoice", "studentChoice1");
                    prepodChoice.getStyleClass().removeAll("prepodChoice", "prepodChoice1");
                    prepodChoice.getStyleClass().add("prepodChoice1");
                    studentChoice.getStyleClass().add("studentChoice");

                    ObservableList<String> department = FXCollections.observableArrayList(
                            "Автоматика и телемеханика на железных дорогах", "Высшая математика",
                            "Информационные и вычислительные системы", "Информатика и информационная безопасность",
                            "Электротехника и теплоэнергетика", "Электрическая связь", "Электроснабжение железных дорог",
                            "Архитектурно-строительное проектирование", "Водоснабжение, водоотведение и гидравлика",
                            "Инженерная химия и естествознание", "Основания и фундаменты");
                    departmentBox = new ComboBox<>(department);

                    ObservableList<String> position = FXCollections.observableArrayList(
                            "Профессор", "Старший преподаватель", "Доцент", "Ассистент", "Заведующий кафедрой",
                            "Декан", "Проректор", "Ректор");
                    positionBox = new ComboBox<>(position);

                    ObservableList<String> degree = FXCollections.observableArrayList("Кандидат наук", "Доктор наук");
                    degreeBox = new ComboBox<>(degree);

                    inputForm.getChildren().removeAll(compensationBox, groupSelect);
                    inputForm.add(departmentBox, 0, 5);
                    inputForm.add(positionBox, 0, 6);
                    inputForm.add(degreeBox, 0, 7);

                    Button sendButton = new Button("Создать");
                    FlowPane sendPane = new FlowPane(sendButton);
                    sendPane.setAlignment(Pos.CENTER);
                    sendPane.setPadding(new Insets(0, 0, 50, 0));
                    setBottom(sendPane);

                    sendButton.setOnAction(e -> {
                        User newUser = new User();
                        newUser.setFirstName(firstName.getText());
                        newUser.setLastName(lastName.getText());
                        newUser.setPatronymic(patronymic.getText());
                        newUser.setEnabledFrom(Date.valueOf(startingUsingAccountDate.getValue()));
                        newUser.setEnabledUntil(Date.valueOf(endingUsingAccountDate.getValue()));
                        newUser.setRole("professor");
                        newUser.setDepartment(departmentBox.getValue());
                        newUser.setAcademicTitle(positionBox.getValue());
                        newUser.setAcademicDegree(degreeBox.getValue());

                        System.out.println(httpUtil.saveNewUser(newUser));
                    });
                }
            }
        });

        studentChoice.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowSelected) {
                if(isNowSelected) {
                    // Выбор студент: Изменение стилей
                    studentChoice.getStyleClass().removeAll("studentChoice", "studentChoice1");
                    prepodChoice.getStyleClass().removeAll("prepodChoice", "prepodChoice1");
                    studentChoice.getStyleClass().add("studentChoice1");
                    prepodChoice.getStyleClass().add("prepodChoice");

                    // Получение списка имён групп
                    List<String> groups = httpUtil.getGroupNames();
                    ObservableList<String> groupOL = FXCollections.observableArrayList(groups);
                    groupSelect = new ComboBox<>(groupOL);

                    ObservableList<String> compensation = FXCollections.observableArrayList("Бюджет", "Контракт", "Целевое");
                    compensationBox = new ComboBox<>(compensation);

                    inputForm.getChildren().removeAll(degreeBox, positionBox, departmentBox);
                    inputForm.add(groupSelect, 0, 5);
                    inputForm.add(compensationBox, 0, 6);
                    inputForm.add(new Text(), 0, 7);

                    Button sendButton = new Button("Создать");

                    FlowPane sendPane = new FlowPane(sendButton);
                    sendPane.setAlignment(Pos.CENTER);
                    sendPane.setPadding(new Insets(0, 0, 50, 0));
                    setBottom(sendPane);

                    sendButton.setOnAction(e -> {
                        List<String> unfilledFields = new ArrayList<>();
                        if(lastName.getText().isBlank()) {
                            unfilledFields.add("Фамилия");
                        }
                        if(firstName.getText().isBlank()) {
                            unfilledFields.add("Имя");
                        }
                        if(groupSelect.getValue() == null) {
                            unfilledFields.add("Группа");
                        }

                        if(!unfilledFields.isEmpty()) {
                            errorInfo.setText("Необходимо заполнить поле " + unfilledFields.getFirst());
                            return;
                        }

                        User newUser = new User();
                        newUser.setFirstName(firstName.getText());
                        newUser.setLastName(lastName.getText());
                        newUser.setPatronymic(patronymic.getText());
                        newUser.setEnabledFrom(Date.valueOf(startingUsingAccountDate.getValue()));
                        newUser.setEnabledUntil(Date.valueOf(endingUsingAccountDate.getValue()));
                        newUser.setRole("student");
                        newUser.setGroupName(groupSelect.getValue());
                        newUser.setReimbursement(compensationBox.getValue());

                        System.out.println(httpUtil.saveNewUser(newUser));
                    });
                }
            }
        });
    }
}
