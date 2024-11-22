module org.example.lab3.ui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.lab3 to javafx.fxml;
    exports org.example.lab3.ui;
    opens org.example.lab3.ui to javafx.fxml;
    exports org.example.lab3.ui.controllers;
    opens org.example.lab3.ui.controllers to javafx.fxml;
    opens org.example.lab3.ui.model to javafx.base;
    opens org.example.lab3.core to javafx.base;
}