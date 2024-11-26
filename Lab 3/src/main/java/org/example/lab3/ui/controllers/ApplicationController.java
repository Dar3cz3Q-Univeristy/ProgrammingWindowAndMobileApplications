package org.example.lab3.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.lab3.core.ClassContainer;
import org.example.lab3.core.Teacher;
import org.example.lab3.core.TeacherConditionEnum;
import org.example.lab3.ui.model.Group;
import org.example.lab3.mockdata.ClassContainerMockData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    private ClassContainer classContainer;

    private String selectedGroup;

    private Teacher selectedTeacher;

    @FXML
    private Button raiseSalary;

    @FXML
    private ComboBox<TeacherConditionEnum> changeState;

    @FXML
    private Button removeGroup;

    @FXML
    private Button removeTeacher;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeGroups();
        initializeTeachers();
        initializeEvents();
        initializeComboBox();
    }

    private void initializeEvents() {
        // Change group event
        groupsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedGroup = newSelection.getName();
                updateTeachersTable();
                selectedTeacher = null;
                updateTeacherInfo();
            }
        });

        // Change teacher event
        teachersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedTeacher = newSelection;
                updateTeacherInfo();
            }
        });

        // Search event
        teachersSearch.setOnKeyPressed(this::handleSearch);

        // Change condition event
        changeState.setOnAction(this::handleConditionChange);

        // Raise salary event
        raiseSalary.setOnAction(this::handleRaiseSalary);

        // Remove teacher event
        removeTeacher.setOnAction(this::handleRemoveTeacher);

        // Remove group event
        removeGroup.setOnAction(this::handleRemoveGroup);

        // Add group event
        addGroupButton.setOnAction(this::handleAddGroup);

        // Add teacher event
        addTeacherButton.setOnAction(this::handleAddTeacher);
    }

    private void initializeComboBox() {
        ObservableList<TeacherConditionEnum> items = FXCollections.observableArrayList(
                TeacherConditionEnum.OBECNY,
                TeacherConditionEnum.DELEGACJA,
                TeacherConditionEnum.CHORY,
                TeacherConditionEnum.NIEOBECNY

        );
        changeState.setItems(items);
    }

    private void initializeComboBox(int state) {
        changeState.getSelectionModel().select(state);
    }

    // Groups

    @FXML
    private Button addGroupButton;

    @FXML
    private TableView<Group> groupsTable;

    @FXML
    private TableColumn<Group, String> groupsTableName;

    @FXML
    private TableColumn<Group, String> groupsTableCapacity;

    @FXML
    private TableColumn<Group, String> groupsTableFill;

    ObservableList<Group> groups = FXCollections.observableArrayList();

    public void initializeGroups() {
        classContainer = ClassContainerMockData.GetMockData();

        groupsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupsTableCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        groupsTableFill.setCellValueFactory(new PropertyValueFactory<>("fillPercentage"));

        displayGroups();
    }

    public void displayGroups() {
        groupsTable.getItems().clear();
        for (String key : classContainer.getTeachersGroups().keySet()) {
            groups.add(new Group(
                    key,
                    classContainer.getTeachersGroups().get(key).getMaxTeachersCapacity(),
                    (double) classContainer.getClassTeacher(key).Fill() / (double) classContainer.getClassTeacher(key).getMaxTeachersCapacity() * 100
            ));
        }
        groupsTable.setItems(groups);
    }

    private void handleAddGroup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab3/add-group-view.fxml"));
            Parent root = loader.load();

            AddGroupController controller = loader.getController();
            controller.sendClassContainer(classContainer);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj nową grupę");
            stage.setOnHiding(event -> {
                displayGroups();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddTeacher(ActionEvent actionEvent) {
        if (selectedGroup == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab3/add-teacher-view.fxml"));
            Parent root = loader.load();

            AddTeacherController controller = loader.getController();
            controller.sendClassContainer(classContainer, selectedGroup);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj nowego nauczyciela");
            stage.setOnHiding(event -> {
                updateTeachersTable();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Teachers

    @FXML
    private Button addTeacherButton;

    @FXML
    private TextField teachersSearch;

    @FXML
    private TableView<Teacher> teachersTable;

    @FXML
    private TableColumn<Teacher, String> teachersName;

    @FXML
    private TableColumn<Teacher, String> teachersLastName;

    ObservableList<Teacher> teachers = FXCollections.observableArrayList();

    public void initializeTeachers() {
        teachersName.setCellValueFactory(new PropertyValueFactory<>("name"));
        teachersLastName.setCellValueFactory(new PropertyValueFactory<>("surname"));
    }

    private void handleSearch(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            teachersTable.getItems().clear();
            teachers.addAll(classContainer.getClassTeacher(selectedGroup).searchPartial(teachersSearch.getText()));
            teachersTable.setItems(teachers);
        }
    }

    public void updateTeachersTable() {
        teachersTable.getItems().clear();
        teachers.addAll(classContainer.getClassTeacher(selectedGroup).sortByName());
        teachersTable.setItems(teachers);
    }

    // Teacher info

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label state;

    @FXML
    private Label year;

    @FXML
    private Label salary;

    public void updateTeacherInfo() {
        if (selectedTeacher == null) {
            name.setText("");
            surname.setText("");
            salary.setText("");
            year.setText("");
            state.setText("");

            return;
        }

        name.setText(selectedTeacher.getName());
        surname.setText(selectedTeacher.getSurname());
        salary.setText(selectedTeacher.getSalary() + " PLN");
        year.setText(selectedTeacher.getYearOfBirth() + "");
        state.setText(selectedTeacher.getCondition().toString());

        initializeComboBox(selectedTeacher.getCondition().ordinal());
    }

    private void handleConditionChange(ActionEvent actionEvent) {
        if (selectedTeacher == null) {
            return;
        }
        classContainer.changeCondition(selectedGroup, selectedTeacher, changeState.getSelectionModel().getSelectedItem());
        updateTeacherInfo();
    }

    private void handleRaiseSalary(ActionEvent actionEvent) {
        if (selectedTeacher == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab3/raise-salary-view.fxml"));
            Parent root = loader.load();

            RaiseSalaryController controller = loader.getController();
            controller.sendTeacher(selectedTeacher);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Zwiększ wypłatę");
            stage.setOnHiding(event -> updateTeacherInfo());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveTeacher(ActionEvent actionEvent) {
        classContainer.getClassTeacher(selectedGroup).removeTeacher(selectedTeacher);
        updateTeachersTable();

        selectedTeacher = null;
        updateTeacherInfo();
    }

    private void handleRemoveGroup(ActionEvent actionEvent) {
        classContainer.getTeachersGroups().remove(selectedGroup);
        displayGroups();
        updateTeachersTable();
        selectedTeacher = null;
        updateTeacherInfo();
    }
}