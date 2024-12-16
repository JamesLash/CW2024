package com.example.demo.model.actors;

import javafx.scene.Group;

/**
 * Represents the user's plane.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "actors/userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double X_UPPER_BOUND = 0;
	private static final double X_LOWER_BOUND = 1200.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 70;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_OFFSET = 130;
	private static final int PROJECTILE_Y_POSITION_OFFSET = -5;

	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	// Initialize the user's plane
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	// Updates the position of the plane
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();
			if (newXPosition < X_UPPER_BOUND || newXPosition > X_LOWER_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	// Updates the state of the plane
	@Override
	public void updateActor() {
		updatePosition();
	}

	// Fires a projectile
	@Override
	public ActiveActorDestructible fireProjectile() {
		double projectileX = getLayoutX() + getTranslateX() + PROJECTILE_X_OFFSET;
		double projectileY = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectileX, projectileY);
	}

	// Handles destruction of the plane
	@Override
	public void destroy() {
		super.destroy();
		double explosionX = getLayoutX() + getTranslateX();
		double explosionY = getLayoutY() + getTranslateY();
		BossExplosion userExplosion = new BossExplosion(explosionX, explosionY);

		if (getScene() != null) {
			Group root = (Group) getScene().getRoot();
			root.getChildren().add(userExplosion);
			userExplosion.playAnimation(root);
		} else {
			System.err.println("UserPlane is not part of a Scene. Explosion cannot be added.");
		}
		System.out.println("User plane destroyed! Game over.");
	}

	// Checks if the plane is moving vertically
	private boolean isMovingVertically() {
		return verticalVelocityMultiplier != 0;
	}

	// Checks if the plane is moving horizontally
	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	// Moves the plane up
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	// Moves the plane down
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	// Moves the plane left
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	// Moves the plane right
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	// Stops vertical movement
	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	// Stops horizontal movement
	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	// Returns the number of kills
	public int getNumberOfKills() {
		return numberOfKills;
	}

	// Increments the kill count
	public void incrementKillCount() {
		numberOfKills++;
	}
}
