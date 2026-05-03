package robatortas.code.files.core.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.utils.CrashHandler;
import robatortas.code.files.core.utils.CrashHandler.ErrorType;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.level.Level;
import robatortas.code.files.project.settings.Globals;

public class GameLevel extends LevelManager {
	
	public GameLevel(GameManager game) {
		super(game);
	}

	public void loadLevel() {
//		NoiseMap.main(null);
		levelReader(Globals.levelPath);
		levelPostReader(Globals.levelPathPost);
		levelDoorReader(Globals.levelPathDoors);
		unload();
		entitiesIteration();
		
//		new PlayerRoomLevel(this).init();
		this.currentLevel = new Level(this);
		this.currentLevel.loadCurrentLevel();
	}
	
	public void unload() {
		flushEntities();
	}
	
	BufferedImage image = null;
	public void levelReader(String path) {
		try {
			image = ImageIO.read(GameLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w*h];
			tileData = new int[w*h];
			tileStates = new TileState[w*h];
			for (int i = 0; i < tileStates.length; i++) {
			    tileStates[i] = new TileState();
			}
			image.getRGB(0, 0, w, h, tiles, 0 , w);
		} catch(Exception e) {
			new CrashHandler().handle(e, "Level file is null at location: " + path, ErrorType.SERIOUS);
		}
	}
	
	public void levelPostReader(String path) {
		try {
			image = ImageIO.read(GameLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			postTiles = new int[w*h];
			image.getRGB(0, 0, w, h, postTiles, 0 , w);
		} catch(Exception e) {
			new CrashHandler().handle(e, "Level file is null at location: " + path, ErrorType.SERIOUS);
		}
	}
	
	public void levelDoorReader(String path) {
		try {
			image = ImageIO.read(GameLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			doorTiles = new int[w*h];
			image.getRGB(0, 0, w, h, doorTiles, 0 , w);
		} catch(Exception e) {
			new CrashHandler().handle(e, "Level file is null at location: " + path, ErrorType.SERIOUS);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void entitiesIteration() {
		entitiesInTiles = new ArrayList[width*height];
		for (int i = 0; i < width*height; i++) {
			entitiesInTiles[i] = new ArrayList<EntityManager>();
		}
	}
	public void flushEntities() {
		entities.clear();
	}
}