package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;

public class Player extends Mob {
	
	public InputManager input = new InputManager();
	
	public Player(int x, int y, InputManager input) {
		this.x = x;
		this.y = y;
	}
	
	public static int velX = 1;
	public static int velY = 1;	
	
	public void update() {
		System.out.println("Player tick");
		
		int xa = 0, ya = 0;
		//Controls the offset of the tiles with keys
		
		if(input.up) ya -= velY;
		if(input.down) ya += velY;
		if(input.left) xa -= velX;
		if(input.right) xa += velX;
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			System.out.println("moving!");
		}
		
		x++;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void render(int x, int y, RenderManager screen) {
		screen.renderSprite(x, y, testPlayerRender);
	}
	
	public SpriteManager testPlayerRender = new SpriteManager(16, 1, 1, SheetArchive.player);
}
