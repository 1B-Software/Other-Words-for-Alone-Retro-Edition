package robatortas.code.files.project;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.save_system.FileSystem;

public class MainApp {
	
	private static GameManager game;
	
	// Main Method
	public static void main(String[] args) {
		if(GameManager.DEV_MODE) Console.log("Starting: Other Words for Alone RETRO EDITION in Developer Mode" + "\n");
		else Console.log("Starting: Other Words for Alone RETRO EDITION in Client Mode" + "\n");
		
		FileSystem file = new FileSystem("res/test.json");
//		fs.createFile("test", "res/");
		file.changeKeyValue("name", "NoFall");
		
		SoundEngine.enter.play();
		
		game = new GameManager();
		game.start();
//		discordInit();
		new Console(game).start();
	}
}