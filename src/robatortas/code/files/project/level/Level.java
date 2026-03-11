package robatortas.code.files.project.level;

import robatortas.code.files.core.level.LevelManager;

public class Level {

	protected LevelManager levelManager;
	
	public static String currentLevelName = "player_room";
	
	String path = "";
	protected String name = ""; // Name of the folder of the level
	int sX, sY; // Starting X and Y for player
	public static float environmentLight = 1f;
	
	
	public Level(LevelManager level) {
		this.levelManager = level;
	}
	
	// Name of the folder of the level
	public Level(String name, LevelManager level) {
		this.levelManager = level;
		this.name = name;
	}
	
	// Where objects and entities are initialized
	public void init() {
	}
	
	// Selects which level to load
	public void loadCurrentLevel() {
		new LevelSelector(this.levelManager);
	}
}