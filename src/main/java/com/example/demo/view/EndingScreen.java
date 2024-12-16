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

/**
 * Displays the ending screen with options to play again or quit.
 */
public class EndingScreen {

    private final MenuListener listener;

    // Initialize the ending screen with a menu listener
    public EndingScreen(MenuListener listener) {
        this.listener = listener;
    }

    // Create and return the ending scene
    public Scene createEndingScene(Stage stage, boolean victory) {
        StackPane root = new StackPane(); // Root pane for layout

        // Display win or loss background
        ImageView backgroundImage = new ImageView(
                victory
                        ? new WinImage(stage.getWidth(), stage.getHeight()).getImage()
                        : new GameOverImage(stage.getWidth(), stage.getHeight()).getImage()
        );
        backgroundImage.setFitWidth(stage.getWidth());
        backgroundImage.setFitHeight(stage.getHeight());

        // Add buttons for play again and quit
        Button playAgainButton = new Button("Play Again");
        Button quitButton = new Button("Quit");

        playAgainButton.setOnAction(e -> listener.onStartGame());
        quitButton.setOnAction(e -> listener.onQuit());

        // Arrange buttons in a horizontal layout
        HBox buttonBox = new HBox(20, playAgainButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Combine buttons into a vertical layout
        VBox vbox = new VBox(buttonBox);
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        root.getChildren().addAll(backgroundImage, vbox); // Add elements to root pane

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // Add button styling
        URL buttonCss = getClass().getResource("/styling/Button.css");
        if (buttonCss != null) {
            scene.getStylesheets().add(buttonCss.toExternalForm());
        }
        playAgainButton.getStyleClass().add("button");
        quitButton.getStyleClass().add("button");

        return scene; // Return the completed scene
    }
}
