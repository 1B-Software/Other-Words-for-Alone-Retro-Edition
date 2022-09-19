package robatortas.code.files.core.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// FINISH THIS CLASS TOMORROW

public class MouseManager implements MouseListener, MouseMotionListener {
	
	// Mouse X & Y
	public static int mX, mY;
	// Mouse Pressed X & Y
	public int mPX, mPY;
	// Mouse Drag X & Y
	public int mDX, mDY;
	
	// Mouse Button
	public static int mouseButton = 0;
	
	public boolean released = false;
	
	// MOUSE BUTTON INPUTS
	public static boolean LEFT = false;
	public boolean MIDDLE = false;
	public boolean RIGHT = false;
	
	public void update() {
		System.out.println(released);
	}
	
	@SuppressWarnings("unused")
	public void mouseClicked(MouseEvent e) {
		
	}
	
	/**<NEWLINE>
	 * <b>mousePressed on the MouseManager class</b>
	 * <br><br>
	 * Gets the clicked mouse button and coordinates of where it clicked at.
	 */
	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		
		if(mouseButton == 1) LEFT = !LEFT;
		if(mouseButton == 3) RIGHT = !RIGHT;
		if(mouseButton == 2) MIDDLE = !MIDDLE;
		
		released = false;
		
		mPX = e.getX();
		mPY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
//		if(toggleable) toggled = !toggled;
		
		released = true;
		
		LEFT = false;
		RIGHT = false;
		MIDDLE = false;
	}
	
	@SuppressWarnings("unused")
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@SuppressWarnings("unused")
	public void mouseExited(MouseEvent e) {
		
	}
	
	// MOUSE MOVEMENT
	
	/**<NEWLINE>
	 * <b>mouseDragged function on the MouseManager class</b>
	 * <br><br>
	 * Checks if the mouse is being dragged.
	 * 
	 * Assigns mDX & mDY (Mouse Drag X & Y) to get their positions when it's dragged.
	 */
	public void mouseDragged(MouseEvent e) {
		mDX = e.getX();
		mDY = e.getY();
	}
	
	/**<NEWLINE>
	 * <b>mouseMoved function on the MouseManager class</b>
	 * <br><br>
	 * Checks if the mouse is being moved.
	 * 
	 * Assigns mX & mY (Mouse X & Y) to get their positions when it moves.
	 */
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
	}
	
	
	// Toggling boolean
	public int toggleTime = 0;
	public boolean toggle;
	/**<NEWLINE>
	 * <b>toggleable function on the MouseManager class</b>
	 * <br><br>
	 * Makes the assigned mouse button toggleable.
	 * 
	 * Of course you don't want all buttons on all situations be toggleable.
	 * So this is just to do that!
	 * 
	 * @param toggleable If the button is toggleable
	 */
	public boolean toggle(boolean button, boolean bool) {
		if(button && !toggle && !released) {
			bool = !bool;
			toggle = true;
			toggleTime++;
		} else if (button && toggle) {
			toggle = false;
		}
//		if(toggleTime > ) System.out.println("TOGGLE!");
		return bool;
	}
	
	
	// GETTERS AND SETTERS
	public int getX() {
		return MouseManager.mX;
	}
	
	public int getY() {
		return MouseManager.mY;
	}
}