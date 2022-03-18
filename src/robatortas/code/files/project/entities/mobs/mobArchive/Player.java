package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;

public class Player extends Mob {
	
	public InputManager input;
	
	public SpriteManager testPlayerRender = new SpriteManager(32, 0, 0, SheetArchive.player);
	
	public Player(int x, int y, InputManager input) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.sprite = testPlayerRender;
	}
	
	public static int velX = 1;
	public static int velY = 1;	
	
	public void update() {
		
		int xa = 0, ya = 0;
		//Controls the offset of the tiles with keys
		
		if(input.up) ya -= velY;
		if(input.down) ya += velY;
		if(input.left) xa -= velX;
		if(input.right) xa += velX;
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}
	
	public void render(RenderManager screen) {
		sprite = testPlayerRender;
		
		screen.renderMob(x, y, this, sprite);
	}
}
