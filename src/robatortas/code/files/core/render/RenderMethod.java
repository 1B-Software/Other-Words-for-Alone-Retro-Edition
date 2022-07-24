package robatortas.code.files.core.render;

import java.io.InputStream;
import java.util.Arrays;

import robatortas.code.files.core.input.KeyBoard;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.settings.Constants;

public class RenderMethod {
	
	private GameManager game;
	
	public static int xScroll, yScroll;
	
	// General settings for the rendering
	public void generalSettings() {
		game.requestFocus();
		game.screen.clear(true);
	}
	
	// Rendering
	public void render(GameManager game) {
		this.game = game;
		
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

	private boolean chat;
	
	public void renderGUI() {
		RenderManager screen = game.screen;
		int x = xScroll;
		int y = yScroll;
		
		Fonts font = new Fonts();
		
		chat = game.level.input.toggle(game.level.input.c, chat);
		
		if(chat) {
			String input = Arrays.toString(KeyBoard.input);
			screen.renderBox(x, y + (screen.width-76), 100, 40, 0xff3C1D13);
			font.fontSize(2);
			font.draw(input, x, y + (screen.width-76), true, screen);
		}
		
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
		screen.renderBox((x + (game.screen.width/3) - 2), y + 8, 84, 12, 0xff1F1F1F);
		for(int i = 0; i < LevelManager.player.health; i++) {
			if(LevelManager.player.health <= 3) {if((game.tickTime / 15) % 2 == 0) continue;}
			screen.renderBox((x + game.screen.width/3) + (i*8) + xa, (y + 10) + ya, 8, 8, 0xffFF282C);
		}
		if(LevelManager.player.hurtTime > 0 && LevelManager.player.health > 0) screen.renderBox((x + game.screen.width/3) + (8*Math.max(LevelManager.player.health, LevelManager.player.health)) + xa, y + 10 + ya, 8, 8, 0xffffffff);
		
		// Stamina
		screen.renderBox((x + (game.screen.width/3) - 2), y + 8 + 13, 84, 7, 0xff1F1F1F);
		for(int i = 0; i < LevelManager.player.stamina; i++) {
			if(LevelManager.player.health <= 3) {if((game.tickTime / 15) % 2 == 0) continue;}
			screen.renderBox((x + game.screen.width/3) + (i*8) + xs, (y + 10) + ys + 13, 8, 3, 0xffFFB200);
		}
	}
	
	public void pixelIterations() {
		for(int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = game.screen.pixels[i];
		}
	}
}