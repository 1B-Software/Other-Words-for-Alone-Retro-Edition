package robatortas.code.files.project.entities;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class EntityAddons {
	
	@SuppressWarnings("unused")
	private EntityManager entity;
	
	private int x, y;
	
	public EntityAddons(EntityManager entity) {
		this.entity = entity;
		this.x = entity.x;
		this.y = entity.y;
	}
	
	public void render(RenderManager screen) {
		
	}
	
	public void update() {
		
	}
	
	public void move(int xa, int ya) {
		this.x = xa;
		this.y = ya;
		
	}
}
