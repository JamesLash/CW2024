package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;

public class MenuScreen {

    private final MenuListener listener;

    public MenuScreen(MenuListener listener) {
        this.listener = listener;
    }

    public Scene createMenuScene(Stage stage) {
        StackPane root = new StackPane();

        // Background Image
        ImageView background = new ImageView(new Image(
                getClass().getResource("/com/example/demo/images/MenuBackground.jpeg").toExternalForm()));
        background.setFitWidth(stage.getWidth());
        background.setFitHeight(stage.getHeight());

        // Buttons
        Button startButton = new Button("Start Game");
        Button quitButton = new Button("Quit");

        Font audiowideFont = Font.loadFont(getClass().getResource("/fonts/Audiowide-Regular.ttf").toExternalForm(), 120);
        if (audiowideFont != null) {
            System.out.println("Font loaded successfully: " + audiowideFont.getName());
        } else {
            System.err.println("Failed to load font: Audiowide");
        }

        Text title = new Text("Sky Battle");
        title.setFont(audiowideFont); // Apply the custom font
        title.getStyleClass().add("title");
        title.setFill(Color.LIGHTSTEELBLUE);
        title.setStroke(Color.BLACK); // Border color
        title.setStrokeWidth(2);      // Border thickness

        // Vertical Layout
        VBox vbox = new VBox(20, title, startButton, quitButton);
        vbox.setAlignment(Pos.CENTER);

        // Event Handlers
        startButton.setOnAction(e -> listener.onStartGame());
        quitButton.setOnAction(e -> listener.onQuit());

        root.getChildren().addAll(background, vbox);

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // Apply Button.css
        URL buttonCss = getClass().getResource("/styling/Button.css");
        if (buttonCss != null) {
            System.out.println("Button CSS file loaded: " + buttonCss.toExternalForm());
            scene.getStylesheets().add(buttonCss.toExternalForm());
        } else {
            System.err.println("Button CSS file not found: /styling/Button.css");
        }


        // Add styles to buttons
        startButton.getStyleClass().add("button");
        quitButton.getStyleClass().add("button");

        return scene;
    }
}
