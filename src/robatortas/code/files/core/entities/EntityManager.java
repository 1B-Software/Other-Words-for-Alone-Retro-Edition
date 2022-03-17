package robatortas.code.files.core.entities;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.entities.EntityAddons;

public class EntityManager {
	
	public int x, y;
	public SpriteManager sprite;
	public LevelManager level;
	
	public boolean removed;
	
	public EntityAddons addons = new EntityAddons(this);
	
	public EntityManager() {
		
	}
	
	public EntityManager(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(int x, int y, RenderManager screen) {
		addons.render(x, y, screen);
	}
	
	public void update() {
	}
	
	public void move(int xa, int ya) {
		addons.move(xa, ya);
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(LevelManager level) {
		this.level = level;
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return addons.intersects(x0, y0, x1, y1);
	}
}
