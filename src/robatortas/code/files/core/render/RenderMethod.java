package robatortas.code.files.core.render;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.menu.InventoryMenu;
import robatortas.code.files.project.render.GUI;
import robatortas.code.files.project.settings.Constants;

public class RenderMethod {
	
	private GameManager game;
	
	private RenderManager screen;
	
	public static int xScroll, yScroll;
	
	// General settings for the rendering
	public void generalSettings() {
		game.screen.clear(true);
	}
	
	// Rendering

	public void render(GameManager game) {
		this.game = game;
		this.screen = game.screen;
		
		xScroll = (int) (LevelManager.player.x - Constants.WIDTH / 2 + 3) ;
		yScroll = (int) (LevelManager.player.y - Constants.HEIGHT / 2);
		
		pixelIterations();
		generalSettings();
		
		if(xScroll > game.level.width * 16 - game.screen.width) xScroll = game.level.width * 16 - game.screen.width + 2;
		if(xScroll < game.level.width - game.screen.width / 7) xScroll = game.level.height * 8 - game.screen.width - 9;
		
		if(yScroll > game.level.height * 16 - game.screen.height + 6) yScroll = game.level.width * 16 - game.screen.height + 6;
		if(yScroll < game.level.height - game.screen.height/6) yScroll = game.level.height * 6 - game.screen.height + 22;
		
		game.level.render(xScroll, yScroll, game.screen);
		
		renderGUI();
	}

	private boolean chat, inv;
	private int x;
	private int y;
	int renderTime = 0;
	
	private GUI gui = new GUI(game);
	
	public void renderGUI() {
		// Chat
		chat = game.level.input.toggle(game.level.input.c, chat);
		
//		if(chat) {
//			String input = Arrays.toString(KeyBoard.input);
//			screen.renderBox(x, y + (screen.width-76), 100, 40, 0xff3C1D13, false);
//			font.setSize(2);
//			font.draw(input, x, y + (screen.width-76), true, screen);
//		}
		
		gui.render(screen, game);
	}
	
	// Iterates through the pixels in the screen class and passes them to the buffer pixels.
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {	
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}