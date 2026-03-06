package robatortas.code.files.core.entities;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class Mob extends EntityManager {

	public float xa, ya;
	
	protected int avgHealth = 10;
	public int health = avgHealth;
	
	// Time the enemy will stay hurt
	public int hurtTime = 0;
	protected int swimTime = 0;
	
	protected int xKnockback, yKnockback;
	
	protected boolean walking = false;
	protected boolean isSwimming = false;
	public int dir = 3;
	
	public void move(float xa, float ya) {
		super.move(xa, ya);
	}
	
	public void move2(float xa, float ya) {
		super.move2(xa, ya);
	}
	
	public boolean isSwimming() {
		if(LevelManager.level.getLevel((int)x >> 4, (int)y >> 4) == TileArchive.water) isSwimming = true;
		else isSwimming = false;
		return isSwimming;
	}
	
	public void update() {
		super.update();
	}
	
	public void render(RenderManager screen) {
		
	}
}