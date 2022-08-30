package robatortas.code.files.project.menu.inventory;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.GameManager;

public class BigPocket {
	
	public void update() {
		
	}
	
	public void render(RenderManager screen, GameManager game) {
		for(int i = 0; i < 10; i++) screen.renderBox(game.xScroll*i, game.yScroll, 2, 40, 0xffffffff);
		for(int i = 0; i < 10; i++) screen.renderBox(game.xScroll*i, game.yScroll, 40, 2, i);
	}
	
//	public static SpriteSheet bigPocketSheet = new SpriteSheet("insert this later!!!", 96, 96);
	
//	public static Animate pocketAnim = new Animate();
}