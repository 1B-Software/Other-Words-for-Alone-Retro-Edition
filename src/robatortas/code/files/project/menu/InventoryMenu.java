package robatortas.code.files.project.menu;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.GameManager;

public class InventoryMenu {

	public GameManager game;
	
	public InventoryMenu(GameManager game) {
		this.game = game;
	}
	
	public void update() {
		
	}
	
	public void render(RenderManager screen) {
//		screen.renderBox(LevelManager.player.x, LevelManager.player.y, 20, 20, 0xffff00ff);
		
		screen.renderSpriteSheet(screen.width/5 + 6, screen.height/8, inventorySheet, 0, true);
	}
	
	SpriteSheetManager inventorySheet = new SpriteSheetManager("/textures/spritesheet/gui/inventory/InventorywithBlueBook.png", 138, 163);
	
//	SpriteManager inventory = new SpriteManager(138, 163, );
}