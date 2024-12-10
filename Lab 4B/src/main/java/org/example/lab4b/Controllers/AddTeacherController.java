package org.example.lab4b.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab4b.Model.Teacher;
import org.example.lab4b.Model.TeacherClass;
import org.example.lab4b.Model.TeacherCondition;
import org.example.lab4b.Service.TeacherService;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTeacherController implements Initializable {

    private final TeacherService service = new TeacherService();
    private TeacherClass selectedGroup;

    public void sendClassContainer(TeacherClass group) {
        selectedGroup = group;
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
    private ComboBox<TeacherCondition> changeState;

    @FXML
    private TextField year;

    private void initializeComboBox() {
        ObservableList<TeacherCondition> items = FXCollections.observableArrayList(
                TeacherCondition.OBECNY,
                TeacherCondition.DELEGACJA,
                TeacherCondition.CHORY,
                TeacherCondition.NIEOBECNY
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
                        selectedGroup,
                        name.getText(),
                        lastname.getText(),
                        changeState.getValue(),
                        Integer.parseInt(year.getText()),
                        Double.parseDouble(salary.getText())
                );

                service.createTeacher(teacher);
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
