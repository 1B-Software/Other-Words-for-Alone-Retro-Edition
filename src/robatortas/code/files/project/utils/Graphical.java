package robatortas.code.files.project.utils;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

/**<NEWLINE>
 * <b>Graphical class</b>
 * <br><br>
 * Utils library that contains all necessary methods for
 * the Graphical User Interface (GUI).
 * <br>
 * All methods that require some sort of rendering need to go in the render method of course!
 * <br>
 * The library isn't big at all but it does all sorts of helpful tasks.
 */

public class Graphical {
	
	/**<NEWLINE>
	 * <b>buildLine function in Graphical class</b>
	 * <br><br>
	 * Build a line (Ex: health bar, progress bar).
	 * @param portionWidth Means how long each individual square will be in the w.
	 * @param portionHeight Means how long each individual square will be in the h.
	 * @param width Means how long the bar will be in the width.
	 * @param height Means how long the bar will be in the height.
	 * @param xOffset How much each piece of the bar is offsetted and adding it for each time the i variable iterates.
	 * @param yOffset How much each piece of the bar is offsetted and adding it for each time the i variable iterates.
	 * @param x Location of the bar in the x axis.
	 * @param y Location of the bar in the x axis.
	 * @param color The color of the bar.
	 * @param screen Used to render
	 * 
	 * @see RenderManager
	 * 
	 */
	public void buildLine(int portionWidth, int portionHeight, int width, int height, int xOffset, int yOffset, int x, int y, int color, RenderManager screen) {
		if(xOffset == 0) xOffset = portionWidth;
		if(yOffset == 0) yOffset = portionHeight;
		for(int i = 0; i < width; i++) screen.renderBox(x+(i*xOffset), y+(i*yOffset), portionWidth, portionHeight, color, false);
	}
	
	/**<NEWLINE>
	 * <b>builcContainer function in Graphical class</b>
	 * <br><br>
	 * Build a container.
	 * 
	 * @param Width the width of the whole container.
	 * @param Height the height of the whole container.
	 * @param x The position on the x axis.
	 * @param y The position on the y axis.
	 * @param sprite The sprite that the container will use.
	 * @param screen Used to render.
	 * 
	 * @see RenderManager
	 */
	public void buildContainer(int width, int height, int x, int y, SpriteManager sprite, RenderManager screen) {
		
	}
	
	/**<NEWLINE>
	 * Build basic movement animations for any gui object.
	 */
	public void buildAnimation() {
		
	}
}