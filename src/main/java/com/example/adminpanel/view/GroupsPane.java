package com.example.adminpanel.view;


import com.example.adminpanel.entity.Group;
import com.example.adminpanel.http.HttpUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class GroupsPane extends VBox {

    private final HttpUtil httpUtil = new HttpUtil();

    private final TextField groupName = new TextField("Название группы");
    private ComboBox<String> faculty;
    private ComboBox<String> programType;
    private final TextField program = new TextField("Направление подготовки");
    private ComboBox<String> studyForm;

    public GroupsPane() {

        List<Group> groupList = httpUtil.getGroups(20);

        StackPane stackPane = new StackPane();

        ObservableList<Group> groups = FXCollections.observableArrayList(groupList);
        TableView<Group> table = new TableView<>(groups);
        table.setPrefWidth(400);
        table.setPrefHeight(500);

        TableColumn<Group, String> nameColumn = new TableColumn<>("Группа");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(nameColumn);

        TableColumn<Group, String> facultyColumn = new TableColumn<>("Факультет");
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        table.getColumns().add(facultyColumn);

        TableColumn<Group, String> programTypeColumn = new TableColumn<>("Тип направления");
        programTypeColumn.setCellValueFactory(new PropertyValueFactory<>("programType"));
        table.getColumns().add(programTypeColumn);

        TableColumn<Group, String> programColumn = new TableColumn<>("Направление подготовки");
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));
        table.getColumns().add(programColumn);

        stackPane.getChildren().add(table);
        GridPane formPane = new GridPane();
        formPane.setMaxSize(300, 400);

        ObservableList<String> facultyList = FXCollections.observableArrayList("Автоматизация и интеллектуальные технологии",
                "Транспортное строительство", "Управление перевозками и логистика", "Факультет безотрывных форм обучения",
                "Промышленное и гражданское строительство", "Транспортные и энергетические системы", "Экономика и менеджмент");
        faculty = new ComboBox<>(facultyList);
        ObservableList<String> programTypeList = FXCollections.observableArrayList("Бакалавриат", "Специалитет",
                "Магистратура");
        programType = new ComboBox<>(programTypeList);
        ObservableList<String> studyFormList = FXCollections.observableArrayList("Очная", "Очно-заочная",
                "Заочная");
        studyForm = new ComboBox<>(studyFormList);

        formPane.add(new Text("Название группы"), 0, 0);
        formPane.add(groupName, 1, 0);
        formPane.add(new Text("Факультет"), 0, 1);
        formPane.add(faculty, 1, 1);
        formPane.add(new Text("Тип направления"), 0, 2);
        formPane.add(programType, 1, 2);
        formPane.add(new Text("Направление"), 0, 3);
        formPane.add(program, 1, 3);
        formPane.add(new Text("Форма обучения"), 0, 4);
        formPane.add(studyForm, 1, 4);

        //stackPane.getChildren().add(formPane);
        getChildren().add(stackPane);
    }
}
