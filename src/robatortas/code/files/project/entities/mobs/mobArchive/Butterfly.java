package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
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
//		if(tickTime % (random.nextInt(5) + 5) == 0) {
//			xa = random.nextInt(3)-1;
//			ya = random.nextInt(3)-1;
//		}
		
		if(xa == 1) dir = 1;
		if(xa == -1) dir = 3;
		if(ya == 1) dir = 2;
		if(ya == -1) dir = 0;
		
		int speed = tickTime & 1;
		
		move(xa * speed, ya * speed);
		walking = true;
	}
	
	public void render(RenderManager screen) {
		screen.renderBox(x, y, 2, 2, 0xff216D16);
		screen.renderBox(x - 1, y + 1, 2, 2, 0xff216D16);
		
		if((tickTime / 3) % 2 == 0) screen.renderBox(x - 2, y - 1, 2, 2, randColor);
		else screen.renderBox(x, y + 1, 2, 2, randColor);
	}
	
	
	public boolean canSwim() {
		return true;
	}
	
	public void die() {
	}
}
