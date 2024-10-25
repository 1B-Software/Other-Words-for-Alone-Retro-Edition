package robatortas.code.files.core.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import robatortas.code.files.project.input.AssignInput;

public class InputManager extends AssignInput implements KeyListener {
	
	public static boolean released = false;
	
	// For updating input
	public void update() {
		assignKeys();
	}
	
	public void keyPressed(KeyEvent e) {
		try {
			released = false;
			keys[e.getKeyCode()] = true;
		}catch(ArrayIndexOutOfBoundsException ee) {
			// DO NOTHING!
		}
	}
	
	public void keyReleased(KeyEvent e) {
		try {
			released = true;
			keys[e.getKeyCode()] = false;
		}catch(ArrayIndexOutOfBoundsException ee) {
			// DO NOTHING!
		}
	}
	
	public void keyTyped(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = false;	
		}catch(ArrayIndexOutOfBoundsException ee) {
			// DO NOTHING!
		}
	}
}
