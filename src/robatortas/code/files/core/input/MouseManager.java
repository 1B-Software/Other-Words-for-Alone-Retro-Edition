package robatortas.code.files.core.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	// Mouse X & Y
	public static int mX, mY;
	// Mouse Pressed
	public static int mPX, mPY;
	// Mouse Drag
	public static int mDX, nDY;
	
	// Mouse Button
	public static int mouseButton = 0;
	
	public int getX() {
		return MouseManager.mX;
	}
	
	public int getY() {
		return MouseManager.mY;
	}
	
	// Toggling boolean
	private boolean toggle;
	private int toggled;
	public boolean toggle(int mouseB, boolean b) {
		if(mouseB!=0 && !toggle) {
			b = !b;
			toggle = true;
			} else if (mouseB!=0 && toggle) {
				if(toggled > 1000) toggle = false;
			}
		if(toggle) toggled++;
		else toggled = 0;
		return b;
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	// Assigning buttons with enums for ease
	public static enum BUTTONS {
		LEFT(3),
		RIGHT(1);
		
		public int button = 0;
		BUTTONS(int b) {
			this.button = b;
		}
	};

	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		System.out.println(mouseButton);
		mX = e.getX();
		mY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		mX = 0;
		mY = 0;
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
	}
}