package com.example.demo.controller;

import com.example.demo.model.interfaces.LevelListener;
import com.example.demo.model.interfaces.MenuListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.model.levels.LevelParent;
import com.example.demo.view.EndingScreen;
import com.example.demo.view.MenuScreen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Controller implements LevelListener, MenuListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.model.levels.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() {
		MenuScreen menuScreen = new MenuScreen(this);
		Scene menuScene = menuScreen.createMenuScene(stage);
		stage.setScene(menuScene);
		stage.show(); // Show the menu scene
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.setLevelListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene); // Set the scene for the level
		myLevel.startGame(); // Start the level game loop
	}

	@Override
	public void onStartGame() {
		try {
			goToLevel(LEVEL_ONE_CLASS_NAME); // Launch the first level
		} catch (Exception e) {
			e.printStackTrace(); // Log any errors during level launch
		}
	}

	@Override
	public void onQuit() {
		System.exit(0); // Exit the application
	}

	@Override
	public void onLevelChange(String newLevelClassName) {
		try {
			goToLevel(newLevelClassName); // Transition to a new level
		} catch (Exception e) {
			e.printStackTrace(); // Log any errors during level transition
		}
	}

	public void showEndingScreen(boolean victory) {
		EndingScreen endingScreen = new EndingScreen(this);
		Scene endingScene = endingScreen.createEndingScene(stage, victory);
		stage.setScene(endingScene); // Display the ending screen
	}
}
