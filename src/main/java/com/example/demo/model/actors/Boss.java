package com.example.demo.model.actors;

import javafx.scene.control.ProgressBar;
import javafx.scene.Group;
import com.example.demo.view.ShieldImage;

import java.util.*;

/**
 * Represents the boss character in the game.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "actors/bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = 0.04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.05;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 200;

	private final List<Integer> movePattern; // Movement pattern for the boss
	private boolean isShielded; // Indicates if the boss is shielded
	private boolean shieldUsed; // Tracks if the shield has been used
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private final ShieldImage shieldImage; // Shield image for the boss
	private ProgressBar healthBar; // Health bar for the boss
	private final List<ActiveActorDestructible> bossProjectiles = new ArrayList<>(); // List of projectiles fired by the boss

	/**
	 * Gets the boss's projectiles.
	 */
	public List<ActiveActorDestructible> getBossProjectiles() {
		return bossProjectiles;
	}

	/**
	 * Initializes the boss with its properties.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldUsed = false;
		initializeMovePattern();

		// Initialize the shield image and health bar
		shieldImage = new ShieldImage(INITIAL_X_POSITION + 100, INITIAL_Y_POSITION + 30);
		healthBar = new ProgressBar(1.0);
		healthBar.getStyleClass().add("progress-bar");
		healthBar.setLayoutX(INITIAL_X_POSITION);
		healthBar.setLayoutY(INITIAL_Y_POSITION - 20);
	}

	/**
	 * Updates the position of the health bar.
	 */
	public void updateHealthBarPosition() {
		healthBar.setLayoutX(getLayoutX() + getTranslateX() - 30);
		healthBar.setLayoutY(getLayoutY() + getTranslateY() - 20);
	}

	/**
	 * Gets the health bar of the boss.
	 */
	public ProgressBar getHealthBar() {
		return healthBar;
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Handles damage taken by the boss.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();

			// Play explosion animation on damage
			double explosionX = getLayoutX() + getTranslateX();
			double explosionY = getLayoutY() + getTranslateY();
			ExplosionAnimation explosion = new ExplosionAnimation(explosionX, explosionY, 200);
			Group root = (Group) getScene().getRoot();
			root.getChildren().add(explosion);
			explosion.playAnimation(root);

			// Update health bar
			double healthPercentage = (double) getHealth() / HEALTH;
			healthBar.setProgress(healthPercentage);
			System.out.println("Boss health: " + getHealth());
		} else {
			System.out.println("Boss is shielded!");
		}
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bossFiresInCurrentFrame()) {
			BossProjectile projectile = new BossProjectile(getProjectileInitialPosition());
			bossProjectiles.add(projectile);
			return projectile;
		}
		return null;
	}

	// Initializes the movement pattern
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	// Updates the shield state
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
		} else if (!shieldUsed && shieldShouldBeActivated()) {
			activateShield();
			shieldImage.showShield();
			System.out.println("Boss shield activated!");
		}
		if (shieldExhausted()) {
			deactivateShield();
			shieldImage.hideShield();
			System.out.println("Boss shield deactivated!");
		}
	}

	// Checks if the shield should be activated
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	// Checks if the shield is exhausted
	private boolean shieldExhausted() {
		return framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD;
	}

	// Activates the shield
	private void activateShield() {
		isShielded = true;
		shieldUsed = true;
	}

	// Deactivates the shield
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();

		shieldImage.setLayoutX(getLayoutX());
		shieldImage.setLayoutY(currentPosition);

		// Ensure boss stays within vertical bounds
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	// Gets the next movement direction
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	// Checks if the boss fires in the current frame
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	// Gets the initial position for the boss's projectile
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	// Checks if the boss is shielded
	public boolean isShielded() {
		return isShielded;
	}

	// Gets the boss's shield image
	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	@Override
	public void destroy() {
		super.destroy();

		// Play boss destruction animation
		double explosionX = getLayoutX() + getTranslateX();
		double explosionY = getLayoutY() + getTranslateY();
		BossExplosion bossExplosion = new BossExplosion(explosionX, explosionY);
		Group root = (Group) getScene().getRoot();
		root.getChildren().add(bossExplosion);
		bossExplosion.playAnimation(root);

		System.out.println("Boss destroyed!");
	}
}
