package robatortas.code.files.core.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	// Mouse X & Y
	public static int mX, mY;
	// Mouse Pressed
	public static int mPX, mPY;
	// Mouse Drag
	public static int mDX, nDY;
	
	// Mouse Button
	public static int mouseButton = 0;
	
	// Toggling boolean
	public static boolean toggled;
	private static boolean toggleable;
	public static boolean toggleable(boolean toggleable) {
		MouseManager.toggleable = toggleable;
		return toggleable;
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public static boolean LEFT;
	public boolean MIDDLE;
	public boolean RIGHT;
	
	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		System.out.println(mouseButton);
		
		if(SwingUtilities.isLeftMouseButton(e))
		
		if(e.getButton() == MouseEvent.BUTTON1) LEFT = true;
		else LEFT = false;
		
		System.out.println(LEFT);
		
		mX = e.getX();
		mY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		if(toggleable) toggled = !toggled;
		
		System.out.println(toggled);
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
	
	public int getX() {
		return MouseManager.mX;
	}
	
	public int getY() {
		return MouseManager.mY;
	}
}