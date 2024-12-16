package com.example.demo.model.actors;

/**
 * Represents the boss's projectile.
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "actors/fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	// Constructor to initialize the projectile
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
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
