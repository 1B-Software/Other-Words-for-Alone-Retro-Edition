package robatortas.code.files.project.entities;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.RenderManager;

public class EntityAddons {
	
	private EntityManager entity;
	
	private int x, y;
	
	public EntityAddons(EntityManager entity) {
		this.entity = entity;
		this.x = entity.x;
		this.y = entity.y;
	}
	
	public void render(int x, int y, RenderManager screen) {
		
	}
	
	public void update() {
		
	}
	
	public void move() {
		
	}
	
	public boolean intersects(int x0, int y0, int x1, int y1) {
		return (x + 8 < x0 || y + 8 < y0 || x - 8 > x1 || y - 8 > y1);
	}
}
