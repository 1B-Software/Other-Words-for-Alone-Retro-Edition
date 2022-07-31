package robatortas.code.files.project;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.sound.SoundEngine;

public class MainApp {
	
	private static GameManager game;
	
	// Main Method
	public static void main(String[] args) {
		if(GameManager.DEV_MODE) Console.writeSysMsg("Starting: Other Words for Alone RETRO EDITION in Developer Mode" + "\n");
		else Console.writeSysMsg("Starting: Other Words for Alone RETRO EDITION in Client Mode" + "\n");
		
		SoundEngine.enter.play();
		game = new GameManager();
		new Console(game).start();
	}
}