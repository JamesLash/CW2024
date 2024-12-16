package com.example.demo.model.actors;

/**
 * Represents a projectile fired by an enemy.
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "actors/enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -10;

	// Initialize the enemy projectile
	public EnemyProjectile(double initialXPos, double initialYPos) {
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
