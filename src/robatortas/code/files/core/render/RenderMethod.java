package robatortas.code.files.core.render;


import robatortas.code.files.core.level.GameLevel;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.archive.SpriteArchive;

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
		
		pixelIterations();
		generalSettings();
		
		game.level.render(xScroll, yScroll, game.screen);
		
//		game.screen.renderSprite(game.x, game.y, SpriteArchive.grass);
	}
	
	public void scroll() {
		xScroll = game.level.player.getX();
		yScroll = game.level.player.getX();
	}
	
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}
