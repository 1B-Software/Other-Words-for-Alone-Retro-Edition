package robatortas.code.files.project;

import java.util.LinkedList;
import java.util.List;

import discord_sdk.UserActivity;
import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class MainApp {
	
	private static GameManager game;
	
	// Main Method
	public static void main(String[] args) {
		if(GameManager.DEV_MODE) Console.log("Starting: Other Words for Alone RETRO EDITION in Developer Mode" + "\n");
		else Console.log("Starting: Other Words for Alone RETRO EDITION in Client Mode" + "\n");
		
		SoundEngine.enter.play();
		game = new GameManager();
//		discordInit();
		new Console(game).start();
	}
	
	public static void discordInit() {
		new UserActivity();
	}
}