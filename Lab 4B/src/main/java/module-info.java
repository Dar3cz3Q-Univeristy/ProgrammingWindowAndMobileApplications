module org.example.lab4b {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;
    requires java.naming;

    opens org.example.lab4b.Model to org.hibernate.orm.core, javafx.base;

    opens org.example.lab4b to javafx.fxml;
    opens org.example.lab4b.DTO to javafx.base;

    exports org.example.lab4b;
    exports org.example.lab4b.Controllers;
    opens org.example.lab4b.Controllers to javafx.fxml;
}