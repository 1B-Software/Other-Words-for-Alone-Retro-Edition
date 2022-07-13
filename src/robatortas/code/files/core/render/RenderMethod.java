package robatortas.code.files.core.render;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.archive.SpriteArchive;
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
		renderGUI();
	}
	
	int xa = 0;
	int ya = 0;
	public void renderGUI() {
		RenderManager screen = game.screen;
		int x = xScroll;
		int y = yScroll;
		
		if(game.level.player.health <= 3) {
			ya = screen.random.nextInt(2);
			xa = screen.random.nextInt(2);
		}
		
		for(int i = 0; i < 10; i++) {
			screen.renderColor(((x + (game.screen.width/3) + 7) + xa) + (i*11), (y + 4) + ya, SpriteArchive.cori, 0, 0xff1F1F1F);
		}
		
		for(int i = 0; i < game.level.player.health; i++) {
			if(game.level.player.health <= 3) {
				if((game.tickTime / 15) % 2 == 0) continue;
			}
			screen.renderSprite((x + (game.screen.width/3) + 7) + (i*11), y + 4, SpriteArchive.cori, 0);
		}
		
		for(int i = 0; i < 5; i++) screen.renderSprite((x + (game.screen.width/3) + 7) + (i*11), y + 16, SpriteArchive.stamina, 0);
	}
	
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}
