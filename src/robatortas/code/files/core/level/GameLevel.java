package robatortas.code.files.core.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.project.entities.mobs.mobArchive.Chicken;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.TerrainGen.NoiseMap;

public class GameLevel extends LevelManager {
	
	public GameLevel(String path) {
		super(path);
	}
	
	public void loadLevel(String path) {
//		NoiseMap.main(null);
		levelReader(path);
		entitiesIteration();
		
		player = new Player(10, 10, input);
		add(player);
		
		add(new Chicken(10, 10));
	}
	
	public void levelReader(String path) {
		try {
			BufferedImage image = ImageIO.read(GameLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w*h];
			image.getRGB(0, 0, w, h, tiles, 0 , w);
		} catch(IOException e) {
			System.err.println("Exception: Unable to read level file," + "\n" + "JVM Log: " + e.getMessage());
		}
	}
	
	public void entitiesIteration() {
		entitiesInTiles = new ArrayList[width*height];
		for (int i = 0; i < width*height; i++) {
			entitiesInTiles[i] = new ArrayList<EntityManager>();
		}
	}
}