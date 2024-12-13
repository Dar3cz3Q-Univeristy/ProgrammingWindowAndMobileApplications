package org.example.lab4b.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab4b.Model.Teacher;
import org.example.lab4b.Service.TeacherService;

import java.net.URL;
import java.util.ResourceBundle;

public class RaiseSalaryController implements Initializable {

    private final TeacherService teacherService = new TeacherService();
    private Teacher teacher;

    public void sendTeacher(Teacher t) {
        teacher = t;
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
                teacher.setSalary(teacher.getSalary() + Double.parseDouble(salaryInput.getText()));
                teacherService.updateTeacher(teacher);
            } catch (NumberFormatException e) {
                error.setText("Proszę wpisać liczbę");
                return;
            }

            Stage stage = (Stage) rdyButton.getScene().getWindow();
            stage.close();
        });
    }
}
