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
		
		this.xScroll = (int) (LevelManager.player.x - Constants.WIDTH / 2 + 3) ;
		this.yScroll = (int) (LevelManager.player.y - Constants.HEIGHT / 2);
		
		pixelIterations();
		generalSettings();
		
		if(xScroll > game.level.width * 16 - game.screen.width) xScroll = game.level.width * 16 - game.screen.width + 2;
		if(xScroll < game.level.width - game.screen.width / 7) xScroll = game.level.height * 8 - game.screen.width - 9;
		
		if(yScroll > game.level.height * 16 - game.screen.height + 6) yScroll = game.level.width * 16 - game.screen.height + 6;
		if(yScroll < game.level.height - game.screen.height/6) yScroll = game.level.height * 6 - game.screen.height + 22;
		
		game.level.render(xScroll, yScroll, game.screen);
	}
	
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}
