package robatortas.code.files.core.input;

import robatortas.code.files.core.render.gl.GLWindow;
import robatortas.code.files.project.input.AssignInput;

/**
 * InputManager - reads key state from GLWindow's GLFW callbacks.
 * No longer uses AWT KeyListener. Polls GLWindow.keys[] instead.
 */
public class InputManager extends AssignInput {

	public static boolean released = false;

	public void update() {
		// Sync released flag from GLFW callback
		released = GLWindow.released;
		assignKeys();
	}
}
