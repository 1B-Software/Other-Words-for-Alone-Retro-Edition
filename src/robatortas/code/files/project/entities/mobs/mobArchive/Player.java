package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.Animate;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.archive.Animations;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Player extends MobAddons {
	
	private InputManager input;
	
	public boolean punch = true;
	public int attackTime, attackDir;
	
	public Player(int x, int y, InputManager input) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.sprite = new SpriteManager(16, 0, 0, SheetArchive.player);
		super.dir = 2;
	}
	
	private static int velX = 1;
	private static int velY = 1;	
	private int xs, ys;
	
	public int tickTime;
	
	public void update() {
		this.xs = 0;
		this.ys = 0;
		
		tickTime++;
		
		// Controls
		controls();
		
		// Reset Animations (AVOIDS CRASHING!)
		animSprite.resetAnimation(animSprite, walking);
		
		if(xs != 0 || ys != 0) {
			move(xs, ys);
			walking = true;
		} else walking = false;
	}
	
	public void attack() {
		attackDir = dir;
		attackTime = 5;
	}
	
	public void render(RenderManager screen) {
		
		if(dir == 0) animSprite = up;
		if(dir == 1) animSprite = right;
		if(dir == 2) animSprite = down;
		if(dir == 3) animSprite = left;
		
		if(!walking) {
			if(attackTime > 0) {
				if(dir == 0) animSprite = punchUp;
				if(dir == 1) animSprite = punchUp;
				if(dir == 2) animSprite = punchDown;
				if(dir == 3) animSprite = punchUp;
				
				if((tickTime / 8) % 2 == 0) {
					animSprite.setFrame(1);
				}
			}
		}
		
		System.out.println(tickTime/32);
		
		sprite = animSprite.getSprite();
		
		beforeLayer(screen);
		screen.renderMob(x, y, this, sprite, 0);
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
	
	
	public void controls() {
		// Controls
		if(input.up) ys -= velY;
		if(input.down) ys += velY;
		if(input.left) xs -= velX;
		if(input.right) xs += velX;
		
		if(input.f) {;
			if(punch == false) {
				punch = true;
				attack();
			}
		} else punch = false;
		if(attackTime > 0) attackTime--;
		
		if(GameManager.DEV_MODE) {
			if(input.shift) {
				velX = 2;
				velY = 2;
			} else {
				velX = 1;
				velY = 1;
			}
		}
	}
	
	// Animations
	public Animate up = new Animate(Animations.playerUp, 1, 3, 3);
	public Animate right = new Animate(Animations.playerRight, 1, 3, 3);
	public Animate down = new Animate(Animations.playerDown, 1, 3, 3);
	public Animate left = new Animate(Animations.playerLeft, 1, 3, 3);
	
	public Animate punchDown = new Animate(Animations.playerPunchDown, 2, 1, 2);
	public Animate punchUp = new Animate(Animations.playerPunchUp, 2, 1, 2);
	
	
	public SpriteManager punchLeft = new SpriteManager(32, 1, 3, SheetArchive.player);
	public SpriteManager punchRight = new SpriteManager(32, 2, 3, SheetArchive.player);
	
	private Animate animSprite = down;
}