package org.example.lab3.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab3.core.ClassContainer;
import org.example.lab3.core.Teacher;
import org.example.lab3.core.TeacherConditionEnum;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTeacherController implements Initializable {

    private ClassContainer _classContainer;
    private String _selectedGroup;

    public void sendClassContainer(ClassContainer classContainer, String selectedGroup) {
        _classContainer = classContainer;
        _selectedGroup = selectedGroup;
    }

    @FXML
    private Label error;

    @FXML
    private TextField lastname;

    @FXML
    private TextField name;

    @FXML
    private Button rdy;

    @FXML
    private TextField salary;

    @FXML
    private ComboBox<TeacherConditionEnum> changeState;

    @FXML
    private TextField year;

    private void initializeComboBox() {
        ObservableList<TeacherConditionEnum> items = FXCollections.observableArrayList(
                TeacherConditionEnum.OBECNY,
                TeacherConditionEnum.DELEGACJA,
                TeacherConditionEnum.CHORY,
                TeacherConditionEnum.NIEOBECNY

        );
        changeState.setItems(items);
        changeState.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboBox();
        rdy.setOnAction(event -> {
            try {
                Teacher teacher = new Teacher(
                        name.getText(),
                        lastname.getText(),
                        changeState.getValue(),
                        Integer.parseInt(year.getText()),
                        Double.parseDouble(salary.getText())
                );

                _classContainer.addTeacher(_selectedGroup, teacher);
            } catch (NumberFormatException e) {
                error.setText("Wypłata oraz rok urodzenia musi być liczbą");
                return;
            } catch (Exception e) {
                error.setText(e.getMessage());
                return;
            }

            Stage stage = (Stage) rdy.getScene().getWindow();
            stage.close();
        });
    }
}
