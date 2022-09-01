package robatortas.code.files.core.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	// Mouse X & Y
	public static int mX, mY;
	// Mouse Pressed X & Y
	public static int mPX, mPY;
	// Mouse Drag X & Y
	public static int mDX, mDY;
	
	// Mouse Button
	public static int mouseButton = 0;
	
	
	// MOUSE BUTTON INPUTS
	public static boolean LEFT;
	public boolean MIDDLE;
	public boolean RIGHT;
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		System.out.println(mouseButton);
		
		if(SwingUtilities.isLeftMouseButton(e))
		
		if(e.getButton() == MouseEvent.BUTTON1) LEFT = true;
		else LEFT = false;
		
		System.out.println(LEFT);
		
		mPX = e.getX();
		mPY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		if(toggleable) toggled = !toggled;
		
		System.out.println(toggled);
	}
	
	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	
	// MOUSE MOVEMENT
	public void mouseDragged(MouseEvent e) {
		mDX = e.getX();
		mDY = e.getY();
	}
	
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
	}
	
	
	// Toggling boolean
	public static boolean toggled;
	private static boolean toggleable;
	public static boolean toggleable(boolean toggleable) {
		MouseManager.toggleable = toggleable;
		return toggleable;
	}
	
	
	// GETTERS AND SETTERS
	public int getX() {
		return MouseManager.mX;
	}
	
	public int getY() {
		return MouseManager.mY;
	}
}