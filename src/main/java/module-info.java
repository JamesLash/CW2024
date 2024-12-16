module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.example.demo.controller;
    exports com.example.demo.view;
    exports com.example.demo.model.actors;
    exports com.example.demo.model.interfaces;
    exports com.example.demo.model.levels;
    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.view to javafx.fxml;
    opens com.example.demo.model.actors to javafx.fxml;
    opens com.example.demo.model.interfaces to javafx.fxml;
    opens com.example.demo.model.levels to javafx.fxml;
}