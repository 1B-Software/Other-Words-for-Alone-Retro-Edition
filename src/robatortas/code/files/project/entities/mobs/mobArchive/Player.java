package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.Animate;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.Animations;
import robatortas.code.files.project.archive.SheetArchive;

public class Player extends Mob {
	
	public InputManager input;
	
	public boolean punch = true;
	public int attackTime;
	
	public Player(int x, int y, InputManager input) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.sprite = new SpriteManager(16, 0, 0, SheetArchive.player);
	}
	
	public static int velX = 1;
	public static int velY = 1;	
	
	public void update() {
		
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
				attackTime=10;
			}
		} else punch = false;
		if(attackTime > 0) attackTime--;
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else walking = false;
	}
	
	public void render(RenderManager screen) {
		if(walking) {
			if(dir == 0) animSprite = up;
			if(dir == 1) animSprite = right;
			if(dir == 2) animSprite = down;
			if(dir == 3) animSprite = left;
		}
		
		sprite = animSprite.getSprite();
		
		if(attackTime > 0) {
			sprite = punchSprite;
		}
		System.out.println(attackTime);
		
		screen.renderMob(x, y, this, sprite);
	}
	
	
	// Animations
	public Animate up = new Animate(Animations.playerUp, 3, 3, 3);
	public Animate right = new Animate(Animations.playerRight, 3, 3, 3);
	public Animate down = new Animate(Animations.playerDown, 3, 3, 3);
	public Animate left = new Animate(Animations.playerLeft, 3, 3, 3);
	
	public SpriteManager punchSprite = new SpriteManager(32, 1, 4, SheetArchive.player);
	
	private Animate animSprite = down;
}
