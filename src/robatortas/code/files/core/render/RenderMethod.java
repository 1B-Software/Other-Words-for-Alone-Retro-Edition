package robatortas.code.files.core.render;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.menu.InventoryMenu;
import robatortas.code.files.project.render.GUI;
import robatortas.code.files.project.settings.Constants;

public class RenderMethod {
	
	private GameManager game;
	private LevelManager level;
	
	private RenderManager screen;
	
	private Fonts font = new Fonts();
	
	public static int xScroll, yScroll;
	
	// General settings for the rendering
	public void generalSettings() {
		game.screen.clear(true);
	}
	
	/**<NEWLINE>
	 * RenderManager's render function
	 * <br><br>
	 * Contains all the code to render stuff.
	 * 
	 * This method gets called on the GameManager class 
	 * ({@link GameManager})
	 * <br>
	 * The GameManager's render method takes care of actually rendering everything
	 * 
	 * @param game The class that takes care of the general game handling
	 * 
	 * @see GameManager
	 */
	public void render(GameManager game) {
		this.game = game;
		this.level = game.level;
		this.screen = game.screen;
		
		xScroll = (int) (LevelManager.player.x - Constants.WIDTH / 2 + 3) ;
		yScroll = (int) (LevelManager.player.y - Constants.HEIGHT / 2);
		
		pixelIterations();
		generalSettings();
		
		if(xScroll > level.width * 16 - screen.width) xScroll = level.width * 16 - screen.width + 2;
		if(xScroll < level.width - screen.width / 7) xScroll = level.height * 8 - screen.width - 9;
		
		if(yScroll > level.height * 16 - screen.height + 6) yScroll = level.width * 16 - screen.height + 6;
		if(yScroll < level.height - screen.height/6) yScroll = level.height * 6 - screen.height + 22;
		
		level.render(xScroll, yScroll, screen);
		
		renderGUI();
		renderDebug();
	}

	private boolean chat, inv;
	private int x;
	private int y;
	int renderTime = 0;
	
	private GUI gui = new GUI();
	
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
	int debugColor = 0x6f0000ff;
	public void renderDebug() {

		font.setSize(8*2);
		if(game.DEBUG) {
			font.draw("E:" + level.entities.size(),0, 5, false, screen);
			font.draw("X:" + (LevelManager.player.x >> 4) + " Y:" + (LevelManager.player.y >> 4), 2, 5*3, false, screen);
			font.draw("Dev_Mode:" + GameManager.DEV_MODE, 2, 5*5, false, screen);
			
			font.setColor(0xffdfef00);
			font.draw("\"DEV_TOOLS\"", 0, 5*13, false, screen);
			font.setColor(0x6f << 24 | ((debugColor & 0x00ff0000) >> 16) + game.resources.memory/2 << 16 | 0x00 << 8 | 0xff);
			font.draw("MEMORY: " + game.resources.memory + " mb", 0, 5*15, false, screen);
			font.draw("MAX_MEMORY: " + game.resources.maxMemory + " mb", 0, 5*17, false, screen);
			font.setColor(debugColor);
			font.draw("PROCESSORS: " + game.resources.processors, 1, 5*19, false, screen);
		}
	}
	
	/**<NEWLINE>
	 * PixelIterations function
	 * <br><br>
	 * Iterates through the pixels in the screen class and passes them to the buffer pixels.
	 * 
	 * @see RenderManager
	 */
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {	
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}