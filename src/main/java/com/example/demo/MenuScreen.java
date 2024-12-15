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

    // Constructor to initialize the MenuScreen with a listener
    public MenuScreen(MenuListener listener) {
        this.listener = listener;
    }

    // Method to create and return the main menu scene
    public Scene createMenuScene(Stage stage) {
        // Root pane to hold all components
        StackPane root = new StackPane();

        // Background Image
        ImageView background = new ImageView(new Image(
                getClass().getResource("/com/example/demo/images/MenuBackground.jpeg").toExternalForm()));
        background.setFitWidth(stage.getWidth()); // Adjust background to fit stage width
        background.setFitHeight(stage.getHeight()); // Adjust background to fit stage height

        // Buttons for Start Game and Quit
        Button startButton = new Button("Start Game");
        Button quitButton = new Button("Quit");

        // Load custom font for the title
        Font audiowideFont = Font.loadFont(
                getClass().getResource("/fonts/Audiowide-Regular.ttf").toExternalForm(), 120);
        if (audiowideFont != null) {
            System.out.println("Font loaded successfully: " + audiowideFont.getName());
        } else {
            System.err.println("Failed to load font: Audiowide");
        }

        // Title text with custom font and styles
        Text title = new Text("Sky Battle");
        title.setFont(audiowideFont); // Apply the custom font
        title.setFill(Color.LIGHTSTEELBLUE); // Set text color
        title.setStroke(Color.BLACK); // Set border color for the title
        title.setStrokeWidth(2); // Set border thickness
        title.getStyleClass().add("title"); // Add CSS style class (if needed)

        // Layout to hold the title and buttons vertically
        VBox vbox = new VBox(20, title, startButton, quitButton); // Spacing between elements is 20px
        vbox.setAlignment(Pos.CENTER); // Center all elements in the VBox

        // Event Handlers for buttons
        startButton.setOnAction(e -> listener.onStartGame()); // Start Game button action
        quitButton.setOnAction(e -> listener.onQuit()); // Quit button action

        // Add the background and VBox to the root layout
        root.getChildren().addAll(background, vbox);

        // Create the scene with the specified dimensions
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // Apply Button.css for styling buttons
        URL buttonCss = getClass().getResource("/styling/Button.css");
        if (buttonCss != null) {
            System.out.println("Button CSS file loaded: " + buttonCss.toExternalForm());
            scene.getStylesheets().add(buttonCss.toExternalForm());
        } else {
            System.err.println("Button CSS file not found: /styling/Button.css");
        }

        // Add CSS style classes to the buttons
        startButton.getStyleClass().add("button");
        quitButton.getStyleClass().add("button");

        // Return the complete scene
        return scene;
    }
}
