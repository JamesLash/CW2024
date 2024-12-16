package com.example.demo.view;

import com.example.demo.model.interfaces.MenuListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class EndingScreen {

    private final MenuListener listener;

    public EndingScreen(MenuListener listener) {
        this.listener = listener;
    }

    public Scene createEndingScene(Stage stage, boolean victory) {
        // Root pane
        StackPane root = new StackPane();

        // Background Image (Win or Loss)
        ImageView backgroundImage = new ImageView(
                victory
                        ? new WinImage(stage.getWidth(), stage.getHeight()).getImage()
                        : new GameOverImage(stage.getWidth(), stage.getHeight()).getImage()
        );
        backgroundImage.setFitWidth(stage.getWidth());
        backgroundImage.setFitHeight(stage.getHeight());

        // Buttons
        Button playAgainButton = new Button("Play Again");
        Button quitButton = new Button("Quit");

        // Add Event Handlers
        playAgainButton.setOnAction(e -> listener.onStartGame());
        quitButton.setOnAction(e -> listener.onQuit());

        // Arrange buttons horizontally
        HBox buttonBox = new HBox(20, playAgainButton, quitButton); // Spacing of 20px between buttons
        buttonBox.setAlignment(Pos.CENTER);

        // Combine title and buttons vertically
        VBox vbox = new VBox(buttonBox); // Spacing of 40px between title and buttons
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        // Add everything to the root pane
        root.getChildren().addAll(backgroundImage, vbox);

        // Create the scene
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
        playAgainButton.getStyleClass().add("button");
        quitButton.getStyleClass().add("button");

        // Return the complete scene
        return scene;
    }
}
