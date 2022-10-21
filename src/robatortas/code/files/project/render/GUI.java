package robatortas.code.files.project.render;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.menu.InventoryMenu;
import robatortas.code.files.project.utils.Graphical;

/**<NEWLINE>
 * <b>GUI class</b>
 * <br><br>
 * This class takes care of all the Graphical User Interface stuff.
 * 
 * The rendering, handling and updates!
 */

public class GUI {
	
	private GameManager game;
	private Graphical graphical = new Graphical();
	
	private int x, y;
	
	public GUI(GameManager game) {
		this.game = game;
	}
	
	public void update() {
		
	}
	
	public void render(RenderManager screen, GameManager game) {
		this.game = game;
		renderInv(screen);
		
//		graphical.buildLine(5, 5, 10, 5, 5, 0, 10, 10, 0x0000ff, screen);
	}
	
	// Render categories here!
	private boolean chat, inv;
	
	private InventoryMenu inventoryMenu = new InventoryMenu();
	
	private int renderTime = 0;
	public void renderInv(RenderManager screen) {
		inv = game.level.input.toggle(game.level.input.e, inv);
		if(inv) inventoryMenu.render(screen, game);
		
		renderTime++;
		if(inv) {
			if(renderTime % 7 == 0 && y > game.yScroll-30) {
				y -= (renderTime&1);
			}
			x = game.xScroll;
		} else {
			y = game.yScroll;
			x = game.xScroll;
		}
		
		renderBars(screen);
	}
	
	// Renders health and stamina bars
	public void renderBars(RenderManager screen) {
		// Health and Stamina
		int xa = 0;
		int ya = 0;
		int xs = 0;
		int ys = 0;
			
		if(LevelManager.player.health <= 3 || LevelManager.player.hurtTime > 0) {
			ya = screen.random.nextInt(2);
			xa = screen.random.nextInt(2);
		}
		if(LevelManager.player.stamina <= 3) {
			ys = screen.random.nextInt(2);
			xs = screen.random.nextInt(2);
		}
		
		// Health
		graphical.buildBox(7, 12, 12, 1, 0, 0, ((game.screen.width/3) - 2), 8 , 0xff1F1F1F, true, screen);
		for(int i = 0; i < LevelManager.player.health; i++) {
			if(LevelManager.player.health <= 3) {if((game.tickTime / 15) % 2 == 0) continue;}
			graphical.buildBox(8, 8, 1, 1, 0, 0, (game.screen.width/3) + (i*8) + xa, 10 + ya , 0xffff282c, true, screen);
		}

		if(LevelManager.player.hurtTime > 0 && LevelManager.player.health > 0) graphical.buildBox(8, 8, 1, 1, 0, 0, (game.screen.width/3) + (8*Math.max(LevelManager.player.health, LevelManager.player.health)) + xa, 10 + ya, 0xffffffff, true, screen);
		
		// Stamina
		graphical.buildBox(7, 7, 12, 1, 0, 0, ((game.screen.width/3) - 2), 21, 0xff1F1F1F, true, screen);
		for(int i = 0; i < LevelManager.player.stamina; i++) {
			if(LevelManager.player.stamina <= 3) {if((game.tickTime / 15) % 2 == 0) continue;}
			graphical.buildBox(8, 3, 1, 1, 0, 0, (game.screen.width/3) + (i*8) + xa, 10 + 13 + ya , 0xffFFB200, true, screen);
		}
	}
}