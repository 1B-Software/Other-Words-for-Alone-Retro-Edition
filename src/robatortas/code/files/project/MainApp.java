package robatortas.code.files.project;

import robatortas.code.files.core.sound.SoundEngine;

public class MainApp {

	// Main Method
	public static void main(String[] args) {
		SoundEngine.enter.play();
		new GameManager();
	}
}