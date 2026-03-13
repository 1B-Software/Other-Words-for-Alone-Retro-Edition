package robatortas.code.files.core.render.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * FBO-based lightmap renderer. Renders radial lights additively into an offscreen texture,
 * then the compose shader multiplies scene * lightmap.
 */
public class LightRenderer {

	private int fbo;
	private int lightTexture;
	private int width, height;
	private ShaderProgram lightShader;
	private SpriteBatch batch;
	private int whiteTexture;

	public LightRenderer(int width, int height, ShaderProgram lightShader, SpriteBatch batch, int whiteTexture) {
		this.width = width;
		this.height = height;
		this.lightShader = lightShader;
		this.batch = batch;
		this.whiteTexture = whiteTexture;

		// Create FBO with float-precision color attachment
		fbo = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);

		lightTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, lightTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA16F, width, height, 0, GL_RGBA, GL_FLOAT, (java.nio.FloatBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, lightTexture, 0);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("Lightmap FBO incomplete");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void begin(float environmentLight) {
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glViewport(0, 0, width, height);
		// Clear to environment light level
		glClearColor(environmentLight, environmentLight, environmentLight, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		// Additive blending for light accumulation
		glBlendFunc(GL_ONE, GL_ONE);
		glBlendEquation(GL_FUNC_ADD);
	}

	/**
	 * Add a radial light. Uses the light shader to draw a gradient circle.
	 */
	public void addLight(float x, float y, float radius, float intensity, float cr, float cg, float cb, float fallOff) {
		// Use beginWith so the light shader is active during the draw call
		batch.beginWith(lightShader);
		lightShader.setUniform2f("uLightPos", x, y);
		lightShader.setUniform1f("uRadius", radius);
		lightShader.setUniform1f("uIntensity", intensity);
		lightShader.setUniform3f("uLightColor", cr, cg, cb);
		lightShader.setUniform1f("uFallOff", fallOff);
		lightShader.setUniform2f("uResolution", width, height);

		// Draw a quad covering the light's bounding box
		float lx = x - radius;
		float ly = y - radius;
		float size = radius * 2;
		batch.drawColor(lx, ly, size, size, 1, 1, 1, 1, whiteTexture);
		batch.end();
	}

	public void end() {
		// Restore standard alpha blending
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBlendEquation(GL_FUNC_ADD);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public int getLightTexture() {
		return lightTexture;
	}

	public void dispose() {
		glDeleteFramebuffers(fbo);
		glDeleteTextures(lightTexture);
	}
}
