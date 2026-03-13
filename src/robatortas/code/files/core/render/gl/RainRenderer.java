package robatortas.code.files.core.render.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * FBO-based rain renderer. Renders rain particles at native game resolution (250x220),
 * then composites the FBO texture onto the scene with GL_NEAREST filtering.
 * This forces rain to snap to the game pixel grid automatically.
 */
public class RainRenderer {

	private int fbo;
	private int rainTexture;
	private int width, height;

	public RainRenderer(int width, int height) {
		this.width = width;
		this.height = height;

		fbo = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);

		rainTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, rainTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
		// GL_NEAREST forces pixel-perfect upscaling — no blurring
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, rainTexture, 0);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("Rain FBO incomplete");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	/** Bind the rain FBO — all subsequent draws go into the rain texture */
	public void begin() {
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glViewport(0, 0, width, height);
		// Clear to fully transparent
		glClearColor(0, 0, 0, 0);
		glClear(GL_COLOR_BUFFER_BIT);
	}

	/** Unbind the rain FBO — back to rendering to screen */
	public void end() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public int getRainTexture() {
		return rainTexture;
	}

	public void dispose() {
		glDeleteFramebuffers(fbo);
		glDeleteTextures(rainTexture);
	}
}
