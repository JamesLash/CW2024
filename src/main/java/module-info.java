module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.view to javafx.fxml;
    opens com.example.demo.model.actors to javafx.fxml;
    opens com.example.demo.model.interfaces to javafx.fxml;
    opens com.example.demo.model.levels to javafx.fxml;
}