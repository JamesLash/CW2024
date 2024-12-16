package com.example.demo.model.actors;

/**
 * Represents a projectile in the game.
 */
public abstract class Projectile extends ActiveActorDestructible {

	// Initialize the projectile
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	// Destroys the projectile on damage
	@Override
	public void takeDamage() {
		this.destroy();
	}

	// Abstract method to update the projectile's position
	@Override
	public abstract void updatePosition();
}
