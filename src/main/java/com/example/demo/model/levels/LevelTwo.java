package com.example.demo.model.levels;

import com.example.demo.model.actors.ActiveActorDestructible;
import com.example.demo.model.actors.Boss;
import com.example.demo.model.actors.EnemyPlane;
import com.example.demo.view.LevelView;

import java.util.List;

/**
 * Represents the second level of the game.
 */

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/backgrounds/background2.jpg";
	private static final int TOTAL_ENEMIES = 15;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.3;
	private static final int PLAYER_INITIAL_HEALTH = 8;

	private Boss boss; // Boss instance
	private boolean bossFightStarted = false; // Tracks if the boss fight has started

	// Initialize LevelTwo
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
			endGame(false);
		} else if (bossFightStarted && boss != null && boss.isDestroyed()) {
			endGame(true);
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

		// Remove all existing enemy planes
		clearAllEnemies();

		// Spawn the boss
		boss = new Boss();
		getRoot().getChildren().add(boss);
		getRoot().getChildren().add(boss.getHealthBar()); // Add the health bar
		getRoot().getChildren().add(boss.getShieldImage()); // Add the shield image if applicable
		System.out.println("The boss has arrived! All enemy planes are cleared. Defeat the boss to win the game!");
	}



	@Override
	protected void updateScene() {
		super.updateScene();

		if (bossFightStarted && boss != null) {
			boss.updateActor();
			boss.updateHealthBarPosition(); // Update the position of the health bar

			// Handle boss firing projectiles
			ActiveActorDestructible projectile = boss.fireProjectile();
			if (projectile != null) {
				getRoot().getChildren().add(projectile);
			}

			// Update and clean up boss projectiles
			List<ActiveActorDestructible> bossProjectiles = boss.getBossProjectiles();
			for (int i = 0; i < bossProjectiles.size(); i++) {
				ActiveActorDestructible bossProjectile = bossProjectiles.get(i);
				bossProjectile.updateActor();

				// Check for collision with the player
				if (bossProjectile.getBoundsInParent().intersects(getUser().getBoundsInParent())) {
					System.out.println("Player hit by boss projectile!");
					getUser().takeDamage(); // Reduce player's health
					bossProjectile.destroy(); // Mark the projectile for destruction
				}

				// Remove destroyed projectiles
				if (bossProjectile.isDestroyed()) {
					getRoot().getChildren().remove(bossProjectile);
					bossProjectiles.remove(i);
					i--; // Adjust the index after removal
				}
			}
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