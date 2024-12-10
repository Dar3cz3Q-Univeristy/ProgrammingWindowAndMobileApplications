package org.example.lab4b.Controllers;

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
import org.example.lab4b.DTO.GroupFillDTO;
import org.example.lab4b.Mapper.GroupMapper;
import org.example.lab4b.Model.Teacher;
import org.example.lab4b.Model.TeacherClass;
import org.example.lab4b.Model.TeacherCondition;
import org.example.lab4b.Service.TeacherClassService;
import org.example.lab4b.Service.TeacherService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    private final TeacherService teacherService = new TeacherService();
    private final TeacherClassService classService = new TeacherClassService();

    private String selectedGroup;
    private Teacher selectedTeacher;

    @FXML
    private Button raiseSalary;

    @FXML
    private ComboBox<TeacherCondition> changeState;

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
        ObservableList<TeacherCondition> items = FXCollections.observableArrayList(
                TeacherCondition.OBECNY,
                TeacherCondition.DELEGACJA,
                TeacherCondition.CHORY,
                TeacherCondition.NIEOBECNY

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
    private TableView<GroupFillDTO> groupsTable;

    @FXML
    private TableColumn<TeacherClass, String> groupsTableName;

    @FXML
    private TableColumn<TeacherClass, String> groupsTableCapacity;

    @FXML
    private TableColumn<TeacherClass, String> groupsTableFill;

    ObservableList<GroupFillDTO> groups = FXCollections.observableArrayList();

    public void initializeGroups() {
        groupsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupsTableCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        groupsTableFill.setCellValueFactory(new PropertyValueFactory<>("fillPercentage"));

        updateGroupsTable();
    }

    public void updateGroupsTable() {
        groupsTable.getItems().clear();

        List<TeacherClass> classes = classService.getAllGroups();

        for (var group : classes) {
            int teachersInGroup = teacherService.countByGroup(group.getName());
            groups.add(GroupMapper.toDTO(group, teachersInGroup));
        }

        groupsTable.setItems(groups);
    }

    private void handleAddGroup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab4b/add-group-view.fxml"));
            Parent root = loader.load();

            AddGroupController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj nową grupę");
            stage.setOnHiding(event -> {
                updateGroupsTable();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab4b/add-teacher-view.fxml"));
            Parent root = loader.load();

            TeacherClass group = classService.findByName(selectedGroup);

            AddTeacherController controller = loader.getController();
            controller.sendClassContainer(group);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj nowego nauczyciela");
            stage.setOnHiding(event -> {
                updateGroupsTable();
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
//        if (event.getCode().toString().equals("ENTER")) {
//            teachersTable.getItems().clear();
//            teachers.addAll(classContainer.getClassTeacher(selectedGroup).searchPartial(teachersSearch.getText()));
//            teachersTable.setItems(teachers);
//        }
    }

    public void updateTeachersTable() {
        teachersTable.getItems().clear();

        List<Teacher> localTeachers = teacherService.getTeachersByGroup(selectedGroup);

        teachers.addAll(localTeachers);

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

        selectedTeacher.setCondition(changeState.getSelectionModel().getSelectedItem());
        teacherService.updateTeacher(selectedTeacher);

        updateTeacherInfo();
    }

    private void handleRaiseSalary(ActionEvent actionEvent) {
        if (selectedTeacher == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab4b/raise-salary-view.fxml"));
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
        teacherService.deleteTeacher(selectedTeacher);
        updateTeachersTable();
        updateGroupsTable();

        selectedTeacher = null;
        updateTeacherInfo();
    }

    private void handleRemoveGroup(ActionEvent actionEvent) {
        TeacherClass group = classService.findByName(selectedGroup);
        classService.deleteClass(group);
        updateGroupsTable();
        updateTeachersTable();
        selectedTeacher = null;
        updateTeacherInfo();
    }
}