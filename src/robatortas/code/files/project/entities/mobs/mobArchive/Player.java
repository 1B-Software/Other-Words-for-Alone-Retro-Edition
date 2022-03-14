package robatortas.code.files.project.entities.mobs.mobArchive;

import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.archive.SpriteArchive;

public class Player extends Mob{
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		
	}
	
	public void render(int x, int y, RenderManager screen) {
		screen.renderSprite(x, y, SpriteArchive.voidSprite);
	}
}
