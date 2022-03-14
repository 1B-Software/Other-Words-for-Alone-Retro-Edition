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
	
	public EntityAddons addons = new EntityAddons();
	
	public EntityManager() {
		
	}
	
	public EntityManager(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(int x, int y, RenderManager screen) {
		addons.render();
	}
	
	public void update() {
		addons.update();
	}
	
	public void move() {
		addons.move();
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(LevelManager level) {
		this.level = level;
	}
}
