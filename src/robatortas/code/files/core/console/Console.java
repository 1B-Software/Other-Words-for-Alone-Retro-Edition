package robatortas.code.files.core.console;

import java.util.Arrays;
import java.util.Scanner;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.entities.ItemEntity;
import robatortas.code.files.project.inventory.Item;

// CONSOLE IS IN TESTDEV!

public class Console implements Runnable {
	
	private static String from = "void";
	
	private Scanner readInput = new Scanner(System.in);
	private String msg;
	
	private Thread thread;
	private boolean running = false;
	
	private Item item;
	
	private GameManager game;
	
	public Console(GameManager game) {
		this.game = game;
	}
	
	public static void writeSysMsg(String msg) {
		from = "Console";
		System.out.println("[" + from + "]" + ": " + msg);
	}
	
	public static void writeErr(String err) {
		from = "Console";
		System.err.println("[" + from + "]" + ": " + err);
	}
	
	public static void writePlayerMsg(String msg) {
		from = "Player";
		System.out.println("[" + from + "]" + ": " + msg);
	}

	int commandIndex = 0;
	private boolean setCommand(int index) {
		commandIndex = index;
		if(msg.startsWith("!")) return msg.contains(cmd[index].toLowerCase());
		return false;
	}
	
	private String getCommand(int index) {
		return cmd[index].toString();
	}
	
	// Get Item name from input commandIndex
	private String getItemFromInput(int commandIndex) {
		String item = msg.contains(" ") ? msg.substring("!".concat(getCommand(commandIndex)).length() + 1) : "nullItem";
		return item;
	}
	private String getNumberFromInput(int commandIndex) {
		String number = msg.contains(" ") ? msg.substring("!".concat(getCommand(commandIndex)).length() + 2) : "nullNumber";
		return number;
	}
	
	private String getCommandList() {
		return Arrays.toString(cmd);
	}
	
	// When in need of making a command do certain stuff depending if it's true or false
	private boolean commandSet(int commandIndex) {
		boolean bool = false;
		if("!".concat(getCommand(commandIndex)).concat(" = true").equals(msg)) bool = true;
		if("!".concat(getCommand(commandIndex)).concat(" = false").equals(msg)) bool = false;
		return bool;
	}
	
	// LIST OF COMMANDS
	private static String[] cmd = new String[] {
			"help", "quit", "dev_mode", "get", "drop", "inventory_size"
			};
	
	// COMMAND FUNCTIONS
	public void commands() {
		errorHandler();
		if(!msg.startsWith("!")) writePlayerMsg(msg);
		
		if(setCommand(0)) writeSysMsg("Here is the list of commands: \n" + getCommandList());
		if(setCommand(1)) {
			writeSysMsg("Quitting...");
			System.exit(0);
		}
		if(setCommand(2)) {
			 if(commandSet(2)) {
				 writeSysMsg("DEVMODE set to true");
				 GameManager.DEV_MODE = true;
			 }
			 if(!commandSet(2)) {
				 writeSysMsg("DEVMODE set to false");
				 GameManager.DEV_MODE = false;
			 }
		}
		if(setCommand(3)) {
			item = new Item().getItem(getItemFromInput(3));
			if(item != null) {
				LevelManager.player.inventory.add(item);
				writeSysMsg("<Number> " + item.getName() + " given to " + LevelManager.player.name);
			} else writeErr("Inputted Item doesn't exist.");
		}
		if(setCommand(4)) {
			String item = getItemFromInput(4);
			game.level.add(new ItemEntity(LevelManager.player.x, LevelManager.player.y, new Item().getItem(item)));
		}
		if(setCommand(5)) {
			writeSysMsg(Integer.toString(LevelManager.player.inventory.items.size()));
		}
	}
	
	// CHECKS FOR INVALID COMMAND INPUT
	private void errorHandler() {
		String get = msg.contains(" ") ? msg.split(" ")[0] : msg;
		if(!getCommandList().contains(get.substring(1)) && !get.substring(1).contains("!")) writeErr("Invalid Command");
	}
	
	// CONSOLE THREAD MANAGER
	public synchronized void start() {
		thread = new Thread(this, "Console");
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	public void run() {
		while(running) {
			msg = readInput.nextLine().toLowerCase();
			commands();
		}
	}
}