package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.render.Animate;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.Animations;
import robatortas.code.files.project.archive.SheetArchive;

public class Chicken extends Mob {
	
	public boolean punch = true;
	public int attackTime, attackDir;
	
	public Chicken(int x, int y) {
		this.x = x;
		this.y = y;
		this.sprite = new SpriteManager(16, 0, 0, SheetArchive.player);
	}
	
	public static int velX = 1;
	public static int velY = 1;	
	
	private int xa = 0; 
	private int ya = 0;
	
	public int tickTime = 0;
	
	public void update() {
		tickTime++;
		
		animSprite.resetAnimation(animSprite, walking);
		
		// TODO: Movements
		
		if(tickTime % (random.nextInt(30) + 30) == 0) {
			xa = random.nextInt(3)-1;
			ya = random.nextInt(3)-1;
			if(random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		
		if(xa == 1) dir = 1;
		if(xa == -1) dir = 3;
		if(ya == 1) dir = 2;
		if(ya == -1) dir = 0;
		
		int speed = tickTime & 1;
		
		if(xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;
		} else walking = false;
	}
	
	
	// TODO: FIX THIS!
	public void render(RenderManager screen) {
		if(walking) {
			switch(dir) {
			case 0: animSprite = left;
			break;
			case 1: animSprite = right;
			break;
			case 2: animSprite = left;
			break;
			case 3: animSprite = left;
			break;
			}
			
			if(dir == 3 && dir == 0) animSprite = left;
			if(dir == 3 && dir == 2) animSprite = left;
			
			if(dir == 1 && dir == 0) animSprite = right;
			if(dir == 1 && dir == 2) animSprite = right;
		}
		
		sprite = animSprite.getSprite();
		
		screen.renderMob(x, y, this, sprite);
	}
	

	private Animate left = new Animate(Animations.chickenLeft, 1, 3, 3);
	private Animate right = new Animate(Animations.chickenRight, 1, 3, 3);
	
	private Animate animSprite = right;
}
