package robatortas.code.files.core.render.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Batched quad renderer. Accumulates sprite quads and flushes them in a single draw call.
 * Each vertex has 8 floats
 */
public class SpriteBatch {

	private static final int MAX_QUADS = 4096;
	private static final int FLOATS_PER_VERTEX = 8; // x,y,u,v,r,g,b,a
	private static final int VERTICES_PER_QUAD = 4;
	private static final int INDICES_PER_QUAD = 6;

	private int vao, vbo, ibo;
	private FloatBuffer vertexBuffer;
	private int quadCount;

	private ShaderProgram shader;
	private int currentTextureId = -1;

	// Orthographic projection matrix (column-major for OpenGL)
	private float[] projectionMatrix;

	public SpriteBatch(ShaderProgram shader) {
		this.shader = shader;

		vertexBuffer = MemoryUtil.memAllocFloat(MAX_QUADS * VERTICES_PER_QUAD * FLOATS_PER_VERTEX);

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, (long) MAX_QUADS * VERTICES_PER_QUAD * FLOATS_PER_VERTEX * Float.BYTES, GL_DYNAMIC_DRAW);

		// position (x, y)
		glVertexAttribPointer(0, 2, GL_FLOAT, false, FLOATS_PER_VERTEX * Float.BYTES, 0);
		glEnableVertexAttribArray(0);
		// texcoord (u, v)
		glVertexAttribPointer(1, 2, GL_FLOAT, false, FLOATS_PER_VERTEX * Float.BYTES, 2L * Float.BYTES);
		glEnableVertexAttribArray(1);
		// color (r, g, b, a)
		// The stride is really similar to how memory addresses handle their values and their limits
		glVertexAttribPointer(2, 4, GL_FLOAT, false, FLOATS_PER_VERTEX * Float.BYTES, 4L * Float.BYTES);
		glEnableVertexAttribArray(2);

		// Index buffer (shared pattern: 0,1,2, 2,3,0 per quad)
		IntBuffer indices = MemoryUtil.memAllocInt(MAX_QUADS * INDICES_PER_QUAD);
		for (int i = 0; i < MAX_QUADS; i++) {
			int offset = i * 4;
			indices.put(offset).put(offset + 1).put(offset + 2);
			indices.put(offset + 2).put(offset + 3).put(offset);
		}
		indices.flip();

		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		MemoryUtil.memFree(indices);
		glBindVertexArray(0);
	}

	public void setProjection(float width, float height) {
		// Orthographic: left=0, right=width, top=0, bottom=height (Y-down like AWT)
		projectionMatrix = new float[] {
			2f / width,  0,            0, 0,
			0,          -2f / height,  0, 0,
			0,           0,           -1, 0,
			-1,          1,            0, 1
		};
	}

	public void begin() {
		shader.bind();
		shader.setUniformMatrix4f("uProjection", projectionMatrix);
		shader.setUniform1i("uTexture", 0);
		shader.setUniform1i("uFontMode", 0);
		quadCount = 0;
		vertexBuffer.clear();
		currentTextureId = -1;
	}
	
	public void setFontMode(boolean enabled) {
		flush();
		shader.setUniform1i("uFontMode", enabled ? 1 : 0);
	}

	/**
	 * Begin a batch pass with a custom shader instead of the default sprite shader.
	 * The custom shader must have a uProjection uniform.
	 */
	public void beginWith(ShaderProgram customShader) {
		customShader.bind();
		customShader.setUniformMatrix4f("uProjection", projectionMatrix);
		customShader.setUniform1i("uTexture", 0);
		quadCount = 0;
		vertexBuffer.clear();
		currentTextureId = -1;
	}

	/**
	 * Draw a textured quad.
	 * @param x screen x
	 * @param y screen y
	 * @param w quad width
	 * @param h quad height
	 * @param u0 left UV
	 * @param v0 top UV
	 * @param u1 right UV
	 * @param v1 bottom UV
	 * @param r tint red (0-1)
	 * @param g tint green (0-1)
	 * @param b tint blue (0-1)
	 * @param a alpha (0-1)
	 * @param textureId GL texture ID
	 */
	public void draw(float x, float y, float w, float h,
					 float u0, float v0, float u1, float v1,
					 float r, float g, float b, float a,
					 int textureId) {
		if (textureId != currentTextureId || quadCount >= MAX_QUADS) {
			flush();
			currentTextureId = textureId;
			glBindTexture(GL_TEXTURE_2D, currentTextureId);
		}

		// Top-left
		vertexBuffer.put(x).put(y).put(u0).put(v0).put(r).put(g).put(b).put(a);
		// Top-right
		vertexBuffer.put(x + w).put(y).put(u1).put(v0).put(r).put(g).put(b).put(a);
		// Bottom-right
		vertexBuffer.put(x + w).put(y + h).put(u1).put(v1).put(r).put(g).put(b).put(a);
		// Bottom-left
		vertexBuffer.put(x).put(y + h).put(u0).put(v1).put(r).put(g).put(b).put(a);

		quadCount++;
	}

	/**
	 * Draw a solid color quad (no texture). Uses a 1x1 white texture.
	 */
	public void drawColor(float x, float y, float w, float h,
						  float r, float g, float b, float a,
						  int whiteTextureId) {
		draw(x, y, w, h, 0, 0, 1, 1, r, g, b, a, whiteTextureId);
	}

	// Flushing means that it uploads all the quads to the gpu
	public void flush() {
		if (quadCount == 0) return;

		vertexBuffer.flip();

		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);

		glDrawElements(GL_TRIANGLES, quadCount * INDICES_PER_QUAD, GL_UNSIGNED_INT, 0);

		glBindVertexArray(0);
		quadCount = 0;
		vertexBuffer.clear();
	}

	public void end() {
		flush();
		shader.unbind();
	}

	public void dispose() {
		MemoryUtil.memFree(vertexBuffer);
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
		glDeleteVertexArrays(vao);
	}
}
