package robatortas.code.files.core.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.project.entities.mobs.mobArchive.Bee;
import robatortas.code.files.project.entities.mobs.mobArchive.Butterfly;
import robatortas.code.files.project.entities.mobs.mobArchive.Chicken;
import robatortas.code.files.project.entities.mobs.mobArchive.Cow;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.entities.mobs.mobArchive.Sheep;

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
		
		add(new Chicken(4 << 4, 4 << 4));
		add(new Cow(4 << 4, 4 << 4));
		add(new Sheep(4 << 4, 4 << 4));
		
		for(int i = 0; i < 10; i++) add(new Bee(7 << 4, 5 << 4));
		for(int i = 0; i < 10; i++) add(new Butterfly(7 << 4, 5 << 4));
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
	
	@SuppressWarnings("unchecked")
	public void entitiesIteration() {
		entitiesInTiles = new ArrayList[width*height];
		for (int i = 0; i < width*height; i++) {
			entitiesInTiles[i] = new ArrayList<EntityManager>();
		}
	}
}