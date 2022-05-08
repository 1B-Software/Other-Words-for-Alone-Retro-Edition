package robatortas.code.files.core.render;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.tiles.TileManager;

// AKA: Screen
public class RenderManager {
	
	public int width, height;
	private int tileSize = 16;
	public int[] pixels;
	
	public int xOffset, yOffset;
	
	public RenderManager(int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width*height];
	}
	
	public void clear(boolean clears) {
		if(clears) {
			for(int i = 0; i < pixels.length; i++) {
				pixels[i] = 0;
			}
		}
	}
	
	// Rendering single pixel (DEPRECATED)
	private int[] tiles = new int[tileSize*tileSize];
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
	
	// Rendering tiles
	public void renderTile(int xp, int yp, TileManager tile) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < tile.sprite.width; y++) {
			int ya = y+yp;
			for(int x = 0; x < tile.sprite.height; x++) {
				int xa = x+xp;
				if(xa < -tile.sprite.width || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = tile.sprite.pixels[x+y*tile.sprite.width];
				if(color != 0xffff00ff) pixels[xa+ya*width] = color;
			}
		}
	}
	
	// Rendering Sprites
	public void renderSprite(int xp, int yp, SpriteManager sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < sprite.height; y++) {
			int ya = y+yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = 15 - y;
			for(int x = 0; x < sprite.width; x++) {
				int xa = x+xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = 15 - x;
				if(xa < -sprite.width || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs+ys*sprite.SIZE];
				if(color != 0xffff00ff) pixels[xa+ya*width] = color;
			}
		}
	}
	
	// Rendering Mobs
	public void renderMob(int xp, int yp, Mob mob, SpriteManager sprite) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = y+yp;
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = x+xp;
				if(xa < -32 || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = mob.getSprite().pixels[x + y * sprite.width];
				if(color != 0xffff00ff) pixels[xa+ya*width] = color;
			}
		}
	}
	
	// Sets these offsets to the values in the level rendering method
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}