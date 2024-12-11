package org.example.lab4b.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab4b.Model.TeacherClass;
import org.example.lab4b.Service.TeacherClassService;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.ResourceBundle;

public class AddGroupController implements Initializable {

    private final TeacherClassService classService = new TeacherClassService();

    @FXML
    private TextField capacity;

    @FXML
    private Label error;

    @FXML
    private TextField name;

    @FXML
    private Button rdy;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdy.setOnAction(event -> {

            try {
                TeacherClass teacherClass = new TeacherClass(name.getText(), Integer.parseInt(capacity.getText()));
                classService.createGroup(teacherClass);
            } catch (NumberFormatException e) {
                error.setText("Pojemność grupy musi być liczbą");
                return;
            } catch (IllegalArgumentException e) {
                error.setText(e.getMessage());
                return;
            } catch (HibernateException e) {
                error.setText("Nazwa grupy jest juz zajeta");
                return;
            }

            Stage stage = (Stage) rdy.getScene().getWindow();
            stage.close();
        });
    }
}
