package robatortas.code.files.project.level;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;

public class LevelRenderManager {
	
	private RenderManager screen;
	private LevelManager level;
	
	public LevelRenderManager(LevelManager level, RenderManager screen) {
		this.level = level;
		this.screen = screen;
	}
	
	// Level Rendering goes here!
	public void render(LevelManager level) {
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x < 0 || y < 0 || x >= screen.width|| y >= screen.height) continue;
				level.getLevel(x, y).render(x, y, level, screen);
				level.getFront(x, y).render(x, y, level, screen);
				
			}
		}
	}
	
	// pinPoint settings
	public int x0,y0,x1,y1; 
	public void pinPoints(int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);
		x0 = (xScroll) >> 4;
		x1 = (xScroll + screen.width + 16) >> 4;
		y0 = (yScroll) >> 4;
		y1 = (yScroll + screen.height + 16) >> 4;
	}

	public void renderEntities(int x, int y) {
		for(int i = 0; i < level.entities.size(); i++) {
			level.entities.get(i).render(screen);
		}
	}
}
