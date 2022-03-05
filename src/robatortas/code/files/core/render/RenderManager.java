package robatortas.code.files.core.render;

import java.util.Random;

import robatortas.code.files.core.level.tiles.TileManager;

// AKA: Screen
public class RenderManager {
	
	private int width, height;
	private int tileSize = 16;
	public int[] pixels;
	
	public int[] tiles = new int[tileSize*tileSize];
	
	private Random random = new Random();
	
	public RenderManager(int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width*height];
		map();
	}
	
	public void map() {
		for(int i = 0; i < tileSize*tileSize; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear(boolean clears) {
		if(clears) {
			for(int i = 0; i < pixels.length; i++) {
				pixels[i] = 0;
			}
		}
	}
	
	public void renderPixel(int xOffset, int yOffset) {
		for(int y = 0; y < height; y++) {
			int yy = y+yOffset;
			if(yy < 0 || yy >= height) continue;
			for(int x = 0; x < width; x++) {
				int xx = x+xOffset;
				if(xx < 0 || xx >= width) continue;
				int i = ((xx >> 4) & 15) + ((yy >> 4) & 15) * 16;
				pixels[x+y*width] = tiles[i];
			}
		}
	}
	
	public void renderTile(int xp, int yp, TileManager tile) {
		for(int y = 0; y < height; y++) {
			int ya = y+yp;
			if(ya < 0 || ya >= height) continue;
			for(int x = 0; x < width; x++) {
				int xa = x+xp;
				if(xa < 0 || xa >= width) continue;
				int i = ((xa >> 4) & 15) + ((ya >> 4) & 15) * 16;
				pixels[xa+ya*width] = tiles[i];
			}
		}
	}
	
	public void renderSprite(int xp, int yp, SpriteManager sprite) {
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = y+yp;
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = x+xp;
				if(xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[x+y*sprite.SIZE];
				pixels[xa+ya*width] = color;
			}
		}
	}
}
