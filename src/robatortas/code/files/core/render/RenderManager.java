package robatortas.code.files.core.render;

import java.util.Random;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;

// AKA: Screen
public class RenderManager {
	
	public int width, height;
	private int tileSize = 16;
	public int[] pixels;
	
	public int xOffset, yOffset;
	
	public Random random = new Random();
	
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
		for(int y = 0; y < 50; y++) {
			int yy = y+yOffset;
			if(yy < 0 || yy >= height) continue;
			for(int x = 0; x < 50; x++) {
				int xx = x+xOffset;
				if(xx < 0 || xx >= width) continue;
				int i = ((xx >> 4) & 15) + ((yy >> 4) & 15) * 16;
				pixels[xx+yy*width] = 0x32ff00ff;
			}
		}
	}
	
	public void renderBox(int xp, int yp, int w, int h, int color) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < h; y++) {
			int ya = y+yp;
			for(int x = 0; x < w; x++) {
				int xa = x+xp;
				if(xa < -w || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				pixels[xa+ya*width] = color;
			}
		}
	}
	
	// Rendering tiles
	public void renderTile(int xp, int yp, TileManager tile) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < tile.sprite.height; y++) {
			int ya = y+yp;
			for(int x = 0; x < tile.sprite.width; x++) {
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
			int ya = y + yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = (sprite.height- 1) - y;
			for(int x = 0; x < sprite.width; x++) {
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = (sprite.width - 1) - x;
				if(xa < -sprite.width || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs+ys*sprite.width];
				if(color != 0xffff00ff) pixels[xa+ya*width] = color;
			}
		}
	}
	
	// Rendering Mobs
	public void renderMob(int xp, int yp, Mob mob, SpriteManager sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < sprite.height; y++) {
			int ya = y+yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = (sprite.height-1) - y;
			for(int x = 0; x < sprite.width; x++) {
				int xa = x+xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = (sprite.width-1) - x;
				if(xa < -sprite.width || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = mob.getSprite().pixels[xs + ys * sprite.width];
				if(color != 0xffff00ff) {
					pixels[xa+ya*width] = color;
					if(mob.hurtTime > 0) pixels[xa+ya*width] = random.nextInt(0xffffff);
				}
			}
		}
	}
	
	public void renderColor(int xp, int yp, SpriteManager sprite, int flip, int inputColor) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < sprite.height; y++) {
			int ya = y + yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = (sprite.height- 1) - y;
			for(int x = 0; x < sprite.width; x++) {
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = (sprite.width - 1) - x;
				if(xa < -sprite.width || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs + ys * sprite.width];
				if(color != 0xffff00ff) pixels[xa+ya*width] = inputColor;
			}
		}
	}
	
	public void renderColorRelativeToLocation(int xp, int yp, SpriteManager sprite, int shade, int flip, LevelManager level) {
		int shadedColor = 0;
		for(int yyy = 0; yyy < level.getLevel(xp >> 4, yp >> 4).sprite.height; yyy++) {
			for(int xxx = 0; xxx < level.getLevel(xp >> 4, yp >> 4).sprite.width; xxx++) {
				int finlColor = level.getLevel(xp >> 4, yp >> 4).sprite.pixels[xxx+yyy*level.getLevel(xp >> 4, yp >> 4).sprite.width];
				int r = (finlColor & 0xff0000) >> 16;
				int g = (finlColor & 0xff00) >> 8;
				int b = (finlColor & 0xff);
				if(shade == 0) shade = 30; // DEFAULT VALUE
				shadedColor = (r - shade) << 16 | (g - shade) << 8 | (b - shade);
			}
		}
		
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < sprite.height; y++) {
			int ya = y + yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = (sprite.height- 1) - y;
			for(int x = 0; x < sprite.width; x++) {
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = (sprite.width - 1) - x;
				if(xa < -sprite.width || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs + ys * sprite.width];
				if(color != 0xffff00ff) pixels[xa+ya*width] = shadedColor;
			}
		}
	}
	
	// Sets these offsets to the values in the level rendering method
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}