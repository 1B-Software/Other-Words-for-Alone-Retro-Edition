package robatortas.code.files.core.render;

import java.util.Random;

import robatortas.code.files.core.level.tiles.TileManager;

// AKA: Screen
public class RenderManager {
	
	public int width, height;
	private int tileSize = 16;
	public int[] pixels;
	
	public int xOffset, yOffset;
	
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
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y+yp;
			for(int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x+xp;
				if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = tile.sprite.pixels[x+y*tile.sprite.SIZE];
				pixels[xa+ya*width] = color;
			}
		}
	}
	
	public void renderSprite(int xp, int yp, SpriteManager sprite) {
		xp -= xOffset;
		yp -= yOffset;
		
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
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
