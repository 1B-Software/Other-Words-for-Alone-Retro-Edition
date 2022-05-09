package robatortas.code.files.core.entities;

import java.util.Random;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Mob extends EntityManager {

	public int xa, ya;
	
	protected Random random = new Random();
	
	protected boolean walking = false;
	public int dir = 3;
	
	public void move(int xa, int ya) {
		
	}
	
	public boolean collision(int xs, int ys) {
		return false;
	}
	
	public void update() {
		addons.update();
	}
	
	public void render(int x, int y, RenderManager screen) {
		
	}
}