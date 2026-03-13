package robatortas.code.files.core.render;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.gl.SpriteBatch;
import robatortas.code.files.project.settings.Globals;

import static org.lwjgl.opengl.GL11.*;

/**
 * RenderManager
 *
 * GPU-backed renderer. All render methods submit textured quads to a SpriteBatch.
 * Coordinates are in game-world units (250x220 viewport).
 */
public class RenderManager {

	/** World viewport dimensions */
	public int width, height;

	public float xOffset, yOffset;

	public Random random = new Random();

	private SpriteBatch batch;

	// 1x1 white texture for solid color quads
	private int whiteTextureId;

	// Pending lights queued during scene rendering (screen-space coords)
	// Each entry: {x, y, radius, intensity, r, g, b, fallOff}
	private List<float[]> pendingLights = new ArrayList<>();

	public RenderManager(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void initGL() {
		ByteBuffer whitePixel = ByteBuffer.allocateDirect(4);
		whitePixel.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
		whitePixel.flip();
		whiteTextureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, whiteTextureId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 1, 1, 0, GL_RGBA, GL_UNSIGNED_BYTE, whitePixel);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public int getWhiteTextureId() {
		return whiteTextureId;
	}

	public void clear(boolean clears) {
		// GPU clear is handled by glClear in GameManager.render()
	}

	public void renderBox(float xp, float yp, int w, int h, int color, int alpha, boolean fixed) {
		float drawX, drawY;
		if (!fixed) {
			drawX = xp - xOffset;
			drawY = yp - yOffset;
		} else {
			drawX = xp;
			drawY = yp;
		}

		float r = ((color >> 16) & 0xFF) / 255f;
		float g = ((color >> 8) & 0xFF) / 255f;
		float b = (color & 0xFF) / 255f;
		float a = (((color >> 24) & 0xFF) * alpha / 255) / 255f;

		batch.drawColor(drawX, drawY, w, h, r, g, b, a, whiteTextureId);
	}

	public void renderTile(float xp, float yp, float scale, TileManager tile) {
		float drawX = xp - xOffset;
		float drawY = yp - yOffset;
		float w = tile.sprite.width * scale;
		float h = tile.sprite.height * scale;
		float a = tile.sprite.alpha / 255f;

		int texId = tile.sprite.getTextureId();
		if (texId == 0) return;

		batch.draw(drawX, drawY, w, h,
				tile.sprite.u0, tile.sprite.v0, tile.sprite.u1, tile.sprite.v1,
				1, 1, 1, a, texId);
	}

	/**
	 * Rendering Sprites
	 * @param xp The x position
	 * @param yp The y position
	 * @param sprite The sprite that will be rendered
	 * @param scale The size of the sprite
	 * @param flip Flips the sprite; 1 flips it on x, 2 flips it on y, 3 flips it on x & y
	 */
	public void renderSprite(float xp, float yp, SpriteManager sprite, float scale, int flip) {
		float drawX = xp - xOffset;
		float drawY = yp - yOffset;
		float w = sprite.width * scale;
		float h = sprite.height * scale;
		float a = sprite.alpha / 255f;

		int texId = sprite.getTextureId();
		if (texId == 0) return;

		float u0 = sprite.u0, v0 = sprite.v0, u1 = sprite.u1, v1 = sprite.v1;
		if (flip == 1 || flip == 3) { float tmp = u0; u0 = u1; u1 = tmp; }
		if (flip == 2 || flip == 3) { float tmp = v0; v0 = v1; v1 = tmp; }

		batch.draw(drawX, drawY, w, h, u0, v0, u1, v1, 1, 1, 1, a, texId);
	}

	// Renders a sub-region of a sprite (used for quadrant-based tile rendering)
	public void renderSpriteRegion(float xp, float yp, SpriteManager sprite, int srcX, int srcY, int srcW, int srcH, float scale) {
		float drawX = xp - xOffset;
		float drawY = yp - yOffset;
		float w = srcW * scale;
		float h = srcH * scale;
		float a = sprite.alpha / 255f;

		int texId = sprite.getTextureId();
		if (texId == 0) return;

		if (sprite.sheet != null && sprite.sheet.WIDTH > 0) {
			float sheetW = sprite.sheet.WIDTH;
			float sheetH = sprite.sheet.HEIGHT;
			float regU0 = (sprite.x + srcX) / sheetW;
			float regV0 = (sprite.y + srcY) / sheetH;
			float regU1 = (sprite.x + srcX + srcW) / sheetW;
			float regV1 = (sprite.y + srcY + srcH) / sheetH;
			batch.draw(drawX, drawY, w, h, regU0, regV0, regU1, regV1, 1, 1, 1, a, texId);
		}
	}

	public void renderSpriteSheet(int xp, int yp, SpriteSheetManager sheet, int flip, boolean fixed) {
		float drawX, drawY;
		if (!fixed) {
			drawX = xp - xOffset;
			drawY = yp - yOffset;
		} else {
			drawX = xp;
			drawY = yp;
		}

		int texId = sheet.getGPUTextureId();
		if (texId == 0) return;

		float u0 = 0, v0 = 0, u1 = 1, v1 = 1;
		if (flip == 1 || flip == 3) { float tmp = u0; u0 = u1; u1 = tmp; }

		batch.draw(drawX, drawY, sheet.WIDTH, sheet.HEIGHT, u0, v0, u1, v1, 1, 1, 1, 1, texId);
	}

	// Rendering Mobs
	public void renderMob(float xp, float yp, Mob mob, SpriteManager sprite, int flip) {
		float drawX = xp - xOffset;
		float drawY = yp - yOffset;
		float w = sprite.width;
		float h = sprite.height;
		float a = mob.alpha / 255f;

		int texId = sprite.getTextureId();
		if (texId == 0) return;

		float u0 = sprite.u0, v0 = sprite.v0, u1 = sprite.u1, v1 = sprite.v1;
		if (flip == 1 || flip == 3) { float tmp = u0; u0 = u1; u1 = tmp; }
		if (flip == 2 || flip == 3) { float tmp = v0; v0 = v1; v1 = tmp; }

		float r = 1, g = 1, b = 1;
		// Darken when hurt
		if (mob.hurtTime > 0) {
			r = 0.25f; g = 0.25f; b = 0.25f;
		}

		batch.draw(drawX, drawY, w, h, u0, v0, u1, v1, r, g, b, a, texId);
	}

	public void renderColor(int xp, int yp, SpriteManager sprite, int flip, int inputColor) {
		float drawX = xp - xOffset;
		float drawY = yp - yOffset;

		float r = ((inputColor >> 16) & 0xFF) / 255f;
		float g = ((inputColor >> 8) & 0xFF) / 255f;
		float b = (inputColor & 0xFF) / 255f;
		float a = ((inputColor >> 24) & 0xFF) / 255f;

		int texId = sprite.getTextureId();
		if (texId == 0) {
			batch.drawColor(drawX, drawY, sprite.width, sprite.height, r, g, b, a, whiteTextureId);
			return;
		}

		float u0 = sprite.u0, v0 = sprite.v0, u1 = sprite.u1, v1 = sprite.v1;
		if (flip == 1 || flip == 3) { float tmp = u0; u0 = u1; u1 = tmp; }
		if (flip == 2 || flip == 3) { float tmp = v0; v0 = v1; v1 = tmp; }

		batch.draw(drawX, drawY, sprite.width, sprite.height, u0, v0, u1, v1, r, g, b, a, texId);
	}

	public void renderColorRelativeToLocation(int xp, int yp, SpriteManager sprite, int shade, int flip, LevelManager level) {
		TileManager tile = level.getLevel(xp >> 4, yp >> 4);
		int baseColor = 0xFF888888;
		if (tile != null && tile.sprite != null && tile.sprite.pixels != null && tile.sprite.pixels.length > 0) {
			baseColor = tile.sprite.pixels[0];
		}
		if (shade == 0) shade = 30;
		int rc = Math.max(0, ((baseColor >> 16) & 0xFF) - shade);
		int gc = Math.max(0, ((baseColor >> 8) & 0xFF) - shade);
		int bc = Math.max(0, (baseColor & 0xFF) - shade);
		int shadedColor = 0xFF000000 | (rc << 16) | (gc << 8) | bc;
		renderColor(xp, yp, sprite, flip, shadedColor);
	}

	public void renderFont(int xp, int yp, SpriteManager sprite, float scale, int color, int flip) {
		float drawX = xp - xOffset;
		float drawY = yp - yOffset;
		float w = sprite.width * scale;
		float h = sprite.height * scale;

		float r, g, b;
		if (color != 0) {
			r = ((color >> 16) & 0xFF) / 255f;
			g = ((color >> 8) & 0xFF) / 255f;
			b = (color & 0xFF) / 255f;
		} else {
			r = 0; g = 0; b = 0;
		}
		float a = ((color >> 24) & 0xFF) / 255f;
		if (a == 0) a = 1f;

		int texId = sprite.getTextureId();
		if (texId == 0) return;

		float u0 = sprite.u0, v0 = sprite.v0, u1 = sprite.u1, v1 = sprite.v1;
		if (flip == 1 || flip == 3) { float tmp = u0; u0 = u1; u1 = tmp; }
		if (flip == 2 || flip == 3) { float tmp = v0; v0 = v1; v1 = tmp; }
		batch.setFontMode(true);
		batch.draw(drawX, drawY, w, h, u0, v0, u1, v1, r, g, b, a, texId);
	}

	public void renderScaled(int xp, int yp, SpriteManager sprite, int scale, int flip) {
		renderSprite(xp, yp, sprite, (float) scale / sprite.width, flip);
	}

	public void debug(int xp, int yp, int w, int h, int color, int perimeter) {
		float drawX = xp - xOffset;
		float drawY = yp - yOffset;
		float r = ((color >> 16) & 0xFF) / 255f;
		float g = ((color >> 8) & 0xFF) / 255f;
		float b = (color & 0xFF) / 255f;
		float a = ((color >> 24) & 0xFF) / 255f;
		batch.drawColor(drawX, drawY, w, h, r, g, b, a, whiteTextureId);
	}

	@Deprecated
	public int[] scale(int[] pixels, int width, int height, int scale) {
		return pixels;
	}

	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	
	/**
	 * Queue a light for rendering. Coordinates are converted from world to screen space.
	 * Called by LightSource.add() during the scene render pass.
	 */
	public void queueLight(float worldX, float worldY, float radius, float intensity, float r, float g, float b, float fallOff) {
		pendingLights.add(new float[]{
			worldX - xOffset, worldY - yOffset,
			radius, intensity, r, g, b, fallOff
		});
	}

	public List<float[]> getPendingLights() {
		return pendingLights;
	}

	public void clearPendingLights() {
		pendingLights.clear();
	}
}
