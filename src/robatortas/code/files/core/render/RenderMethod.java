package robatortas.code.files.core.render;

import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.settings.Constants;

public class RenderMethod {
	
	private GameManager game;
	
	private int xScroll, yScroll;
	
	// General settings for the rendering
	public void generalSettings() {
		game.requestFocus();
		game.screen.clear(true);
	}
	
	// Rendering
	public void render(GameManager game) {
		this.game = game;
		
		this.xScroll = (int) (game.level.player.x - Constants.WIDTH / 2.5f) ;
		this.yScroll = (int) (game.level.player.y - Constants.HEIGHT / 2.5f);
		
		pixelIterations();
		generalSettings();
		
		game.level.render(xScroll, yScroll, game.screen);
	}
	
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}
