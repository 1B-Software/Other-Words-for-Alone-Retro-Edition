package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Bee extends MobAddons {
	
	public boolean punch = true;
	public int attackTime, attackDir;
	
	public Bee(int x, int y) {
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
		super.update();
		tickTime++;
		
		// TODO: Movements
		if(tickTime % (random.nextInt(5) + 5) == 0) {
			xa = random.nextInt(3)-1;
			ya = random.nextInt(3)-1;
		}
		
		if(xa == 1) dir = 1;
		if(xa == -1) dir = 3;
		if(ya == 1) dir = 2;
		if(ya == -1) dir = 0;
		
		int speed = tickTime & 1;
		
		move(xa * speed, ya * speed);
		walking = true;
	}
	
	public void render(RenderManager screen) {
		screen.renderBox(x, y, 2, 2, 0xffffc300);
		screen.renderBox(x+2, y, 2, 2, 0xff000000);
		
		if((tickTime / 3) % 2 == 0) screen.renderBox(x + 1, y - 2, 2, 2, 0xffd8fbff);
		else screen.renderBox(x + 1, y , 2, 2, 0xffd8fbff);
		
		if(dir == 1) {
			screen.renderBox(x, y, 2, 2, 0xffffc300);
			screen.renderBox(x+2, y, 2, 2, 0xff000000);
		}
		if(dir == 3) {
			screen.renderBox(x+2, y, 2, 2, 0xffffc300);
			screen.renderBox(x, y, 2, 2, 0xff000000);
		}
	}
	
	public void die() {
	}
}
