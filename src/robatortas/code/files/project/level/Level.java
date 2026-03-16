package robatortas.code.files.project.level;

import java.awt.List;
import java.util.LinkedList;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;

public class Level {

	public LevelManager levelManager;
	
	public static String currentLevelName = "player_room";
	
	public LevelSelector levelSelector;
	
	public LinkedList<EntityManager> entitiesInLevel = new LinkedList<>();
	
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
	
	public void add(EntityManager e) {
		e.init(levelManager);
		e.removed = false;
		entitiesInLevel.add(e);
		levelManager.entities.add(e);

		int xp = (int)e.x >> 4;
		int yp = (int)e.y >> 4;

		levelManager.insertEntity(xp, yp, e);
	}
	
	public void remove(EntityManager e) {
		e.removed = true;
		e.remove();
		entitiesInLevel.remove(e);
		levelManager.entities.remove(e);

		int xp = (int)e.x >> 4;
		int yp = (int)e.y >> 4;

		levelManager.removeEntity(xp, yp, e);
	}
	
	// Where objects and entities are initialized
	public void init() {
	}
	
	public void update() {
	}
	
	public void render(float x, float y, RenderManager screen) {
	}
	
	// Selects which level to load
	public void loadCurrentLevel() {
		this.levelSelector = new LevelSelector(this.levelManager);
	}
}