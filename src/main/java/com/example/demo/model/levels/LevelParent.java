package com.example.demo.model.levels;


import java.net.URL;
import java.util.*;

import com.example.demo.controller.Controller;
import com.example.demo.model.actors.Boss;
import com.example.demo.model.interfaces.LevelListener;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import com.example.demo.model.actors.ActiveActorDestructible;
import com.example.demo.model.actors.FighterPlane;
import com.example.demo.model.actors.UserPlane;
import com.example.demo.view.LevelView;

/**
 * Represents the base class for all levels in the game.
 */

public abstract class LevelParent {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	protected final List<ActiveActorDestructible> userProjectiles;
	protected final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	private final LevelView levelView;

    private StackPane pauseMenu;
	private boolean isPaused = false;
	private ImageView pauseButtonImage;

	private Boss boss;
	public Boss getBoss() {
		return boss;
	}

	/**
	 * Initializes the level with a background, screen size, and player health.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {


		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
//		String path = getClass().getResource(backgroundImageName)).toExternalForm()
        Image image;

//        image = new Image("/Users/xinyuanluo/Desktop/maintainable software/CW2024/src/main/resources/images/background1.jpg");
		String val = Objects.requireNonNull(getClass().getResource(backgroundImageName)).toString();
		image = new Image(val);

        this.background = new ImageView(image);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser()); // Add UserPlane to the scene graph
	}

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	private LevelListener levelListener;

	public void setLevelListener(LevelListener listener){
		this.levelListener = listener;
	}
	protected void notifyLevelChange(String newLevelClassName){
		if(levelListener != null){
			levelListener.onLevelChange(newLevelClassName);
		}
	}

	/**
	 * Initializes and returns the scene for the level.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		initializePauseButton(); // Add pause button
		// Add the CSS file to the scene
		scene.getStylesheets().add(getClass().getResource("/styling/BossHealthBar.css").toExternalForm());
		return scene;
	}

	/**
	 * Starts the game loop.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		timeline.stop();
		setLevelListener(levelListener);
		notifyLevelChange(levelName);
	}

	protected void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e ->{
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.LEFT) user.moveLeft();
				if (kc == KeyCode.RIGHT) user.moveRight();
				if (kc == KeyCode.SPACE) fireProjectile();

		});
		background.setOnKeyReleased(e -> {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stopVertical();
				if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) user.stopHorizontal();

		});
		root.getChildren().add(background);
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		if (projectile != null){
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
			}
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	protected void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected double getScreenHeight() {
		return screenHeight;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	protected void clearAllEnemies() {
		for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) { // Iterate over a copy to avoid concurrent modification
			getRoot().getChildren().remove(enemy); // Remove enemy from the scene
		}
		enemyUnits.clear(); // Clear the enemy list
		System.out.println("All enemies have been cleared!");
	}

	protected void endGame(boolean victory) {
		timeline.stop(); // Stop the game loop

		if (victory) {
			// Play the boss explosion animation
			if (boss != null) {
				boss.destroy(); // Trigger the boss explosion

				// Delay before showing the winImage
				delayAndExecute(Duration.seconds(0.5), () -> {
					levelView.showWinImage(); // Show the winImage after the delay
				});
			}
		} else {
			// Ensure UserPlane is part of the scene graph
			if (user.getScene() == null) {
				getRoot().getChildren().add(user); // Temporarily add UserPlane to the scene graph
			}

			// Play the user explosion animation
			user.destroy(); // Trigger the user explosion

			// Delay before showing the gameOverImage
			delayAndExecute(Duration.seconds(0.5), () -> {
				levelView.showGameOverImage(); // Show the gameOverImage after the delay
			});
		}

		// Notify the controller to show the ending screen after the delay
		delayAndExecute(Duration.seconds(1.0), () -> {
			LevelListener listener = levelListener;
			if (listener instanceof Controller) {
				((Controller) listener).showEndingScreen(victory);
			}
		});
	}

	private void initializePauseButton() {
		// Load the image and create an ImageView
		Image pauseImage = new Image(getClass().getResource("/com/example/demo/images/UI/pauseButton.png").toExternalForm());
		ImageView pauseImageView = new ImageView(pauseImage);
		pauseImageView.setFitWidth(50);
		pauseImageView.setFitHeight(50);

		// Create a button with the ImageView as its graphic
		Button pauseButton = new Button();
		pauseButton.setGraphic(pauseImageView);
		pauseButton.setLayoutX(getScreenWidth() - 100); // Top-right corner
		pauseButton.setLayoutY(20);
		pauseButton.setOnAction(e -> showPauseMenu());
		root.getChildren().add(pauseButton);

		initializePauseMenu();
	}

	private void initializePauseMenu() {
		pauseMenu = new StackPane();
		pauseMenu.setVisible(false); // Initially hidden

		// Load the background image
		ImageView backgroundImage = new ImageView(
				new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/backgrounds/PauseMenu.jpeg")).toExternalForm())
		);
		backgroundImage.setFitWidth(getScreenWidth());
		backgroundImage.setFitHeight(getScreenHeight());

		// Create buttons
		Button resumeButton = new Button("Resume");
		resumeButton.setOnAction(e -> resumeGame());

		Button restartButton = new Button("Start Again");
		restartButton.setOnAction(e -> restartLevel());

		Button quitButton = new Button("Quit");
		quitButton.setOnAction(e -> System.exit(0)); // Quit the application

		VBox menuButtons = new VBox(20, resumeButton, restartButton, quitButton);
		menuButtons.setAlignment(Pos.CENTER);

		// Add background and buttons to the StackPane
		pauseMenu.getChildren().addAll(backgroundImage, menuButtons);

		// Set the size of the pause menu
		pauseMenu.setLayoutX(0);
		pauseMenu.setLayoutY(0);
		pauseMenu.setPrefWidth(getScreenWidth());
		pauseMenu.setPrefHeight(getScreenHeight());

		root.getChildren().add(pauseMenu);

		URL buttonCss = getClass().getResource("/styling/Button.css");
		if (buttonCss != null) {
			System.out.println("Button CSS file loaded: " + buttonCss.toExternalForm());
			scene.getStylesheets().add(buttonCss.toExternalForm());
		} else {
			System.err.println("Button CSS file not found: /styling/Button.css");
		}

		// Add CSS style classes to the buttons
		resumeButton.getStyleClass().add("button");
		restartButton.getStyleClass().add("button");
		quitButton.getStyleClass().add("button");

	}


	// Add this method to show the pause menu and stop the timeline
	private void showPauseMenu() {
		isPaused = true;
		pauseMenu.setVisible(true);
		timeline.pause(); // Stop the game loop
	}

	// Add this method to resume the game
	private void resumeGame() {
		isPaused = false;
		pauseMenu.setVisible(false);
		timeline.play(); // Restart the game loop
	}

	// Add this method to restart the current level
	private void restartLevel() {
		// Clear existing state and restart the current level
		isPaused = false;
		pauseMenu.setVisible(false);
		timeline.stop();
		notifyLevelChange(this.getClass().getName()); // Restart the current level
	}

	protected void delayAndExecute(Duration duration, Runnable task) {
		PauseTransition pause = new PauseTransition(duration);
		pause.setOnFinished(e -> task.run());
		pause.play();
	}





}
