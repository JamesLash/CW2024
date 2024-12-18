package com.example.demo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the win screen image.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/UI/win.jpeg";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	// Initialize the win image at a specific position
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	// Show the win image
	public void showWinImage() {
		this.setVisible(true);
	}
}
