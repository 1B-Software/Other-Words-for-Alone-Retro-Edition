package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.Animate;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.Animations;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class Player extends Mob {
	
	public InputManager input;
	
	public boolean punch = true;
	public int attackTime, attackDir;
	
	public Player(int x, int y, InputManager input) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.sprite = new SpriteManager(16, 0, 0, SheetArchive.player);
	}
	
	public static int velX = 1;
	public static int velY = 1;	
	
	public int tickTime;
	
	public void update() {
		tickTime++;
		int xa = 0, ya = 0;
		
		animSprite.resetAnimation(animSprite, walking);
		
		// Controls
		if(input.up) ya -= velY;
		if(input.down) ya += velY;
		if(input.left) xa -= velX;
		if(input.right) xa += velX;
		
		if(input.f) {;
			if(punch == false) {
				punch = true;
				attack();
			}
		} else punch = false;
		if(attackTime > 0) attackTime--;
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else walking = false;
	}
	
	public void attack() {
		attackDir = dir;
		attackTime = 5;
	}
	
	public void render(RenderManager screen) {
		if(!walking) {
			if(dir == 0) {
				animSprite = up;
				if(attackTime > 0) animSprite = punchUp;
			}
		} else {
			if(dir == 0) animSprite = up;
			if(dir == 1) animSprite = right;
			if(dir == 2) animSprite = down;
			if(dir == 3) animSprite = left;
		}
		
		sprite = animSprite.getSprite();
		
		beforeLayer(screen);
		screen.renderMob(x, y, this, sprite);
		afterLayer(screen);
	}
	
	public void beforeLayer(RenderManager screen) {
		// Finally using switch case huh?
			switch(attackDir) {
			case 0: 
				if(attackTime > 0) screen.renderSprite(x+8, y-3, SpriteArchive.swingFx, 0);
				break;
			case 1:
				if(attackTime > 0) screen.renderSprite(x+20, y+6, SpriteArchive.swingFx_Sides, 1);
				break;
			case 3:
				if(attackTime > 0) screen.renderSprite(x-3, y+6, SpriteArchive.swingFx_Sides, 0);
				break;
			}
	}
	
	public void afterLayer(RenderManager screen) {
		switch(attackDir) {
		case 2:
			if(attackTime > 0) screen.renderSprite(x+8, y+15, SpriteArchive.swingFx, 2);
			break;
		}
	}
	
	
	// Animations
	public Animate up = new Animate(Animations.playerUp, 3, 3, 3);
	public Animate right = new Animate(Animations.playerRight, 3, 3, 3);
	public Animate down = new Animate(Animations.playerDown, 3, 3, 3);
	public Animate left = new Animate(Animations.playerLeft, 3, 3, 3);
	
	public Animate punchDown = new Animate(Animations.playerPunchDown, 2, 2, 2);
	public Animate punchUp = new Animate(Animations.playerPunchUp, 2, 2, 2);
	
	
	public SpriteManager punchLeft = new SpriteManager(32, 1, 3, SheetArchive.player);
	public SpriteManager punchRight = new SpriteManager(32, 2, 3, SheetArchive.player);
	
	private Animate animSprite = down;
}
