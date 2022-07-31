package robatortas.code.files.core.console;

import java.util.Arrays;
import java.util.Scanner;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.utils.CrashHandler;
import robatortas.code.files.core.utils.CrashHandler.ErrorType;
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
	
	private String getPart(int startChar, int word) {
		String get = msg.contains(" ") ? msg.split(" ")[word] : msg;
		String substring = get.substring(startChar);
		return substring;
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
	
	private String item;
	
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
			try {
				item = getItemFromInput(3);
				if(item.equals(new Item().getItem(item).getName())) {
					LevelManager.player.inventory.add(new Item().getItem(item));
					writeSysMsg("<Number> " + item + " given to " + LevelManager.player.name);
				}
			} catch(Exception e) {
				writeErr("Inputted Item does not exist");
			}
		}
		if(setCommand(4)) {
			try {
				item = getItemFromInput(4);
				if(item.equals(new Item().getItem(item).getName())) {
					game.level.add(new ItemEntity(LevelManager.player.x, LevelManager.player.y, new Item().getItem(item)));
					writeSysMsg("1 " + item + " given to " + LevelManager.player.name);
				}
			} catch(Exception e) {
				writeErr("Inputted Item does not exist");
			}
		}
		if(setCommand(5)) {
			writeSysMsg(Integer.toString(LevelManager.player.inventory.items.size()));
		}
	}
	
	// CHECKS FOR INVALID COMMAND INPUT
	private void errorHandler() {
		try {
			if(!getCommandList().contains(getPart(1, 0)) && !getPart(0, 0).equals("!")) writeErr("Invalid Command");
		} catch(Exception e) {
			new CrashHandler().handle(e, "Console Error Handler failed", ErrorType.UNHANDLED);
		}
	}
	
	// CONSOLE THREAD MANAGER
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Console");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(running) {
			msg = readInput.nextLine().toLowerCase();
			commands();
		}
	}
}