package robatortas.code.files.project.input;

import java.awt.event.KeyEvent;

import robatortas.code.files.core.input.InputManager;

// To assign inputs like movement and interaction keys, also for mouse input.
public class AssignInput {
	public boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	public boolean space, f, shift;
	public boolean c, e;
	
	public boolean f3;
	
	/* 
	 * Assigns all the keys to get them updated for each tick.
	 * 
	 * Called on InputManager's update method.
	 */
	public void assignKeys() { 
		gamePlayKeys();
		hotKeys();
	}
	
	// checks if key is toggled
	private boolean toggle;
	// Tracks toggle time
	private int toggled;
	
	/*
	 * toggles keys.
	 * 
	 * The first boolean parameter (key) is the key that you want to toggle press.
	 * 
	 * The second boolean parameter (b) is the boolean variable you want to use as 
	 * an indicator of the toggling of the action.
	 */
	public boolean toggle(boolean key, boolean b) {
		if(key && !toggle) {
			b = !b;
			toggle = true;
		} else if (key && toggle && InputManager.released) {
			toggle = false;
		}
		if(toggle) toggled++;
		else toggled = 0;
		return b;
	}
	
	/* 
	 * Checks if key is pressed with the inputted key
	 * The boolean parameter is the key that you are wanting to check if it is pressed or not.
	 * 
	 * This will just return the boolean value making it in my perspective a bit cleaner.
	 */
	public boolean isKeyPressed(boolean key) {
	    return key;
	}
	
	// Assign core gameplay keys here!
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
	
	// Assign all hotkeys here!
	private void hotKeys() {
		f3 = keys[KeyEvent.VK_F3];
	}
}
