package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Butterfly extends MobAddons {
	
	private int randColor;
	
	public Butterfly(int x, int y) {
		this.x = x;
		this.y = y;
		this.randColor = random.nextInt(0xffffff);
	}
	
	public static int velX = 1;
	public static int velY = 1;	
	
	private int xa = 0; 
	private int ya = 0;
	
	public int tickTime = 0;
	
	public void update() {
		super.update();
		tickTime++;
		
		// TODO: Movements
		if(tickTime % (random.nextInt(20) + 20) == 0) {
			xa = random.nextInt(3)-1;
			ya = random.nextInt(3)-1;
		}
		
		if(xa == 1) dir = 1;
		if(xa == -1) dir = 3;
		if(ya == 1) dir = 2;
		if(ya == -1) dir = 0;
		
		int speed = 0;
		
		if(tickTime % 3 == 0) {
			speed = tickTime & 2;
		}
		
		move(xa * speed, ya * speed);
		walking = true;
	}
	
	public void render(RenderManager screen) {
		int num = 0;
		
		if((tickTime / 4) % 2 == 0) sprite = new SpriteManager(8, 0, 0, butterfly);
		else {
			sprite = new SpriteManager(8, 1, 0, butterfly);
			num = 2;
		}
		
		for(int yy = sprite.y; yy < sprite.height; yy++) {
			for(int xx = sprite.y; xx < sprite.width; xx++) {
				if(sprite.pixels[xx+yy*sprite.width] == 0xff7F7F7F) sprite.pixels[xx+yy*sprite.width] = 0xff << 24 | randColor;
			}
		}
		
		int flip = 0;
		
		switch(dir) {
		case 0: flip = 1;
		break;
		case 1: flip = 0;
		break;
		case 2: flip = 0;
		break;
		case 3: flip = 1;
		}
		
		screen.renderMob(x - num, y - num - 13, this, sprite, flip);
	}
	
	private static SpriteSheetManager butterfly = new SpriteSheetManager("/textures/spritesheet/mob/butterfly.png", 16, 8);
	
	public boolean canSwim() {
		return true;
	}
	
	public void die() {
	}
	
	public boolean isInvincible() {
		return true;
	}
}
