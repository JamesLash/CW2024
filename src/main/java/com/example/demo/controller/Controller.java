package com.example.demo.controller;

import com.example.demo.LevelListener;
import com.example.demo.LevelParent;
import com.example.demo.MenuScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.MenuListener;
import com.example.demo.controller.Main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Controller implements LevelListener, MenuListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() {
		MenuScreen menuScreen = new MenuScreen(this);
		Scene menuScene = menuScreen.createMenuScene(stage);
		stage.setScene(menuScene);
		stage.show();
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.setLevelListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	@Override
	public void onStartGame() {
		try {
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			// Handle exception, e.g., show error dialog
		}
	}

	@Override
	public void onQuit() {
		System.exit(0);
	}

	@Override
	public void onLevelChange(String newLevelClassName) {
		try {
			goToLevel(newLevelClassName);
		} catch (Exception e) {
			// Handle exception, e.g., show error dialog
			e.printStackTrace();
		}
	}
}