package robatortas.code.files.project.input;

import java.awt.event.KeyEvent;

// To assign inputs like movement and interaction keys, also for mouse input.
public class AssignInput {
	public boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	public boolean space, f, shift;
	public boolean c, e;
	
	public boolean f3;
	
	public int presses;
	
	public void assignKeys() {
		gamePlayKeys();
		hotKeys();
	}
	
	private boolean toggle;
	private int toggled;
	public boolean toggle(boolean key, boolean b) {
		if(key && !toggle) {
			b = !b;
			toggle = true;
			} else if (key && toggle) {
				if(toggled > 1000) toggle = false;
			}
		if(toggle) toggled++;
		else toggled = 0;
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
		
		c = keys[KeyEvent.VK_C];
		e = keys[KeyEvent.VK_E];
	}
	
	private void hotKeys() {
		f3 = keys[KeyEvent.VK_F3];
	}
	
	// WILL USE THIS IN THE FUTURE!
	public void assignMouse() {
		
	}
}
