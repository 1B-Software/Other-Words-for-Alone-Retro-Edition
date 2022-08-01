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
	
	// Gets specified command from the command list
	private String getCommand(int index) {
		return cmd[index].toString();
	}
	
	private String getCommandList() {
		return Arrays.toString(cmd);
	}
	
	// When in need of making a command do certain stuff depending if it's true or false (Command boolean logic)
	private boolean commandSet(int commandIndex) {
		boolean bool = false;
		if("!".concat(getCommand(commandIndex)).concat(" = true").equals(msg)) bool = true;
		if("!".concat(getCommand(commandIndex)).concat(" = false").equals(msg)) bool = false;
		return bool;
	}
	
	// LIST OF COMMANDS
	private static String[] cmd = new String[] {
			"help", "quit", "dev_mode", "get", "spawn", "inventory_size"
			};
	
	// Gets a part of the command
	private String getPart(int startChar, int word) {
		String get = msg.contains(" ") ? msg.split(" ")[word] : msg;
		String substring = get.substring(startChar);
		return substring;
	}
	
	// Parses each part of the input into readable code data
	private void parser() {
		// Gets command
		String command = getPart(1, 0);
		// Gets first value (Entity most probably)
		String first = getPart(0, 1);
		// Gets second value (quantity)
		String second = msg.contains(command + " " + first + " ") ? getPart(0, 2) : "";
		System.out.println(second);
		
		String result = "";
		
		try {

			// Checks if the inputted command is valid
			if(!getCommandList().contains(command) && !getPart(0, 0).equals("!")) writeErr("Invalid Command");
			
			if(!first.equals(new Item().getItem(item).getName())) writeErr("Invalid Item");
		} catch(Exception e) {
//			new CrashHandler().handle(e, "Console Error Handler failed", ErrorType.UNHANDLED);
		}
	}

	private String item;
	// COMMAND FUNCTIONS
	public void commands() {
		errorHandler();
		if(!msg.startsWith("!")) writePlayerMsg(msg);
		
		parser();
		if(setCommand(0)) writeSysMsg("Here is the list of commands: \n" + getCommandList());
		if(setCommand(1)) {
			writeSysMsg("Quitting...");
			System.exit(0);
		}
		
		// Change DevMode variable
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
		
		// Add item to player inventory
		if(setCommand(3)) {
			try {
				item = getPart(0, 1);
				if(item.equals(new Item().getItem(item).getName())) {
					LevelManager.player.inventory.add(new Item().getItem(item));
					writeSysMsg("<Number> " + item + " given to " + LevelManager.player.name);
				}
			} catch(Exception e) {
				writeErr("Inputted Item does not exist");
			}
		}
		
		// Spawn Entity or ItemEntity
		if(setCommand(4)) {
			try {
				item = getPart(0, 1);
				if(item.equals(new Item().getItem(item).getName())) {
					for(int i = 0; i < Integer.parseInt(getPart(0, 2)); i++) {
						game.level.add(new ItemEntity(LevelManager.player.x, LevelManager.player.y, new Item().getItem(item)));
						writeSysMsg(i + " " + item + " spawned at your location");
					}
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