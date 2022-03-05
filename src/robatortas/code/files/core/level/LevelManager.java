package robatortas.code.files.core.level;

import robatortas.code.files.project.settings.Constants;

public class LevelManager {
	
	public static LevelManager level = new GameLevel(Constants.levelPath);
	
	public LevelManager(String path) {
		loadLevel(path);
	}
	
	public void loadLevel(String path) {
	}
	
	public void loadLevel() {
		
	}
	
	public void tick() {
		
	}
	
	public void render() {
		
	}
	
	public void getLevel() {
		
	}
}
