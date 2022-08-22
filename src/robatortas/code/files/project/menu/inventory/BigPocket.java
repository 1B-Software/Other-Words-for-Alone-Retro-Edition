package robatortas.code.files.project.menu.inventory;

import robatortas.code.files.core.render.RenderManager;

public class BigPocket {
	
	public void update() {
		
	}
	
	public void render(RenderManager screen) {
		for(int i = 0; i < 10; i++) screen.renderBox(20*i, 10, 2, 40, 0xffffffff);
		for(int i = 0; i < 10; i++) screen.renderBox(20*i, 10, 40, 2, i);
	}
	
//	public static SpriteSheet bigPocketSheet = new SpriteSheet("insert this later!!!", 96, 96);
	
//	public static Animate pocketAnim = new Animate();
}