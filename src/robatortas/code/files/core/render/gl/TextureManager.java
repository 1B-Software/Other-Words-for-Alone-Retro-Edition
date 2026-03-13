package robatortas.code.files.core.render.gl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Manages GPU textures. Uploads int[] ARGB pixel data as OpenGL textures
 * with GL_NEAREST filtering for pixel-art crispness.
 */
public class TextureManager {

	private int textureId;
	private int width, height;

	/**
	 * Upload pixel data from a spritesheet's int[] pixels to a GPU texture.
	 * Converts ARGB to RGBA and handles magenta (0xFFFF00FF) as transparent.
	 */
	public TextureManager(int[] pixels, int width, int height) {
		this.width = width;
		this.height = height;

		ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
		for (int i = 0; i < pixels.length; i++) {
			int argb = pixels[i];
			int a = (argb >> 24) & 0xFF;
			int r = (argb >> 16) & 0xFF;
			int g = (argb >> 8) & 0xFF;
			int b = argb & 0xFF;

			// Magenta = transparent
			if ((argb & 0x00FFFFFF) == 0xFF00FF) {
				a = 0;
			}

			buffer.put((byte) r);
			buffer.put((byte) g);
			buffer.put((byte) b);
			buffer.put((byte) a);
		}
		buffer.flip();

		textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	/**
	 * Create texture from an existing OpenGL texture ID (for FBOs).
	 */
	public TextureManager(int textureId, int width, int height) {
		this.textureId = textureId;
		this.width = width;
		this.height = height;
	}

	public void bind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, textureId);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void dispose() {
		glDeleteTextures(textureId);
	}

	public int getId() { return textureId; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
