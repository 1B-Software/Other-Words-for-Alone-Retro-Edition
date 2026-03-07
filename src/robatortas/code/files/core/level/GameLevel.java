package robatortas.code.files.core.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager.TileState;
import robatortas.code.files.core.utils.CrashHandler;
import robatortas.code.files.core.utils.CrashHandler.ErrorType;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.TileArchive;
import robatortas.code.files.project.entities.mobs.mobArchive.Bee;
import robatortas.code.files.project.entities.mobs.mobArchive.Butterfly;
import robatortas.code.files.project.entities.mobs.mobArchive.Chicken;
import robatortas.code.files.project.entities.mobs.mobArchive.Cow;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.entities.mobs.mobArchive.Sheep;
import robatortas.code.files.project.settings.Globals;

public class GameLevel extends LevelManager {
	
	public GameLevel(String path) {
		super(path);
	}
	
	public void loadLevel(String path) {
//		NoiseMap.main(null);
		levelReader(path);
		levelPostReader(Globals.levelPathPost);
		entitiesIteration();
		
		player = new Player(3<<4, 4<<4, input);
		add(player);
		
		add(new Chicken(100<<4, 100<<4));
		add(new Cow(4 << 4, 4 << 4));
		add(new Sheep(4 << 4, 4 << 4));
		
		for(int i = 0; i < 10; i++) add(new Bee(7 << 4, 5 << 4));
		for(int i = 0; i < 10; i++) add(new Butterfly(7 << 4, 5 << 4));
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
	
	@SuppressWarnings("unchecked")
	public void entitiesIteration() {
		entitiesInTiles = new ArrayList[width*height];
		for (int i = 0; i < width*height; i++) {
			entitiesInTiles[i] = new ArrayList<EntityManager>();
		}
	}
}