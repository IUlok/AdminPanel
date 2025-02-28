package com.example.adminpanel.view;


import com.example.adminpanel.entity.Group;
import com.example.adminpanel.http.HttpUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class GroupsPane extends BorderPane {

    private final HttpUtil httpUtil = new HttpUtil();

    private final TextField groupName = new TextField();
    private ComboBox<String> faculty;
    private ComboBox<String> programType;
    private final TextField program = new TextField("Направление подготовки");
    private final TextField searchInput = new TextField();
    private ComboBox<String> studyForm;
    private TableView<Group> table;

    private String selectedFindParam = "name";

    private GridPane formPane = new GridPane();

    private Group selectedGroup;

    public GroupsPane() {
        setPrefHeight(768);
        setPrefWidth(880);

        List<Group> groupList = httpUtil.getGroups(20);
        //List<Group> groupList = new ArrayList<>();

        FlowPane panelOnTop = new FlowPane();
        panelOnTop.setAlignment(Pos.CENTER);
        panelOnTop.setHgap(50);
        panelOnTop.setPadding(new Insets(50,0,30,0));

        GridPane searchGrand = new GridPane(2, 1);
        FlowPane searchPanel = new FlowPane();
        searchPanel.setPrefWidth(600);
        searchPanel.setAlignment(Pos.CENTER);
        searchPanel.setHgap(20);
        searchPanel.setVgap(20);
        searchPanel.getStyleClass().add("searchPanel");
        searchInput.setPrefSize(500,50);
        searchInput.getStyleClass().add("searchInput");
        searchPanel.getChildren().add(searchInput);

        Label searchButton = new Label();
        searchButton.getStyleClass().add("searchButton");
        searchButton.setPrefSize(50,50);
        searchPanel.getChildren().add(searchButton);
        searchButton.setOnMouseClicked((e) -> {
            getGroupsWithParamValue();
        });

        RadioButton nameChoice = new RadioButton("по названию");
        RadioButton cursChoice = new RadioButton("по курсу");
        RadioButton facultyChoice = new RadioButton("по факультету");
        RadioButton typeChoice = new RadioButton("по форме обучения");

        nameChoice.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if(isNowSelected) {
                selectedFindParam = "name";
            }
        });

        cursChoice.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if(isNowSelected) {
                selectedFindParam = "course";
            }
        });

        facultyChoice.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if(isNowSelected) {
                selectedFindParam = "faculty";
            }
        });

        typeChoice.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if(isNowSelected) {
                selectedFindParam = "studyForm";
            }
        });

        FlowPane filterPane = new FlowPane();
        filterPane.setPadding(new Insets(20,0,0,0));
        filterPane.setAlignment(Pos.CENTER);
        filterPane.setHgap(50);
        ToggleGroup filterButtons = new ToggleGroup();
        nameChoice.setToggleGroup(filterButtons);
        cursChoice.setToggleGroup(filterButtons);
        facultyChoice.setToggleGroup(filterButtons);
        typeChoice.setToggleGroup(filterButtons);
        nameChoice.setSelected(true);
        filterPane.getChildren().addAll(nameChoice, cursChoice, facultyChoice, typeChoice);

        searchGrand.add(searchPanel, 0, 1);
        searchGrand.add(filterPane, 0,2);

        panelOnTop.getChildren().add(searchGrand);

        Button createGroupButton = new Button("Создать новую группу");
        createGroupButton.getStyleClass().add("creteGroupButton");
        createGroupButton.setPrefSize(150,50);
        createGroupButton.setOnAction(e -> {
            selectedGroup = null;
            groupName.setText("");
            faculty.setValue("");
            programType.setValue("");
            program.setText("");
            studyForm.setValue("");

            formPane.setVisible(true);
        });
        panelOnTop.getChildren().add(createGroupButton);
        setTop(panelOnTop);

        StackPane stackPane = new StackPane();

        ObservableList<Group> groups = FXCollections.observableArrayList(groupList);
        table = new TableView<>(groups);
        table.setPrefWidth(880);
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

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

        formPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
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

        Button saveButton = new Button("Сохранить группу");
        saveButton.setOnAction(e -> {
            Group group = new Group();
            if(selectedGroup != null)
                group.setId(selectedGroup.getId());
            group.setName(groupName.getText());
            group.setFaculty(faculty.getValue());
            group.setProgramType(programType.getValue());
            group.setProgram(program.getText());
            group.setStudyForm(studyForm.getValue());

            System.out.println(httpUtil.saveGroup(group));
            formPane.setVisible(false);
            reloadGroups();
        });

        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e1 -> {
            formPane.setVisible(false);
            reloadGroups();
        });

        formPane.add(saveButton, 0, 5);
        formPane.add(closeButton, 0, 6);
        formPane.setVisible(false);
        stackPane.getChildren().add(formPane);

        table.setRowFactory(tv -> {
            TableRow<Group> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2 && !(row.isEmpty())) {
                    Group group = row.getItem();
                    selectedGroup = group;

                    groupName.setText(group.getName());
                    faculty.setValue(group.getFaculty());
                    programType.setValue(group.getProgramType());
                    program.setText(group.getProgram());
                    studyForm.setValue(group.getStudyForm());
                    formPane.setVisible(true);
                }
            });
            return row;
        });


        //getChildren().add(stackPane);
        setCenter(stackPane);
    }

    private void reloadGroups() {
        if(searchInput.getText().isBlank()) {
            List<Group> newGroupList = httpUtil.getGroups(20);
            table.getItems().clear();
            table.getItems().addAll(newGroupList);
        } else {
            getGroupsWithParamValue();
        }
    }

    private void getGroupsWithParamValue() {
        if(searchInput.getText().isBlank()) {
            return;
        }

        List<Group> groups = httpUtil.findGroupByParam(selectedFindParam, searchInput.getText());
        table.getItems().clear();
        table.getItems().addAll(groups);
    }
}
