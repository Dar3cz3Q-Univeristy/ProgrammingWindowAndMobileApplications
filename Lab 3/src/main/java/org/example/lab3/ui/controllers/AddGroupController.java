package org.example.lab3.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.lab3.core.ClassContainer;

import java.net.URL;
import java.util.ResourceBundle;

public class AddGroupController implements Initializable {

    private ClassContainer _classContainer;

    public void sendClassContainer(ClassContainer classContainer) {
        _classContainer = classContainer;
    }

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
                _classContainer.addClass(name.getText(), Integer.parseInt(capacity.getText()));
            } catch (NumberFormatException e) {
                error.setText("Pojemność grupy musi być liczbą");
                return;
            } catch (IllegalArgumentException e) {
                error.setText(e.getMessage());
                return;
            }

            Stage stage = (Stage) rdy.getScene().getWindow();
            stage.close();
        });
    }
}
