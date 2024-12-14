package com.example.demo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int TOTAL_ENEMIES = 8;
	private static final int KILLS_TO_ADVANCE = 5;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.3;
	private static final int PLAYER_INITIAL_HEALTH = 10;

	private Boss boss; // Boss instance
	private boolean bossFightStarted = false; // Tracks if the boss fight has started

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		if (bossFightStarted) return; // Stop spawning enemies during the boss fight

		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (bossFightStarted && boss.isDestroyed()) {
			winGame();
			System.out.println("Congratulations! You defeated the boss and won the game!");
		} else if (!bossFightStarted && userHasReachedKillTarget()) {
			startBossFight();
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	private void startBossFight() {
		bossFightStarted = true;
		boss = new Boss();
		getRoot().getChildren().add(boss);
		System.out.println("The boss has arrived! Defeat the boss to win the game!");
	}

	@Override
	protected void updateScene() {
		super.updateScene();

		// Update boss during the boss fight
		if (bossFightStarted && boss != null) {
			boss.updateActor(); // Make the boss move and perform actions
		}
	}

	@Override
	protected void handleUserProjectileCollisions() {
		// Call parent method for normal enemy collisions
		super.handleUserProjectileCollisions();

		// Handle collisions with the boss
		if (bossFightStarted && boss != null) {
			for (ActiveActorDestructible projectile : userProjectiles) {
				if (projectile.getBoundsInParent().intersects(boss.getBoundsInParent())) {
					System.out.println("Projectile hit the boss!");
					boss.takeDamage(); // Boss takes damage
					projectile.destroy(); // Remove the projectile
					break; // Stop checking other projectiles after one collision
				}
			}
		}
	}

}
