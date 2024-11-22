package org.example.lab3.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab3.core.Teacher;

import java.net.URL;
import java.util.ResourceBundle;

public class RaiseSalaryController implements Initializable {

    private Teacher _teacher;

    public void sendTeacher(Teacher teacher) {
        _teacher = teacher;
    }

    @FXML
    private Label error;

    @FXML
    private Button rdyButton;

    @FXML
    private TextField salaryInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdyButton.setOnAction(event -> {
            try {
                _teacher.updateSalary(Double.parseDouble(salaryInput.getText()));
            } catch (NumberFormatException e) {
                error.setText("Proszę wpisać liczbę");
                return;
            }

            Stage stage = (Stage) rdyButton.getScene().getWindow();
            stage.close();
        });
    }
}
