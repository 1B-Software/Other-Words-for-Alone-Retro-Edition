package robatortas.code.files.core.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.utils.CrashHandler;
import robatortas.code.files.core.utils.CrashHandler.ErrorType;

public class SpriteSheetManager {
	
	public int[] pixels;
	private SpriteManager[] sprites;
	public int WIDTH;
	public int HEIGHT;
	public final int SIZE;
	public String path;
	private String nullPath = "/textures/spritesheet/NULL_TEXTURE.png";
	
	public SpriteSheetManager(String path, int size) {
		this.path = path;
		this.SIZE = size;
		this.WIDTH = size;
		this.HEIGHT = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	public SpriteSheetManager(String path, int width, int height) {
		this.path = path;
		this.SIZE = -1;
		this.WIDTH = width;
		this.HEIGHT = height;
		pixels = new int[width*height];
		load();
	}
	
	// NSS
	public int x, y;
	public int frameSize;
	public SpriteSheetManager(SpriteSheetManager sheet, int x, int y, int width, int height, int frameSize) {
		int xx = x * frameSize;
		int yy = y * frameSize;
		int w = width * frameSize;
		int h = height * frameSize;
		this.frameSize = frameSize;
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
		// TILE precision
		for(int y0 = 0; y0 < height; y0++) {
			for(int x0 = 0; x0 < width; x0++) {
				int[] spritePixels = new int[frameSize * frameSize];
				// PIXEL precision
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
	
	// gets Sprites from animation and stores them in an array
	public SpriteManager[] getSprites() {
		return sprites;
	}
	
	int i = 1;
	
	public void load() {
		BufferedImage image = null;
		try {
			System.out.print("Loading " + path + " ------->");
			image = ImageIO.read(SpriteSheetManager.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
			System.out.println(" Loaded");
		} catch(Exception e) {
			new CrashHandler().handle(e, "Unable to locate the path location!", ErrorType.UNEXPECTED);
			if(image == null) {
				Console.writeSysMsg("Locating and using the SpriteSheet in the nullPath variable\n");
				if(i > 0) {
					i--;
					path = nullPath;
					WIDTH = 0;
					HEIGHT = 0;
					load();
				}
				
			}
		}
	}
}