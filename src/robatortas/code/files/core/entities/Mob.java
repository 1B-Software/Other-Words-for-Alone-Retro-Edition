package robatortas.code.files.core.entities;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Mob extends EntityManager {

	private MobAddons addons = new MobAddons();
	
	protected boolean walking = false;
	
	public int xa, ya;
	public void move(int xa, int ya) {
		this.xa = xa;
		this.ya = ya;
		
		if(xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
		}
		
		addons.move();
	}
	
	public void update() {
		addons.render();
	}
	
	public void render(int x, int y, RenderManager screen) {
		addons.render();
	}
}