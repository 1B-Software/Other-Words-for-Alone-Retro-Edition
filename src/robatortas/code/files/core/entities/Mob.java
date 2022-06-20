package robatortas.code.files.core.entities;

import java.util.Random;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Mob extends EntityManager {

	public int xa, ya;
	
	protected int avgHealth = 10;
	public int health = avgHealth;
	
	// Time the enemy will stay hurt
	public int hurtTime = 0;
	protected int swimTime = 0;
	
	protected int xKnockback, yKnockback;
	
	protected boolean walking = false;
	protected boolean isSwimming = false;
	public int dir = 3;
	
	public void move(int xa, int ya) {
		
	}
	
	public void update() {
		
	}
	
	public void render(RenderManager screen) {
		
	}
}