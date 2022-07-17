package robatortas.code.files.project;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.sound.SoundEngine;

public class MainApp {
	
	private static GameManager game;
	
	// Main Method
	public static void main(String[] args) {
		SoundEngine.enter.play();
		game = new GameManager();
		new Console(game).start();
	}
}