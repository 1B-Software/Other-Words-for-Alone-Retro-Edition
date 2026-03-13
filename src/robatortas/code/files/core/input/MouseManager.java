package robatortas.code.files.core.input;

import robatortas.code.files.core.render.gl.GLWindow;

/**
 * MouseManager - reads mouse state from GLWindow's GLFW callbacks.
 * No longer uses AWT MouseListener.
 */
public class MouseManager {

	// Mouse X & Y (synced from GLWindow)
	public static int mX, mY;

	// Mouse Button
	public static int mouseButton = 0;

	public boolean released = false;

	// MOUSE BUTTON INPUTS (synced from GLWindow)
	public static boolean LEFT = false;
	public boolean MIDDLE = false;
	public boolean RIGHT = false;

	public void update() {
		// Sync from GLWindow's GLFW callbacks
		mX = GLWindow.mouseX;
		mY = GLWindow.mouseY;
		LEFT = GLWindow.mouseLeft;
		RIGHT = GLWindow.mouseRight;
		MIDDLE = GLWindow.mouseMiddle;
		mouseButton = GLWindow.mouseButton;
	}

	// Toggling boolean
	public boolean toggle;
	public boolean toggle(boolean button, boolean bool) {
		if(button && !toggle && !released) {
			bool = !bool;
			toggle = true;
		} else if (!button && toggle && released) {
			toggle = false;
		}
		return bool;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}
}
