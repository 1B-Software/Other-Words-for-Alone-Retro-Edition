package robatortas.code.files.project.settings;

public class Globals {
	public static final String TITLE = "Other Words for Alone";
	public static int WIDTH = 250;
	public static int HEIGHT = 220; // 150
	public static final int SCALE = 5;

	/** Internal render buffer runs at RENDER_SCALE * WIDTH/HEIGHT for sub-pixel smoothness */
	public static final int RENDER_SCALE = 4;
	
	public static final String VERSION = "Alpha 1.7";

	public static final int CENTER_X = WIDTH / 2;
	public static final int CENTER_Y = HEIGHT / 2;
	
	public static String levelPath = "/textures/level/player_room";
	public static String levelPathPost = "";
	public static String levelPathDoors = ""; ///textures/level/
	public static String levelDoorsTxt = "";
	
	public int ticks = 0;
}
