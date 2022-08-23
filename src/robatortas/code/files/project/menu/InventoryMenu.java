package robatortas.code.files.project.menu;

import robatortas.code.files.core.input.MouseManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.menu.inventory.BigPocket;

public class InventoryMenu {

	public GameManager game;
	
	public static SpriteSheetManager gui = new SpriteSheetManager("/textures/spritesheet/gui/inventory/medium_pocket_anim.png", 105, 99);
	
	public InventoryMenu(GameManager game) {
		this.game = game;
	}
	
	public void update() {
		
	}
	
	boolean grid;
	public void render(RenderManager screen) {
		grid = game.mouse.toggle(MouseManager.BUTTONS.RIGHT.button, grid);
		if(grid)screen.renderSpriteSheet(screen.width/5 + 6, screen.height/8, inventorySheet, 0, true);
		
		hitBoxes(screen);
	}
	
	public void hitBoxes(RenderManager screen) {
		System.out.println(MouseManager.mY);
		
		// Big pocket
		if(MouseManager.mX >= 329 && MouseManager.mX <= 406 && MouseManager.mY >= 222 && MouseManager.mY <= 344) {
			System.out.println("MOUSE IN!!");
			new BigPocket().render(screen);
		}
	}
	
	SpriteSheetManager inventorySheet = new SpriteSheetManager("/textures/spritesheet/gui/inventory/inventory_blue_book.png", 138, 163);
	
//	SpriteManager inventory = new SpriteManager(138, 163, );
}