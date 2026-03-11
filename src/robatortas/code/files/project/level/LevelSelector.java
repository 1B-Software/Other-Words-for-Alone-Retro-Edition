package robatortas.code.files.project.level;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.level.levels.OutsideLevel;
import robatortas.code.files.project.level.levels.PlayerRoomLevel;
import robatortas.code.files.project.settings.Globals;

public class LevelSelector {

	private LevelManager levelManager;
	private Level currentLevel;
	
	private List<Level> allLevels = new LinkedList<>();
	private List<String> allLevelNames = new LinkedList<>();
	
	// List of Levels
	Level playerRoomLevel;
	Level outsideLevel;
	
	
	public LevelSelector(LevelManager level) {
		this.levelManager = level;
		
		addLevel();
		Console.log("LEVEL NAMES: " + getLevel("player_room"));
		this.currentLevel = getCurrentLevel();
		Console.log("INIT LEVEL" + currentLevel);
		currentLevel.init();
		
	}
	
	public Level getCurrentLevel() {
		for(int i = 0; i < allLevels.size(); i++) {
			if(Globals.levelPath.contains(allLevels.get(i).name)) {
				return allLevels.get(i);
			}
		}
		return null;
	}
	
	public Level getLevel(String name) {
		for(int i = 0; i < allLevels.size(); i++) {
			if(allLevels.get(i).name.equals(name)) return allLevels.get(i);
		}
		return null; // TODO: CHANGE THIS SOON TO A NULL LEVEL OR SMTH
	}
	
	public void addLevel() {
		this.playerRoomLevel = new PlayerRoomLevel("player_room", levelManager);
		this.outsideLevel = new OutsideLevel("outside", levelManager);
		
		allLevels.add(playerRoomLevel);
		allLevels.add(outsideLevel);
	}
	
	// Gets all the names of the levels. DEPRECATED
	private List<String> acknowledgeAllLevels() {
		String initDir = "res/textures/level/";
		File file = new File(initDir);
		File[] allFilesInDir = file.listFiles();
		for(File f : allFilesInDir) {
			if(f.isDirectory()) {
				this.allLevelNames.add(f.getName());
			}
		}
		return this.allLevelNames;
	}
}
