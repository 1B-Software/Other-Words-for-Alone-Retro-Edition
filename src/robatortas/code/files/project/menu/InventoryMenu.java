package robatortas.code.files.project.menu;

import robatortas.code.files.core.input.MouseManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.menu.inventory.BigPocket;

public class InventoryMenu {

	public static SpriteSheetManager gui = new SpriteSheetManager("/textures/spritesheet/gui/inventory/medium_pocket_anim.png", 105, 99);
	
	public void update() {
		
	}
	
	boolean grid = false;
	
	public void render(RenderManager screen, GameManager game) {
		screen.renderSpriteSheet(screen.width/5 + 6, screen.height/8, inventorySheet, 0, true);
		grid = game.mouse.toggle(MouseManager.LEFT, grid);
		if(grid) clickBoxes(screen, game);
	}
	
	public void clickBoxes(RenderManager screen, GameManager game) {screen.renderBox(10, 10, 10, 10, 0xff0000ff, true);
		// Big pocket
		if(MouseManager.mX >= 329 && MouseManager.mX <= 406 && MouseManager.mY >= 222 && MouseManager.mY <= 344) {
			new BigPocket().render(screen, game);
			screen.renderBox(10, 10, 10, 10, 0xff0000ff, true);
		}
		
		// Medium Pocket
		
	}
	
	private static SpriteSheetManager inventorySheet = new SpriteSheetManager("/textures/spritesheet/gui/inventory/inventory_blue_book.png", 138, 163);
}