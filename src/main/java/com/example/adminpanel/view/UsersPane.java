package com.example.adminpanel.view;

import com.example.adminpanel.entity.Group;
import com.example.adminpanel.entity.User;
import com.example.adminpanel.http.HttpUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;

public class UsersPane extends BorderPane {
    private final HttpUtil httpUtil = new HttpUtil();

    // Элементы ввода
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
    private ComboBox<String> facultiesSelect;

    private Button saveButton;

    private final TextField searchInput = new TextField();
    private final TableView<User> table;

    // В этой переменной хранится имя свойства, по которому производится поиск
    private String selectedFindParam = "lastname";

    private final GridPane formPane = new GridPane();

    // Группа, с которой ведётся работа
    private User selectedUser;

    private Text errorInfo;

    public UsersPane() {
        setPrefSize(880, 768);

        errorInfo = new Text();
        errorInfo.setFill(Color.RED);

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

        RadioButton lastnameChoice = new RadioButton("по фамилии");
        RadioButton groupChoice = new RadioButton("по группе");
        RadioButton departmentChoice = new RadioButton("по кафедре");
        RadioButton typeChoice = new RadioButton("по форме обучения");

        lastnameChoice.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if(isNowSelected) {
                selectedFindParam = "lastname";
            }
        });

        groupChoice.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if(isNowSelected) {
                selectedFindParam = "group";
            }
        });

        departmentChoice.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if(isNowSelected) {
                selectedFindParam = "department";
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
        lastnameChoice.setToggleGroup(filterButtons);
        groupChoice.setToggleGroup(filterButtons);
        departmentChoice.setToggleGroup(filterButtons);
        typeChoice.setToggleGroup(filterButtons);
        lastnameChoice.setSelected(true);
        filterPane.getChildren().addAll(lastnameChoice, groupChoice, departmentChoice, typeChoice);

        searchGrand.add(searchPanel, 0, 1);
        searchGrand.add(filterPane, 0,2);

        panelOnTop.getChildren().add(searchGrand);

        setTop(panelOnTop);

        // Основной интерфейс помещается в StackPane, чтобы была возможность размещения окна с формой
        // редактирования группы поверх всех элементов
        StackPane stackPane = new StackPane();

        table = new TableView<>();
        table.setPrefSize(880, 460);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<User, String> lastNameColumn = new TableColumn<>("Фамилия");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        table.getColumns().add(lastNameColumn);

        TableColumn<User, String> firstNameColumn = new TableColumn<>("Имя");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.prefWidthProperty().bind(table.widthProperty().multiply(1));
        table.getColumns().add(firstNameColumn);

        TableColumn<User, String> patronymicColumn = new TableColumn<>("Отчество");
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        patronymicColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.7));
        table.getColumns().add(patronymicColumn);

        TableColumn<User, String> roleColumn = new TableColumn<>("Тип пользователя");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.prefWidthProperty().bind(table.widthProperty().multiply(3));
        table.getColumns().add(roleColumn);

        TableColumn<User, User> settings = new TableColumn<>();
        settings.setCellFactory(col -> {
            Label settingsButton = new Label();
            settingsButton.setPrefSize(20,21);
            settingsButton.getStyleClass().add("settingsButton");

            Label deleteButton = new Label();
            deleteButton.setPrefSize(20,21);
            deleteButton.getStyleClass().add("deleteButton");

            FlowPane buttonsSettings = new FlowPane(settingsButton, deleteButton);
            buttonsSettings.setHgap(3);
            buttonsSettings.setAlignment(Pos.CENTER);

            TableCell<User, User> addCell = new TableCell<>() {
                public void updateItem(User group, boolean empty) {
                    super.updateItem(group, empty);
                    if(empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(buttonsSettings);
                    }
                }
            };
            settingsButton.setOnMouseClicked(event -> {
                TableRow<User> row = addCell.getTableRow();
                selectedUser = row.getItem();
                showGroupInfo();
            });

            deleteButton.setOnMouseClicked(event -> {
                TableRow<User> row = addCell.getTableRow();
                selectedUser = row.getItem();

                deleteMethod();
            });
            return addCell;
        });
        settings.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        table.getColumns().add(settings);

        stackPane.getChildren().add(table);
        formPane.setMaxSize(400, 300);
        formPane.getStyleClass().add("formGroupPane");
        formPane.setAlignment(Pos.CENTER);
        formPane.setVgap(10);
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
        formPane.add(new Text("Название группы: "), 0, 0);
        formPane.add(groupName, 1, 0);
        formPane.add(new Text("Факультет: "), 0, 1);
        formPane.add(faculty, 1, 1);
        formPane.add(new Text("Образование: "), 0, 2);
        formPane.add(programType, 1, 2);
        formPane.add(new Text("Направление: "), 0, 3);
        formPane.add(program, 1, 3);
        formPane.add(new Text("Форма обучения: "), 0, 4);
        formPane.add(studyForm, 1, 4);
        formPane.add(errorInfo, 0, 7);

        Button saveButton = new Button("Сохранить группу");
        saveButton.setOnAction(e -> {
            List<String> unfilledFields = new ArrayList<>();
            if(groupName.getText().isBlank()) unfilledFields.add("Название группы");
            if(faculty.getValue() == "") unfilledFields.add("Факультет");
            if(programType.getValue() == "") unfilledFields.add("Образование");
            if(program.getText().isBlank()) unfilledFields.add("Направление");
            if(studyForm.getValue() == "") unfilledFields.add("Форма обучения");
            if(!unfilledFields.isEmpty()){
                StringBuilder str = new StringBuilder();
                for(String s : unfilledFields) str.append(s).append(", ");
                errorInfo.setText("Необходимо заполнить следующие поля: " + str);
                return;
            }
            Group group = new Group();
            if(selectedUser != null) group.setId(selectedUser.getId());
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
        saveButton.setPrefWidth(120);
        FlowPane panelButtons = new FlowPane(saveButton, closeButton);
        panelButtons.setHgap(20);

        formPane.add(panelButtons, 1,5);
        formPane.setVisible(false);
        stackPane.getChildren().add(formPane);
        // Установка на таблицу свойства получения фокуса
        table.setFocusTraversable(true);

        table.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1 && !(row.isEmpty())) {
                    // Установка фокуса на строку
                    row.requestFocus();
                }
                if (e.getClickCount() == 2 && !(row.isEmpty())) {
                    User user = row.getItem();
                    selectedUser = user;

                    showGroupInfo();
                }
            });
            row.setOnKeyPressed(ke -> {
                System.out.println(ke.getCode().getName());
                if (ke.getCode() == KeyCode.DELETE) {
                    User selectedItem = table.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        selectedUser = selectedItem;
                        deleteMethod();
                        ke.consume();
                    }
                }
            });
            return row;
        });

        setCenter(stackPane);
    }

    private void showGroupInfo() {

        groupName.setText(selectedUser.getName());
        faculty.setValue(selectedUser.getFaculty());
        programType.setValue(selectedUser.getProgramType());
        program.setText(selectedUser.getProgram());
        studyForm.setValue(selectedUser.getStudyForm());
        formPane.setVisible(true);
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

    private void deleteMethod(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтвердите действие");
        alert.setHeaderText("Удаление группы");
        alert.setContentText("Вы точно намерены удалить данную группу?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            boolean res = httpUtil.deleteGroupById(selectedUser.getId());
            if(res) {
                selectedUser = null;
                reloadGroups();
            }
        }
    }
}
