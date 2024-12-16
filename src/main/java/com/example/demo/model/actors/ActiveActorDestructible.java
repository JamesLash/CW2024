package com.example.demo.model.actors;

import com.example.demo.model.interfaces.Destructible;

/**
 * A destructible active actor.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	// Constructor to initialize the actor
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	// Updates the actor's position
	@Override
	public abstract void updatePosition();

	// Updates the actor's state
	public abstract void updateActor();

	// Handles damage to the actor
	@Override
	public abstract void takeDamage();

	// Destroys the actor
	@Override
	public void destroy() {
		setDestroyed();
	}

	// Sets the actor as destroyed
	protected void setDestroyed() {
		this.isDestroyed = true;
	}

	// Returns if the actor is destroyed
	public boolean isDestroyed() {
		return isDestroyed;
	}

}