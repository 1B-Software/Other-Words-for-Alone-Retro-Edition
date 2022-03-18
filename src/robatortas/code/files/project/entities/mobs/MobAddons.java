package robatortas.code.files.project.entities.mobs;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.render.SpriteManager;

public class MobAddons {
	
	private Mob mob;
	
	public MobAddons(Mob mob) {
		this.mob = mob;
	}
	
	public void move() {
		
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
	
	public SpriteManager getSprite() {
		return mob.sprite;
	}
	
	// TODO: Make death! for much later in development tho..
	public void die() {
		
	}
}
