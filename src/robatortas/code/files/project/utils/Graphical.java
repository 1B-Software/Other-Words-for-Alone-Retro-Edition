package robatortas.code.files.project.utils;

import robatortas.code.files.core.render.RenderManager;

/**<NEWLINE>
 * Utils library that contains all necessary methods for
 * the Graphical User Interface (GUI).
 * <br>
 * All methods that require some sort of rendering need to go in the render method of course!
 * <br>
 * The library isn't big at all but it does all sorts of helpful tasks.
 */

public class Graphical {
	
	/**<NEWLINE>
	 * Build a line (Ex: health bar, progress bar).
	 * <br>
	 * -portionWidth & portionHeight mean how long each individual square will be in the w & h.
	 * <br>
	 * -lengthWidth & lengthHeight mean how long the bar will be in the w & h.
	 * <br>
	 * -xOffset & yOffset are how much each piece of the bar is offsetted and adding it for each time the i variable iterates.
	 * <br>
	 * -x & y is the location of the bar.
	 * <br>
	 * -color is the color of the bar.
	 * <br>
	 * -screen is the screen (used to render).
	 * 
	 */
	public void buildLine(int portionWidth, int portionHeight, int lengthWidth, int lengthHeight, int xOffset, int yOffset, int x, int y, int color, RenderManager screen) {
		if(xOffset == 0) xOffset = portionWidth;
		if(yOffset == 0) yOffset = portionHeight;
		for(int i = 0; i < lengthWidth; i++) screen.renderBox(x+(i*xOffset), y+(i*yOffset), portionWidth, portionHeight, color, false);
	}
	
	// Build a container for its rendering.
	public void buildContainer() {
		
	}
	
	// Build basic movement animations for any gui object.
	public void buildAnimation() {
		
	}
}