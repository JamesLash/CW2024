package com.example.demo.model.actors;

/**
 * Represents the user's projectile.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "actors/userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final int HORIZONTAL_VELOCITY = 15;

	// Initialize the user's projectile
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	// Updates the position of the projectile
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	// Updates the state of the projectile
	@Override
	public void updateActor() {
		updatePosition();
	}
}
