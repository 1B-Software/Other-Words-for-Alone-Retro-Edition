package robatortas.code.files.core.console;

import java.util.Arrays;
import java.util.Scanner;

// CONSOLE IS IN TESTDEV!

public class Console implements Runnable {
	
	private static String from = "void";
	
	private Scanner readInput = new Scanner(System.in);
	private String s;
	
	private Thread thread;
	private boolean running = false;
	
	public static void writeSysMsg(String msg) {
		from = "Console";
		System.out.println("[" + from + "]" + ": " + msg);
	}
	
	public static void writeErr(String err) {
		from = "Console";
		System.err.println(err);
	}
	
	public static void writePlayerMsg(String msg) {
		from = "Player";
		System.out.println("[" + from + "]" + ": " + msg);
	}
	
	private boolean readNextLine(int index) {
		if(s.startsWith("!")) return s.contains(cmd[index].toLowerCase());
		return false;
	}

	private static String[] cmd = new String[] {
			"help", "exit", "hello"
			};
	
	private String getCommandList() {
		return Arrays.toString(cmd);
	}
	
	// COMMANDS ALPHA
	public void commands() {
		if(!s.startsWith("!")) writePlayerMsg(s);
		
		if(readNextLine(0)) {
			writeSysMsg("Here is the list of commands: \n"
					+ getCommandList());
		}
		if(readNextLine(1)) {
			writeSysMsg("Quitting...");
			System.exit(0);
		}
		if(readNextLine(2)) writeSysMsg("Hello fellow human!");
			
	}
	
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
			s = readInput.nextLine().toLowerCase();
			commands();
		}
	}
}
