package robatortas.code.files.core.console;

import java.util.Scanner;

// CONSOLE IS IN TESTDEV!

public class Console {

	private static String GREEN = "\033[32m";
	
	private static String from = "void";
	
	public static void writeSysMsg(String msg) {
		from = "Console";
		System.out.println("[" + from + "]" + ": " + msg);
	}
	
	public static void writeErr(String err) {
		from = "Console";
		System.err.println(err);
	}
	
	public static void writePlayerMsg(String msg) {
//		from = Player.name;
		System.out.println("[" + from + "]" + ": " + msg);
	}
	
	private static String[] cmd = new String[] {
			"help", "exit", "hello world"
			};
	
	// COMMANDS ALPHA
	public static void commands() {
		if(readNextLine(0)) writeSysMsg(" I am glad to help here, tho I can't atm!");
		if(readNextLine(1)) writeSysMsg(" Quitting..."); System.exit(0);
		if(readNextLine(2)) writeSysMsg(" Hello fellow human!");
	}
	
	private static boolean readNextLine(int index) {
		Scanner readInput = new Scanner(System.in);
		String s = readInput.nextLine().toLowerCase();
		
		return s.contains(cmd[index].toLowerCase());
	}
}
