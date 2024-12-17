package com.example.demo.model.levels;

import com.example.demo.model.actors.ActiveActorDestructible;
import com.example.demo.model.actors.EnemyPlane;
import com.example.demo.view.LevelView;

/**
 * Represents the first level of the game.
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/background1.jpg";
	private static final String LEVEL_TWO_CLASS_NAME = "com.example.demo.model.levels.LevelTwo";
	private static final int TOTAL_ENEMIES = 10;
	private static final int KILLS_TO_ADVANCE = 15;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	// Initialize LevelOne
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	// Check if the game is over
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			endGame(false);
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(LEVEL_TWO_CLASS_NAME);
		}
	}

	// Set up friendly units
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	// Spawn enemy units
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	// Create a LevelView instance
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	// Check if user has reached the target kills
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
