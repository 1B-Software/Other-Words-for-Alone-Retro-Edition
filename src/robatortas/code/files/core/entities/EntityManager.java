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
	
	public void render(RenderManager screen) {
		addons.render(screen);
	}
	
	public void update() {
		System.out.println("ENTITY!");
	}
	
	public void move(int xa, int ya) {
		addons.move(xa, ya);
	}
	
	public void hurt(Mob mob, int damage, int attackDir) {
		
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void remove() {
		removed = true;
	}
	
	public SpriteManager getSprite() {
		return addons.getSprite();
	}
	
	public void init(LevelManager level) {
		this.level = level;
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return addons.intersects(x0, y0, x1, y1);
	}

	public boolean canSwim() {
		return false;
	}
}
