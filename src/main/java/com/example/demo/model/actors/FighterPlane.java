package com.example.demo.model.actors;

/**
 * Represents a fighter plane.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	// Initialize the fighter plane
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	// Abstract method for firing a projectile
	public abstract ActiveActorDestructible fireProjectile();

	// Handles damage to the plane
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	// Gets the X-coordinate for the projectile
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	// Gets the Y-coordinate for the projectile
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	// Checks if health is zero
	private boolean healthAtZero() {
		return health == 0;
	}

	// Returns the health of the plane
	public int getHealth() {
		return health;
	}
}
