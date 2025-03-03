package com.example.adminpanel.view;

import com.example.adminpanel.entity.Group;
import com.example.adminpanel.entity.User;
import com.example.adminpanel.http.HttpUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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

    // Класс-утилита для работы с HTTP-запросами к серверу
    private final HttpUtil httpUtil = new HttpUtil();

    // Элементы ввода
    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField patronymic = new TextField();
    private final TextField email = new TextField();

    private final DatePicker startingUsingAccountDate = new DatePicker();
    private final DatePicker endingUsingAccountDate = new DatePicker();

    // Элементы ввода преподавателя
    private ComboBox<String> degreeBox;
    private ComboBox<String> departmentBox;
    private ComboBox<String> positionBox;

    // Элементы ввода студента
    private ComboBox<String> compensationBox;
    private ComboBox<String> groupSelect;
    private ComboBox<String> facultiesSelect;

    private boolean isStudentForm;

    private Button createButton;

    private FlowPane sendAlignmentPane;

    private Text errorInfo;
    private Text ZAGlushka;

    public NewUserPane() {
        setPrefSize(880, 768);

        ZAGlushka = new Text();

        // Переключение выбора типа пользователя
        GridPane choiceBox = new GridPane();
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
        inputForm.setVisible(false);

        firstName.setPromptText("Имя");
        lastName.setPromptText("Фамилия");
        patronymic.setPromptText("Отчество");
        email.setPromptText("Эл. почта");
        startingUsingAccountDate.setValue(LocalDate.now());
        endingUsingAccountDate.setValue(LocalDate.now());

        errorInfo = new Text();
        errorInfo.setFill(Color.RED);

        // Добавление элементов на форму
        inputForm.add(lastName, 0, 0);
        inputForm.add(firstName, 0, 1);
        inputForm.add(patronymic, 0, 2);
        inputForm.add(email, 0, 3);
        inputForm.add(startingUsingAccountDate, 0, 4);
        inputForm.add(endingUsingAccountDate, 0, 5);
        inputForm.add(errorInfo, 0, 9);

        // Добавление формы на панель
        setCenter(inputForm);

        ChangeListener<Boolean> changeListener = (observableValue, o, isNowSelected) -> {
            inputForm.setVisible(true);
            createButton = new Button("Создать");
            sendAlignmentPane = new FlowPane(createButton);
            sendAlignmentPane.setAlignment(Pos.CENTER);
            sendAlignmentPane.setPadding(new Insets(0, 0, 50, 0));
            setBottom(sendAlignmentPane);
            if(studentChoice.isSelected()){
                isStudentForm = true;
                errorInfo.setText("");
                firstName.setText("");
                lastName.setText("");
                patronymic.setText("");
                // Выбор студент: Изменение стилей
                studentChoice.getStyleClass().removeAll("studentChoice", "studentChoice1");
                prepodChoice.getStyleClass().removeAll("prepodChoice", "prepodChoice1");
                studentChoice.getStyleClass().add("studentChoice1");
                prepodChoice.getStyleClass().add("prepodChoice");

                // Получение списка факультетов
                List<String> faculties = httpUtil.getFaculties();
                ObservableList<String> facultiesOL = FXCollections.observableArrayList(faculties);
                facultiesSelect = new ComboBox<>(facultiesOL);

                groupSelect = new ComboBox<>();
                groupSelect.setDisable(true);

                // При выборе факультета из списка
                facultiesSelect.setOnAction(e -> {
                    String faculty = facultiesSelect.getValue();
                    // Получение имён групп по факультету
                    List<String> groupsByFaculty = httpUtil.findGroupsByParam("faculty", faculty)
                            .stream().map(Group::getName).toList();
                    groupSelect.setItems(FXCollections.observableArrayList(groupsByFaculty));
                    groupSelect.setDisable(false);
                });

                ObservableList<String> compensation = FXCollections.observableArrayList("Бюджет", "Контракт", "Целевое");
                compensationBox = new ComboBox<>(compensation);
                System.out.println(inputForm.getChildren().removeAll(degreeBox, positionBox, departmentBox));
                Platform.runLater(() -> {
                            if(!inputForm.getChildren().contains(facultiesSelect)) inputForm.add(facultiesSelect, 0, 6);
                            if(!inputForm.getChildren().contains(groupSelect)) inputForm.add(groupSelect, 0, 7);
                            if(!inputForm.getChildren().contains(compensationBox)) inputForm.add(compensationBox, 0, 8);
                            if(!inputForm.getChildren().contains(ZAGlushka)) inputForm.add(ZAGlushka, 0, 9);
                        }
                );
            }
            else{
                isStudentForm = false;
                errorInfo.setText("");
                firstName.setText("");
                lastName.setText("");
                patronymic.setText("");
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
                System.out.println(inputForm.getChildren().removeAll(compensationBox, facultiesSelect, groupSelect, ZAGlushka));
                //for(Node i : inputForm.getChildren()) System.out.println(i + "\n");
                Platform.runLater(()-> {
                    if(!inputForm.getChildren().contains(departmentBox)) inputForm.add(departmentBox, 0, 6);
                    if(!inputForm.getChildren().contains(positionBox)) inputForm.add(positionBox, 0, 7);
                    if(!inputForm.getChildren().contains(degreeBox)) inputForm.add(degreeBox, 0, 8);
                });
            }
            createButton.setOnAction(e -> {
                List<String> unfilledFields = new ArrayList<>();
                if(lastName.getText().isBlank()) unfilledFields.add("Фамилия");
                if (firstName.getText().isBlank()) unfilledFields.add("Имя");

                if(isStudentForm){
                    if (groupSelect.getValue() == null) unfilledFields.add("Группа");
                    if (compensationBox.getValue() == null) unfilledFields.add("Основа обучения");
                } else{
                    if(departmentBox.getValue() == null) unfilledFields.add("Факультет");
                    if(positionBox.getValue() == null) unfilledFields.add("Должность");
                    if(degreeBox.getValue() == null) unfilledFields.add("Учёное звание");
                }

                if (!unfilledFields.isEmpty()) {
                    errorInfo.setText("Необходимо заполнить поле " + unfilledFields.getFirst());
                    return;
                }

                User newUser = new User();
                newUser.setFirstName(firstName.getText());
                newUser.setLastName(lastName.getText());
                newUser.setPatronymic(patronymic.getText());
                newUser.setEnabledFrom(Date.valueOf(startingUsingAccountDate.getValue()));
                newUser.setEnabledUntil(Date.valueOf(endingUsingAccountDate.getValue()));
                newUser.setEmail(email.getText());

                if(isStudentForm){
                    newUser.setRole("Студент");
                    newUser.setGroupName(groupSelect.getValue());
                    newUser.setReimbursement(compensationBox.getValue());
                } else{
                    newUser.setRole("Преподаватель");
                    newUser.setDepartment(departmentBox.getValue());
                    newUser.setAcademicTitle(positionBox.getValue());
                    newUser.setAcademicDegree(degreeBox.getValue());
                }

                System.out.println(httpUtil.saveUser(newUser));
            });
        };
        studentChoice.selectedProperty().addListener(changeListener);
        prepodChoice.selectedProperty().addListener(changeListener);
    }
}
