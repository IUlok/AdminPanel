package com.example.adminpanel.view;


import com.example.adminpanel.entity.Group;
import com.example.adminpanel.http.HttpUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class GroupsPane extends VBox {

    private final HttpUtil httpUtil = new HttpUtil();

    public GroupsPane() {

        List<Group> groupList = httpUtil.getGroups(20);

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

        getChildren().add(table);
    }
}
