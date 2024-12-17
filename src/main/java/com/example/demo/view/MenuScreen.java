package com.example.demo.view;

import com.example.demo.model.interfaces.MenuListener;
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

/**
 * Represents the main menu screen.
 */
public class MenuScreen {

    private final MenuListener listener;

    // Initialize the menu screen with a listener
    public MenuScreen(MenuListener listener) {
        this.listener = listener;
    }

    // Create and return the main menu scene
    public Scene createMenuScene(Stage stage) {
        StackPane root = new StackPane(); // Root pane

        // Background image
        ImageView background = new ImageView(new Image(
                getClass().getResource("/com/example/demo/images/backgrounds/MenuBackground.jpeg").toExternalForm()));
        background.setFitWidth(stage.getWidth());
        background.setFitHeight(stage.getHeight());

        // Buttons for Start Game and Quit
        Button startButton = new Button("Start Game");
        Button quitButton = new Button("Quit");

        // Load and style the title
        Text title = new Text("Sky Battle");
        title.setFont(Font.loadFont(getClass().getResource("/fonts/Audiowide-Regular.ttf").toExternalForm(), 120));
        title.setFill(Color.LIGHTSTEELBLUE);

        // Layout for title and buttons
        VBox vbox = new VBox(20, title, startButton, quitButton);
        vbox.setAlignment(Pos.CENTER);

        // Button actions
        startButton.setOnAction(e -> listener.onStartGame());
        quitButton.setOnAction(e -> listener.onQuit());


        // Add button styling
        URL buttonCss = getClass().getResource("/styling/Button.css");
        startButton.getStyleClass().add("button");
        quitButton.getStyleClass().add("button");

// Add elements to the root pane
        root.getChildren().addAll(background, vbox);

// Create and return the scene
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        scene.getStylesheets().add(buttonCss.toExternalForm()); // Add CSS file
        return scene;

    }
}
