package robatortas.code.files.project.entities.mobs;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.entities.mobs.mobArchive.Chicken;

public class MobAddons extends Mob {
	
	public void move(int xa, int ya) {
		super.xa = xa;
		super.ya = ya;
		
		if(xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if(!collision(super.xa, super.ya)) {
			x += xa;
			y += ya;
		}
		
		if(xa > 0) dir = 1;
		if(xa < 0) dir = 3;
		if(ya > 0) dir = 2;
		if(ya < 0) dir = 0;
	}
	
	public void update() {
		die();
		
		if(hurtTime > 0) hurtTime--;
	}
	
	public void render(RenderManager screen) {
		
	}
	
	public SpriteManager getSprite() {
		return sprite;
	}
	
	
	/////////////
	// HURTING //
	/////////////
	
	public void hurt(Mob mob, int damage, int attackDir) {
		doHurt(damage, attackDir);
		System.out.println("OUCH!");
	}
	
	public void doHurt(int damage, int attackDir) {
		if(hurtTime > 0) return;
		health -= damage;
		if (attackDir == 0) yKnockback = -10;
		if (attackDir == 1) xKnockback = 10;
		if (attackDir == 2) yKnockback = 10;
		if (attackDir == 3) xKnockback = -10;
		hurtTime = 10;
	}
	
	public void knockBack() {
		if(xKnockback > 0) {
			move(1, 0);
			dir = 3;
			xKnockback--;
		}
		if(xKnockback < 0) {
			move(-1, 0);
			dir = 1;
			xKnockback++;
		}
		if(yKnockback > 0) {
			move(0, 1);
			dir = 0;
			yKnockback--;
		}
		if(yKnockback < 0) {
			move(0, -1);
			dir = 2;
			yKnockback++;
		}
	}
	
	public void die() {
		if(health <= 0) {
			remove();
			System.out.println("DEAD!");
		}
	}
	
	
	///////////////
	// Collision //
	///////////////
	
	public boolean collision(int xs, int ys) {
		boolean solid = false;
		for(int c = 0; c < 4; c++) {
			int xt = ((super.x+xs) + c % 2 * 8 + 12) >> 4;
			int yt = ((super.y+ys) + c / 2 * 1 + 22) >> 4;
			// if (SolidMethod() == true) solid = true 
			if(level.getLevel(xt, yt).solid(level, xt, yt, this)) {
				solid = true;
			}
			if(level.getFront(xt, yt).solid(level, xt, yt, this)) {
				solid = true;
			}
		}
//		System.out.println("SOLID = " + solid);
		return solid;
	}
}
