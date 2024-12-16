package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle(TITLE); // Set the title of the application
		stage.setResizable(false); // Make the window non-resizable
		stage.setHeight(SCREEN_HEIGHT); // Set the height of the window
		stage.setWidth(SCREEN_WIDTH); // Set the width of the window
		Controller myController = new Controller(stage);
		myController.launchGame(); // Launch the game
	}

	public static void main(String[] args) {
		launch(); // Start the JavaFX application
	}
}
