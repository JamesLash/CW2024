package com.example.demo.model.actors;

import javafx.scene.control.ProgressBar;
import javafx.scene.Group;
import com.example.demo.view.ShieldImage;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = 0.04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.05; // Reduced for realism
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 200; // Duration for shield

	private final List<Integer> movePattern;
	private boolean isShielded;
	private boolean shieldUsed; // Tracks if the shield has been used
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private final ShieldImage shieldImage;
	private ProgressBar healthBar;

	private final List<ActiveActorDestructible> bossProjectiles = new ArrayList<>();

	public List<ActiveActorDestructible> getBossProjectiles() {
		return bossProjectiles;
	}

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldUsed = false; // Shield has not been used yet
		initializeMovePattern();

		shieldImage = new ShieldImage(INITIAL_X_POSITION + 100, INITIAL_Y_POSITION + 30);

		// Initialize the health bar
		healthBar = new ProgressBar(1.0); // Full health initially
		healthBar.getStyleClass().add("progress-bar");

		// Position the health bar relative to the boss
		healthBar.setLayoutX(INITIAL_X_POSITION);
		healthBar.setLayoutY(INITIAL_Y_POSITION - 20); // Slightly above the boss
	}

	// Method to update health bar position
    public void updateHealthBarPosition() {
		healthBar.setLayoutX(getLayoutX() + getTranslateX() - 30);
		healthBar.setLayoutY(getLayoutY() + getTranslateY() - 20); // Keep it above the boss
	}

	// Getter for the health bar
	public ProgressBar getHealthBar() {
		return healthBar;
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
			double explosionX = getLayoutX() + getTranslateX();
			double explosionY = getLayoutY() + getTranslateY();

			// Enlarge explosion size for boss hits (e.g., 200px)
			ExplosionAnimation explosion = new ExplosionAnimation(explosionX, explosionY, 200);
			Group root = (Group) getScene().getRoot();
			root.getChildren().add(explosion);
			explosion.playAnimation(root);

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
			return projectile; // Return the projectile so it can be added to the scene
		}
		return null;
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
		} else if (!shieldUsed && shieldShouldBeActivated()) { // Shield can only be used once
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

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		shieldUsed = true; // Mark shield as used
	}

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
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

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

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	public boolean isShielded() {
		return isShielded;
	}

	public ShieldImage getShieldImage() {
		return shieldImage;
	}

	@Override
	public void destroy() {
		super.destroy();
		double explosionX = getLayoutX() + getTranslateX();
		double explosionY = getLayoutY() + getTranslateY();

		// Bigger explosion for boss destruction
		BossExplosion bossExplosion = new BossExplosion(explosionX, explosionY);
		Group root = (Group) getScene().getRoot();
		root.getChildren().add(bossExplosion);
		bossExplosion.playAnimation(root);

		System.out.println("Boss destroyed!");
	}

}
