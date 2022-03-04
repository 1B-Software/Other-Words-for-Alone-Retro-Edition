package robatortas.code.files.core.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheetManager {
	
	public int[] pixels;
	public final int WIDTH, HEIGHT;
	public final int SIZE;
	public String path;
	
	public SpriteSheetManager(String path, int size) {
		this.path = path;
		this.SIZE = size;
		this.WIDTH = size;
		this.HEIGHT = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	public void load() {
		try {
			System.out.println("Loading " + path + " ------->");
			BufferedImage image = ImageIO.read(SpriteSheetManager.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch(IOException e) {
			System.err.println("Unable to load SpriteSheet.");
		}
	}
}
