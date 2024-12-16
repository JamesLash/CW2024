package com.example.demo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the shield image for the boss.
 */
public class ShieldImage extends ImageView {

	private static final int SHIELD_SIZE = 200;

	// Initialize the shield image at a specific position
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/effects/shield.png")).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	// Show the shield
	public void showShield() {
		this.setVisible(true);
	}

	// Hide the shield
	public void hideShield() {
		this.setVisible(false);
	}
}
