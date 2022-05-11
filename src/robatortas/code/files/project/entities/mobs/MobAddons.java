package robatortas.code.files.project.entities.mobs;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.render.SpriteManager;

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
		
	}
	
	public void render() {
		
	}
	
	public SpriteManager getSprite() {
		return sprite;
	}
	
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
	
	// TODO: Make death! for much later in development tho..
	public void die() {
		
	}
}
