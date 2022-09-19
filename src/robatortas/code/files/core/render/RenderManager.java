package robatortas.code.files.core.render;

import java.util.Random;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.utils.CrashHandler;
import robatortas.code.files.project.archive.SheetArchive;

/**<NEWLINE>
 * RenderManager (AKA: Screen):
 * <br>
 * 
 * Contains all methods that take care of rendering everything onto the screen.
 * <br>
 * After all pixels are stored on the RenderManager's PIXELS array
 * <br>
 * the GameManager class's 
 * <br>
 * ({@link robatortas.code.files.project.GameManager}) 
 * <br>
 * PIXELS array will get filled with RenderManager's PIXELS[]
 */

public class RenderManager {
	
	public int width, height;
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
	
	// TODO: LATER!
	public void debug(int xp, int yp, int w, int h, int color, int perimeter) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < h; y++) {
			int ya = y+yp;
			for(int x = 0; x < w; x++) {
				int xa = x+xp;
				if(xa < -w || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				
				// dejame pienso...
				pixels[xa+ya*width] = color;
//				if(color != 0xffff00ff) pixels[xa+ya*width] = color;
			}
		}
	}
	
	public void renderBox(int xp, int yp, int w, int h, int color, boolean fixed) {
		// fixed meaning that it is literally attached to the screen
		if(!fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
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
	
	/**<NEWLINE> 
	 * <b>renderSprite function on RenderManager class</b>
	 * <br><br>
	 * Rendering Sprites
	 * @param xp The x position
	 * @param yp The y position
	 * @param sprite The sprite that will be rendered
	 * @param scale The size of the sprite
	 * @param flip Flips the sprite; 1 flips it on x, 2 flips it on y, 3 flips it on x & y
	 */
	public void renderSprite(int xp, int yp, SpriteManager sprite, int scale, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		
		int[] scaledPixels = scale(sprite.pixels, sprite.width, sprite.height, scale);
		
		for(int y = 0; y < sprite.height; y++) {
			int ya = y + yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = 15 - y;
			for(int x = 0; x < sprite.width; x++) {
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = 15 - x;
				if(xa < - sprite.width || xa >= width || ya < - 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = 0;
				
				if(scale == 0) color = sprite.pixels[xs + ys * sprite.width];
				else color = scaledPixels[xs + ys * sprite.width];
				if(color == 0) color = 0xffff00ff;
				if(color != 0xffff00ff && scale <= sprite.width) pixels[(xa + (sprite.width-scale))+(ya + (sprite.height-scale))*width] = color;
			}
		}
	}
	
	public void renderSpriteSheet(int xp, int yp, SpriteSheetManager sheet, int flip, boolean fixed) {
		if(!fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for(int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + yp;
			int ys = y;
			for(int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = 15 - x;
				if(xa < - sheet.WIDTH || xa >= width || ya < - 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sheet.pixels[xs + ys * sheet.WIDTH];
				
				if(color != 0xffff00ff) pixels[xa + ya * width] = color;
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
	
	public void renderFont(int xp, int yp, SpriteManager sprite, int scale, int color, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		
		for(int y = 0; y < scale; y++) {
			int ya = y + yp;
			int ys = y;
			if(flip == 2 || flip == 3) ys = 15 - y;
			for(int x = 0; x < scale; x++) {
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = 15 - x;
				if(xa < - scale || xa >= width || ya < - 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int colorPix = sprite.pixels[xs + ys * sprite.width];
				if (colorPix != 0xffff00ff) {
//					if(color == 0) if(scale <= sprite.width) pixels[(((xa*scale)/sprite.width)+(sprite.width-scale))+(((ya*scale)/sprite.height)+(sprite.height-scale))*width] = colorPix;
//					else if(scale <= sprite.width) pixels[(((xa*scale)/sprite.width)+(sprite.width-scale))+(((ya*scale)/sprite.height)+(sprite.height-scale))*width] = color;
					if(color != 0) pixels[xa+ya*width] = color;
					else pixels[xa+ya*width] = 0xff000000;
				}
			}
		}
	}
	
	public void renderScaled(int xp, int yp, SpriteManager sprite, int scale, int flip) {
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
				if(color != 0xffff00ff) {
					if(scale <= sprite.width) pixels[(((xa*scale)/sprite.width)+(sprite.width-scale))+(((ya*scale)/sprite.height)+(sprite.height-scale))*width] = color;
				}
			}
		}
	}
	
	@Deprecated
	// PPS (Per Pixel Scaling) Algorithm
	public int[] scale(int[] pixels, int width, int height, int scale) {
		int[] scaledPixels = new int[width*height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int color = 0;
				
				color = pixels[x + y * width];
				int algorithm = ((x*scale)/width) + ((y*scale)/height) * width;
				if(color != 0xffff00ff) scaledPixels[algorithm] = color;
			}
		}
		return scaledPixels;
	}
	
	// Sets these offsets to the values in the level rendering method
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}