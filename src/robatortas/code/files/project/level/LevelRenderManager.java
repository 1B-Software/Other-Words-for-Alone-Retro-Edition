package robatortas.code.files.project.level;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;

public class LevelRenderManager {
	
	private RenderManager screen;
	
	public LevelRenderManager(RenderManager screen) {
		this.screen = screen;
	}
	
	// Level Rendering goes here!
	public void render(int x, int y, LevelManager level) {
		level.getLevel(x, y).render(x, y, screen);
	}
	
	// pinPoint settings
	public int x0,y0,x1,y1; 
	public void pinPoints(int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);
		x0 = xScroll >> 4;
		x1 = (xScroll + screen.width + 16) >> 4;
		y0 = yScroll >> 4;
		y1 = (yScroll + screen.height + 16) >> 4;
	}
}
