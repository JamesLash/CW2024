package com.example.demo.model.actors;

import javafx.scene.Group;

/**
 * Represents an enemy plane.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "actors/enemyplane.png";
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	// Initialize the enemy plane
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	// Updates the position of the enemy plane
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	// Fires a projectile
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	// Updates the state of the enemy plane
	@Override
	public void updateActor() {
		updatePosition();
	}

	// Handles destruction of the enemy plane
	@Override
	public void destroy() {
		super.destroy();
		double explosionX = getLayoutX() + getTranslateX();
		double explosionY = getLayoutY() + getTranslateY();
		ExplosionAnimation explosion = new ExplosionAnimation(explosionX, explosionY);
		Group root = (Group) getScene().getRoot();
		root.getChildren().add(explosion);
		explosion.playAnimation(root);
		System.out.println("Enemy destroyed!");
	}
}
