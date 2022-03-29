package robatortas.code.files.core.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheetManager {
	
	public int[] pixels;
	private SpriteManager[] sprites;
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
	
	public SpriteSheetManager(SpriteSheetManager sheet, int x, int y, int width, int height, int frameSize) {
		int xx = x * frameSize;
		int yy = y * frameSize;
		int w = width * frameSize;
		int h = height * frameSize;
		SIZE = -1;
		this.WIDTH = w;
		this.HEIGHT = h;
		pixels = new int[w*h];
		
		// Gets single sprite
		for(int y0 = 0; y0 < h; y0++) {
			//y position
			int yp = yy + y0;
			for(int x0 = 0; x0 < w; x0++) {
				//x position
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}
		
		int frames = 0;
		
		sprites = new SpriteManager[width * height];
		//TILE precision
		for(int y0 = 0; y0 < height; y0++) {
			for(int x0 = 0; x0 < width; x0++) {
				int[] spritePixels = new int[frameSize * frameSize];
				//PIXEL precision
				for(int y1 = 0; y1 < frameSize; y1++) {
					for(int x1 = 0; x1 < frameSize; x1++) {
						spritePixels[x1 + y1 * frameSize] = pixels[(x1 + x0 * frameSize) + (y1 + y0 * frameSize) * frameSize];
					}
				}
				SpriteManager sprite = new SpriteManager(spritePixels, frameSize, frameSize);
				sprites[frames++] = sprite;
			}
		}
	}
	
	//gets Sprites from animation and stores them in an array
	public SpriteManager[] getSprites() {
		return sprites;
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
