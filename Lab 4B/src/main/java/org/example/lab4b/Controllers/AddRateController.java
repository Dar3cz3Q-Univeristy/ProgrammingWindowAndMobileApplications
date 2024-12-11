package org.example.lab4b.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.lab4b.Model.Rate;
import org.example.lab4b.Model.TeacherClass;
import org.example.lab4b.Service.RateService;
import org.example.lab4b.Service.TeacherClassService;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRateController implements Initializable {

    private final RateService rateService = new RateService();
    private final TeacherClassService teacherClassService = new TeacherClassService();

    @FXML
    private Label error;

    @FXML
    private ComboBox<Integer> grade;

    @FXML
    private ComboBox<TeacherClass> group;

    @FXML
    private TextArea comment;

    @FXML
    private Button rdy;

    private void initializeComboBox() {
        ObservableList<Integer> grades = FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6
        );
        grade.setItems(grades);
        grade.getSelectionModel().select(5);

        ObservableList<TeacherClass> groups = FXCollections.observableArrayList(
                teacherClassService.getAllGroups()
        );

        group.setConverter(new StringConverter<>() {
            @Override
            public String toString(TeacherClass entity) {
                return entity.getName();
            }

            @Override
            public TeacherClass fromString(String string) {
                return null;
            }
        });
        group.setItems(groups);
        group.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboBox();
        rdy.setOnAction(event -> {
            try {
                Rate rate = new Rate(
                        grade.getValue(),
                        group.getValue()
                );

                if (!comment.getText().isEmpty()) {
                    rate.setComment(comment.getText());
                }

                rateService.createRate(rate);
            } catch (Exception e) {
                error.setText(e.getMessage());
                return;
            }

            Stage stage = (Stage) rdy.getScene().getWindow();
            stage.close();
        });
    }
}
