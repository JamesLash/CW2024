package com.example.demo.view;

import javafx.scene.Group;

/**
 * Extends LevelView for level two features.
 */
public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;

	// Initialize the view for level two
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	// Add images to the root
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	// Show the shield
	public void showShield() {
		shieldImage.showShield();
	}

	// Hide the shield
	public void hideShield() {
		shieldImage.hideShield();
	}
}
