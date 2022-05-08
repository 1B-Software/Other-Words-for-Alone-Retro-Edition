package robatortas.code.files.core.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import robatortas.code.files.project.input.AssignInput;

public class InputManager extends AssignInput implements KeyListener {
	
	// For updating input
	public void update() {
		assignKeys();
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
