package robatortas.code.files.project.level.levels;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.Level;

public class OutsideLevel extends Level {
	
	Rain rain;
	
	public OutsideLevel(String name, LevelManager level) {
		super(name, level);
	}

	public void init() {
//		levelManager.unload();
		rain = new Rain(10, 2f, this);
		environmentLight = 0.7f;
		LevelManager.player = new Player(3<<4, 5<<4, levelManager.input);
		levelManager.add(LevelManager.player);
	}
	
	public void update() {
		rain.update();
	}
	
	public void render(float x, float y, RenderManager screen) {
		rain.render(x, y, screen);
	}
}
