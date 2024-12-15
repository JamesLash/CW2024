package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScreen {

    private final MenuListener listener;

    public MenuScreen(MenuListener listener) {
        this.listener = listener;
    }

    public Scene createMenuScene(Stage stage) {
        // Root Pane
        StackPane root = new StackPane();

        // Background Image
        ImageView background = new ImageView(new Image(
                getClass().getResource("/com/example/demo/images/MenuBackground.jpeg").toExternalForm()));
        background.setFitWidth(stage.getWidth());
        background.setFitHeight(stage.getHeight());

        // Buttons
        Button startButton = new Button("Start Game");
        Button quitButton = new Button("Quit");

        VBox vbox = new VBox(20, startButton, quitButton);
        vbox.setAlignment(Pos.CENTER);

        // Event Handlers
        startButton.setOnAction(e -> listener.onStartGame());
        quitButton.setOnAction(e -> listener.onQuit());

        root.getChildren().addAll(background, vbox);

        return new Scene(root, stage.getWidth(), stage.getHeight());
    }
}
