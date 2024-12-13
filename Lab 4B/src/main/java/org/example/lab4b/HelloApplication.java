package org.example.lab4b;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.lab4b.Controllers.ApplicationController;
import org.example.lab4b.Util.HibernateUtil;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void init() throws Exception {
        HibernateUtil.getSessionFactory();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        ApplicationController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("Lab 3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}