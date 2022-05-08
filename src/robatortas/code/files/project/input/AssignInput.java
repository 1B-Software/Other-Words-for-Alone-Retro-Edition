package robatortas.code.files.project.input;

import java.awt.event.KeyEvent;

// To assign inputs like movement and interaction keys, also for mouse input.
public class AssignInput {
	public boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	public boolean f, shift;
	
	public void assignKeys() {
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
		
		f = keys[KeyEvent.VK_F];
		shift = keys[KeyEvent.VK_SHIFT];
	}
	
	
	// WILL USE THIS IN THE FUTURE!
	public void assignMouse() {
		
	}
}
