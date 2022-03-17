package robatortas.code.files.core.render;

import robatortas.code.files.project.GameManager;

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
		
		this.xScroll = game.level.player.x;
		this.yScroll = game.level.player.y;
		
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
