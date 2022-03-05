package robatortas.code.files.core.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameLevel extends LevelManager {
	
	private String path;
	
	public GameLevel(String path) {
		super(path);
	}
	
	public void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(GameLevel.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			
			int[] pixels = new int[w*h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Unable to load level file parameters.");
		}
	}
}
