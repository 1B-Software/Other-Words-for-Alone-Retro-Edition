package robatortas.code.files.project.menu;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.GameManager;

public class InventoryMenu {

	public GameManager game;
	
	public InventoryMenu(GameManager game) {
		this.game = game;
	}
	
	public void update() {
		
	}
	
	public void render(RenderManager screen) {
		screen.renderBox(LevelManager.player.x, LevelManager.player.y, 20, 20, 0xffff00ff);
	}
}