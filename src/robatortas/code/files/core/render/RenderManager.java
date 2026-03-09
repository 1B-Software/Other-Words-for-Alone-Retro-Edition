package robatortas.code.files.core.render;

import java.util.Random;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.utils.CrashHandler;
import robatortas.code.files.core.utils.MathUtils;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.settings.Globals;

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

	/** World viewport dimensions (used for camera/viewport logic) */
	public int width, height;
	/** Internal pixel buffer dimensions (width * RENDER_SCALE) */
	public int pixelWidth, pixelHeight;
	public int[] pixels;

	public float[] lightmapR, lightmapG, lightmapB;
	
	public float xOffset, yOffset;

	public Random random = new Random();

	public RenderManager(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixelWidth = width * Globals.RENDER_SCALE;
		this.pixelHeight = height * Globals.RENDER_SCALE;

		pixels = new int[pixelWidth * pixelHeight];
		lightmapR = new float[pixelWidth * pixelHeight];
		lightmapG = new float[pixelWidth * pixelHeight];
		lightmapB = new float[pixelWidth * pixelHeight];
	}

	public void clear(boolean clears) {
		if(clears) {
			for(int i = 0; i < pixels.length; i++) {
				pixels[i] = 0;
			}
			for(int i = 0; i < lightmapR.length; i++) {
				lightmapR[i] = lightmapG[i] = lightmapB[i] = 0.3f;
			}
		}
	}

	public void applyLighting() {
	    for (int i = 0; i < pixels.length; i++) {
	        float b = lightmapR[i];
	        int c = pixels[i];
	        int r = Math.min(255, (int)(((c >> 16) & 0xFF) * lightmapR[i]));
	        int g = Math.min(255, (int)(((c >> 8)  & 0xFF) * lightmapG[i]));
	        int bl = Math.min(255, (int)(( c       & 0xFF) * lightmapB[i]));
	        int a = (pixels[i] >> 24) & 0xFF;
	        pixels[i] = (a << 24) | (r << 16) | (g << 8) | bl;
	    }
	}
	
	
	private int blendPixel(int dst, int src) {
	    int srcA = (src >> 24) & 0xFF;
	    if(srcA == 255) return src;   // fully opaque, skip math
	    if(srcA == 0)   return dst;   // fully transparent, skip

	    float a = srcA / 255.0f;
	    int srcR = (src >> 16) & 0xFF;
	    int srcG = (src >>  8) & 0xFF;
	    int srcB =  src        & 0xFF;
	    int dstR = (dst >> 16) & 0xFF;
	    int dstG = (dst >>  8) & 0xFF;
	    int dstB =  dst        & 0xFF;
	    
	    

	    int r = (int)(srcR * a + dstR * (1 - a));
	    int g = (int)(srcG * a + dstG * (1 - a));
	    int b = (int)(srcB * a + dstB * (1 - a));

	    return 0xFF000000 | (r << 16) | (g << 8) | b;
	}

	// TODO: LATER!
	public void debug(int xp, int yp, int w, int h, int color, int perimeter) {
		int rs = Globals.RENDER_SCALE;
		int sxp = (int)((xp - xOffset) * rs);
		int syp = (int)((yp - yOffset) * rs);
		w *= rs;
		h *= rs;

		for(int y = 0; y < h; y++) {
			int ya = y+syp;
			for(int x = 0; x < w; x++) {
				int xa = x+sxp;
				if(xa < -w || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;

				// dejame pienso...
				pixels[xa+ya*pixelWidth] = color;
//				if(color != 0xffff00ff) pixels[xa+ya*pixelWidth] = color;
			}
		}
	}

	public void renderBox(float xp, float yp, int w, int h, int color, int alpha, boolean fixed) {
		int rs = Globals.RENDER_SCALE;
		// fixed meaning that it is literally attached to the screen
		if(!fixed) {
			xp = (xp - xOffset) * rs;
			yp = (yp - yOffset) * rs;
		} else {
			xp *= rs;
			yp *= rs;
		}
		w *= rs;
		h *= rs;

		for(int y = 0; y < h; y++) {
			float ya = y+yp;
			for(int x = 0; x < w; x++) {
				float xa = x+xp;
				if(xa < -w || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;
				
				int srcA = (color >> 24) & 0xFF;
				srcA = (srcA * alpha) / 255;
				color = (srcA << 24) | (color & 0x00FFFFFF);
				pixels[(int)xa+(int)ya*pixelWidth] = blendPixel(pixels[(int)xa+(int)ya*pixelWidth], color);
			}
		}
	}

	// Rendering tiles
	public void renderTile(float xp, float yp, float scale, TileManager tile) {
		int rs = Globals.RENDER_SCALE;
		xp = (xp - xOffset) * rs;
		yp = (yp - yOffset) * rs;
		scale *= rs;

		int outW = (int)(tile.sprite.width  * scale);
		int outH = (int)(tile.sprite.height * scale);

		for(int dy = 0; dy < outH; dy++) {
			float ya = dy + yp;
			if(ya < 0 || ya >= pixelHeight) continue;
			int ys = (int)(dy / scale);
			for(int dx = 0; dx < outW; dx++) {
				float xa = dx + xp;
				if(xa < 0 || xa >= pixelWidth) continue;
				int xs = (int)(dx / scale);

				int color = tile.sprite.pixels[xs + ys * tile.sprite.width];
				if(color == 0xffff00ff) continue;
				if(color != 0) {
					int srcA = (color >> 24) & 0xFF;
					srcA = (srcA * tile.sprite.alpha) / 255;
					color = (srcA << 24) | (color & 0x00FFFFFF);
				}
				pixels[(int)xa + (int)ya * pixelWidth] = blendPixel(pixels[(int)xa + (int)ya * pixelWidth], color);
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
	public void renderSprite(float xp, float yp, SpriteManager sprite, float scale, int flip) {
		int rs = Globals.RENDER_SCALE;
		xp = (xp - xOffset) * rs;
		yp = (yp - yOffset) * rs;
		scale *= rs;

		int outW = (int)(sprite.width  * scale);
		int outH = (int)(sprite.height * scale);

		// Nearest-neighbor -> iterate output pixels, map back to source
		for(int dy = 0; dy < outH; dy++) {
			float ya = dy + yp;
			if(ya < 0 || ya >= pixelHeight) continue;
			float ys = (int)(dy / scale);
			if(flip == 2 || flip == 3) ys = (sprite.height - 1) - ys;
			for(int dx = 0; dx < outW; dx++) {
				float xa = dx + xp;
				if(xa < 0 || xa >= pixelWidth) continue;
				int xs = (int)(dx / scale);
				if(flip == 1 || flip == 3) xs = (sprite.width - 1) - xs;

				int color = sprite.pixels[xs + (int)ys * sprite.width];
				if(color == 0xffff00ff) continue;
				int srcA = (color >> 24) & 0xFF;
				srcA = (srcA * sprite.alpha) / 255;
				color = (srcA << 24) | (color & 0x00FFFFFF);
				pixels[(int)xa + (int)ya * pixelWidth] = blendPixel(pixels[(int)xa + (int)ya * pixelWidth], color);
			}
		}
	}

	// Renders a sub-region of a sprite (used for quadrant-based tile rendering)
	public void renderSpriteRegion(float xp, float yp, SpriteManager sprite, int srcX, int srcY, int srcW, int srcH, float scale) {
		int rs = Globals.RENDER_SCALE;
		xp = (xp - xOffset) * rs;
		yp = (yp - yOffset) * rs;
		scale *= rs;
		
		int outW = (int)(srcW * scale);
		int outH = (int)(srcH * scale);
		
		for(int dy = 0; dy < outH; dy++) {
			float ya = dy + yp;
			if(ya < 0 || ya >= pixelHeight) continue;
			int ys = srcY + (int)(dy / scale);
			for(int dx = 0; dx < outW; dx++) {
				float xa = dx + xp;
				if(xa < 0 || xa >= pixelWidth) continue;
				int xs = srcX + (int)(dx / scale);
				int color = sprite.pixels[xs + ys * sprite.width];
				if(color == 0xffff00ff) continue;
				if(color != 0) {
					int srcA = (color >> 24) & 0xFF;
					srcA = (srcA * sprite.alpha) / 255;
					color = (srcA << 24) | (color & 0x00FFFFFF);
				}
				pixels[(int)xa + (int)ya * pixelWidth] = blendPixel(pixels[(int)xa + (int)ya * pixelWidth], color);
			}
		}
	}

	public void renderSpriteSheet(int xp, int yp, SpriteSheetManager sheet, int flip, boolean fixed) {
		int rs = Globals.RENDER_SCALE;
		float sxp, syp;
		if(!fixed) {
			sxp = (xp - xOffset) * rs;
			syp = (yp - yOffset) * rs;
		} else {
			sxp = xp * rs;
			syp = yp * rs;
		}

		int outW = sheet.WIDTH * rs;
		int outH = sheet.HEIGHT * rs;

		for(int dy = 0; dy < outH; dy++) {
			int ya = (int)(dy + syp);
			int ys = dy / rs;
			for(int dx = 0; dx < outW; dx++) {
				int xa = (int)(dx + sxp);
				int xs = dx / rs;
				if(flip == 1 || flip == 3) xs = (sheet.WIDTH - 1) - (dx / rs);
				if(xa < -outW || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;
				int color = sheet.pixels[xs + ys * sheet.WIDTH];

				if(color != 0xffff00ff) pixels[xa + ya * pixelWidth] = blendPixel(pixels[xa+ya*pixelWidth], color);
			}
		}
	}

	// Rendering Mobs
	public void renderMob(float xp, float yp, Mob mob, SpriteManager sprite, int flip) {
		int rs = Globals.RENDER_SCALE;
		xp = (xp - xOffset) * rs;
		yp = (yp - yOffset) * rs;

		int outW = sprite.width * rs;
		int outH = sprite.height * rs;

		for(int dy = 0; dy < outH; dy++) {
			float ya = dy+yp;
			int ys = dy / rs;
			if(flip == 2 || flip == 3) ys = (sprite.height-1) - (dy / rs);
			for(int dx = 0; dx < outW; dx++) {
				float xa = dx+xp;
				int xs = dx / rs;
				if(flip == 1 || flip == 3) xs = (sprite.width-1) - (dx / rs);
				if(xa < -outW || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;
				int color = mob.getSprite().pixels[xs + ys * sprite.width];
				if(color != 0xffff00ff) {
					int srcA = (color >> 24) & 0xFF;
					srcA = (srcA * mob.alpha) / 255;
					color = (srcA << 24) | (color & 0x00FFFFFF);
					pixels[(int)xa+(int)ya*pixelWidth] = blendPixel(pixels[(int)xa+(int)ya*pixelWidth], color);
//					if(mob.hurtTime > 0) pixels[xa+ya*pixelWidth] = 0xff << 24 | random.nextInt(0xffffff);
					if(mob.hurtTime > 0) pixels[(int)xa+(int)ya*pixelWidth] = blendPixel(pixels[(int)xa+(int)ya*pixelWidth], color >> 2 | 0x00);
				}
			}
		}
	}

	public void renderColor(int xp, int yp, SpriteManager sprite, int flip, int inputColor) {
		int rs = Globals.RENDER_SCALE;
		float sxp = (xp - xOffset) * rs;
		float syp = (yp - yOffset) * rs;

		int outW = sprite.width * rs;
		int outH = sprite.height * rs;

		for(int dy = 0; dy < outH; dy++) {
			int ya = (int)(dy + syp);
			int ys = dy / rs;
			if(flip == 2 || flip == 3) ys = (sprite.height- 1) - (dy / rs);
			for(int dx = 0; dx < outW; dx++) {
				int xa = (int)(dx + sxp);
				int xs = dx / rs;
				if(flip == 1 || flip == 3) xs = (sprite.width - 1) - (dx / rs);
				if(xa < -outW || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs + ys * sprite.width];
				if(color != 0xffff00ff) pixels[xa+ya*pixelWidth] = inputColor;
			}
		}
	}

	public void renderColorRelativeToLocation(int xp, int yp, SpriteManager sprite, int shade, int flip, LevelManager level) {
		int rs = Globals.RENDER_SCALE;
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

		float sxp = (xp - xOffset) * rs;
		float syp = (yp - yOffset) * rs;

		int outW = sprite.width * rs;
		int outH = sprite.height * rs;

		for(int dy = 0; dy < outH; dy++) {
			int ya = (int)(dy + syp);
			int ys = dy / rs;
			if(flip == 2 || flip == 3) ys = (sprite.height- 1) - (dy / rs);
			for(int dx = 0; dx < outW; dx++) {
				int xa = (int)(dx + sxp);
				int xs = dx / rs;
				if(flip == 1 || flip == 3) xs = (sprite.width - 1) - (dx / rs);
				if(xa < -outW || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs + ys * sprite.width];
				if(color != 0xffff00ff) pixels[xa+ya*pixelWidth] = shadedColor;
			}
		}
	}

	public void renderFont(int xp, int yp, SpriteManager sprite, int scale, int color, int flip) {
		int rs = Globals.RENDER_SCALE;
		float sxp = (xp - xOffset) * rs;
		float syp = (yp - yOffset) * rs;

		int outW = scale * rs;
		int outH = scale * rs;

		for(int dy = 0; dy < outH; dy++) {
			int ya = (int)(dy + syp);
			int ys = dy / rs;
			if(flip == 2 || flip == 3) ys = (scale - 1) - (dy / rs);
			for(int dx = 0; dx < outW; dx++) {
				int xa = (int)(dx + sxp);
				int xs = dx / rs;
				if(flip == 1 || flip == 3) xs = (scale - 1) - (dx / rs);
				if(xa < -outW || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;
				int colorPix = sprite.pixels[xs + ys * sprite.width];
				if (colorPix != 0xffff00ff) {
//					if(color == 0) if(scale <= sprite.width) pixels[(((xa*scale)/sprite.width)+(sprite.width-scale))+(((ya*scale)/sprite.height)+(sprite.height-scale))*pixelWidth] = colorPix;
//					else if(scale <= sprite.width) pixels[(((xa*scale)/sprite.width)+(sprite.width-scale))+(((ya*scale)/sprite.height)+(sprite.height-scale))*pixelWidth] = color;
					if(color != 0) pixels[xa+ya*pixelWidth] = color;
					else pixels[xa+ya*pixelWidth] = 0xff000000;
				}
			}
		}
	}

	public void renderScaled(int xp, int yp, SpriteManager sprite, int scale, int flip) {
		int rs = Globals.RENDER_SCALE;
		int sxp = (int)((xp - xOffset) * rs);
		int syp = (int)((yp - yOffset) * rs);

		int outW = sprite.width * rs;
		int outH = sprite.height * rs;

		for(int dy = 0; dy < outH; dy++) {
			int ya = dy + syp;
			int ys = dy / rs;
			if(flip == 2 || flip == 3) ys = (sprite.height- 1) - (dy / rs);
			for(int dx = 0; dx < outW; dx++) {
				int xa = dx + sxp;
				int xs = dx / rs;
				if(flip == 1 || flip == 3) xs = (sprite.width - 1) - (dx / rs);
				if(xa < -outW || xa >= pixelWidth || ya < 0 || ya >= pixelHeight) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs+ys*sprite.width];
				if(color != 0xffff00ff) {
					if(scale <= sprite.width) pixels[(((xa*scale)/sprite.width)+(sprite.width-scale))+(((ya*scale)/sprite.height)+(sprite.height-scale))*pixelWidth] = color;
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
	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}