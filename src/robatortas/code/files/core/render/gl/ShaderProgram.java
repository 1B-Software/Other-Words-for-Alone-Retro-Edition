package robatortas.code.files.core.render.gl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	private HashMap<String, Integer> uniforms = new HashMap<>();

	public ShaderProgram(String vertexPath, String fragmentPath) {
		vertexShaderId = compileShader(loadSource(vertexPath), GL_VERTEX_SHADER);
		fragmentShaderId = compileShader(loadSource(fragmentPath), GL_FRAGMENT_SHADER);

		programId = glCreateProgram();
		glAttachShader(programId, vertexShaderId);
		glAttachShader(programId, fragmentShaderId);
		glLinkProgram(programId);

		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new RuntimeException("Shader link error: " + glGetProgramInfoLog(programId));
		}

		glDetachShader(programId, vertexShaderId);
		glDetachShader(programId, fragmentShaderId);
		glDeleteShader(vertexShaderId);
		glDeleteShader(fragmentShaderId);
	}

	private String loadSource(String path) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = ShaderProgram.class.getResourceAsStream(path);
			if (is == null) throw new RuntimeException("Shader not found: " + path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}
			br.close();
		} catch (Exception e) {
			throw new RuntimeException("Failed to load shader: " + path, e);
		}
		return sb.toString();
	}

	private int compileShader(String source, int type) {
		int id = glCreateShader(type);
		glShaderSource(id, source);
		glCompileShader(id);
		if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
			throw new RuntimeException("Shader compile error: " + glGetShaderInfoLog(id));
		}
		return id;
	}

	public void bind() {
		glUseProgram(programId);
	}

	public void unbind() {
		glUseProgram(0);
	}

	private int getUniformLocation(String name) {
		return uniforms.computeIfAbsent(name, n -> glGetUniformLocation(programId, n));
	}

	public void setUniform1i(String name, int value) {
		glUniform1i(getUniformLocation(name), value);
	}

	public void setUniform1f(String name, float value) {
		glUniform1f(getUniformLocation(name), value);
	}

	public void setUniform2f(String name, float x, float y) {
		glUniform2f(getUniformLocation(name), x, y);
	}

	public void setUniform3f(String name, float x, float y, float z) {
		glUniform3f(getUniformLocation(name), x, y, z);
	}

	public void setUniform4f(String name, float x, float y, float z, float w) {
		glUniform4f(getUniformLocation(name), x, y, z, w);
	}

	public void setUniformMatrix4f(String name, float[] matrix) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.floats(matrix);
			glUniformMatrix4fv(getUniformLocation(name), false, fb);
		}
	}

	public void dispose() {
		glDeleteProgram(programId);
	}

	public int getId() {
		return programId;
	}
}
