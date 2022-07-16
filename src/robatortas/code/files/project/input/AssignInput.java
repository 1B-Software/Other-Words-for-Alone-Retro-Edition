package robatortas.code.files.project.input;

import java.awt.event.KeyEvent;

// To assign inputs like movement and interaction keys, also for mouse input.
public class AssignInput {
	public boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	public boolean space, f, shift;
	
	public boolean f3;
	
	public void assignKeys() {
		gamePlayKeys();
		hotKeys();
	}
	
	private boolean toggle;
	public boolean toggle(boolean key, boolean b) {
	    if (isKeyPressed(key) && !toggle) {
	        b = !b;
	        toggle = true;
	    } else if (!isKeyPressed(key) && toggle) {
	        toggle = false;
	    }
	    return b;
	}
	
	public boolean isKeyPressed(boolean key) {
	    return key;
	}
	
	private void gamePlayKeys() {
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
		
		f = keys[KeyEvent.VK_F];
		space = keys[KeyEvent.VK_SPACE];
		shift = keys[KeyEvent.VK_SHIFT];
	}
	
	private void hotKeys() {
		f3 = keys[KeyEvent.VK_F3];
	}
	
	
	// WILL USE THIS IN THE FUTURE!
	public void assignMouse() {
		
	}
}
