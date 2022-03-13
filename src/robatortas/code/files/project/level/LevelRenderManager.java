package robatortas.code.files.project.level;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;

public class LevelRenderManager {
	
	// Level Rendering goes here!
	public void render(int x, int y, RenderManager screen, LevelManager level) {
		level.getLevel(x, y).render(x, y, screen);
	}
}
