package robatortas.code.files.project.input;

import static org.lwjgl.glfw.GLFW.*;

import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.gl.GLWindow;

// To assign inputs like movement and interaction keys, also for mouse input.
// Now reads from GLWindow.keys[] using GLFW key codes instead of AWT KeyEvent.
public class AssignInput {
	// Point to GLWindow's key array (GLFW key codes, up to 512)
	public boolean[] keys = GLWindow.keys;

	public boolean up, down, left, right;
	public boolean space, f, shift;
	public boolean c, e;
	public boolean upMenu, downMenu, enter;

	public boolean f3;

	/*
	 * Assigns all the keys to get them updated for each tick.
	 * Called on InputManager's update method.
	 */
	public void assignKeys() {
		gamePlayKeys();
		hotKeys();
		mainMenuKeys();
	}

	// checks if key is toggled
	protected boolean toggle = false;
	public boolean toggle(boolean key, boolean b) {
		if(key && !toggle && !InputManager.released) {
			b = !b;
			toggle = true;
		} else if(!key && toggle && InputManager.released) {
			toggle = false;
		}
		return b;
	}

	public boolean isKeyPressed(boolean key) {
	    return key;
	}

	// Assign core gameplay keys here! (GLFW key codes)
	private void gamePlayKeys() {
		up    = keys[GLFW_KEY_W]     || keys[GLFW_KEY_UP];
		down  = keys[GLFW_KEY_S]     || keys[GLFW_KEY_DOWN];
		left  = keys[GLFW_KEY_A]     || keys[GLFW_KEY_LEFT];
		right = keys[GLFW_KEY_D]     || keys[GLFW_KEY_RIGHT];

		f     = keys[GLFW_KEY_F];
		space = keys[GLFW_KEY_SPACE];
		shift = keys[GLFW_KEY_LEFT_SHIFT] || keys[GLFW_KEY_RIGHT_SHIFT];

		c = keys[GLFW_KEY_C];
		e = keys[GLFW_KEY_E];
	}

	public void mainMenuKeys() {
		upMenu   = keys[GLFW_KEY_UP];
		downMenu = keys[GLFW_KEY_S] || keys[GLFW_KEY_DOWN];
		enter    = keys[GLFW_KEY_ENTER];
	}

	// Assign all hotkeys here!
	private void hotKeys() {
		f3 = keys[GLFW_KEY_F3];
	}
}
