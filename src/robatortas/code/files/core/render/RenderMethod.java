package robatortas.code.files.core.render;

import robatortas.code.files.core.level.LevelManager;
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
		
		this.xScroll = (int) (LevelManager.player.x - Constants.WIDTH / 2.1f) ;
		this.yScroll = (int) (LevelManager.player.y - Constants.HEIGHT / 2.1f);
		
		pixelIterations();
		generalSettings();
		
		System.out.println(xScroll);
		game.level.render(xScroll, yScroll, game.screen);
	}
	
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}
