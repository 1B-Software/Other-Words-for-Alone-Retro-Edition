package robatortas.code.files.project.menu.main_menu;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.Fonts;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.settings.Globals;

public class MainMenu {

	String[] buttons = {"play", "options", "quit"};
	boolean[] buttonsState = {true, false, false};
	GameManager game;

	Fonts font = new Fonts();
	public InputManager input;
	
	// How inside the play button I am
	private boolean[] playLayer = {};

	public MainMenu(GameManager game) {
		this.game = game;
		input = new InputManager();
	}
	
	int dir = 0;
	boolean fadeOutQuit = false;
	public void update() {
		input.update();
		
		if(input.toggle(input.up, false)) {
			SoundEngine.select.play();
			dir--;
			for(int i = 0; i < buttonsState.length; i++) {
				buttonsState[i] = false;
			}
		}
		if(input.toggle(input.down, false)) {
			SoundEngine.select.play();
			dir++;
			
			for(int i = 0; i < buttonsState.length; i++) {
				buttonsState[i] = false;
			}
		}
		
		if(input.toggle(input.enter, false)) {
			if(buttonsState[0]) {
				game.startFadeToGame();
			}
			
			System.out.println(fadeOutQuit);
			if(buttonsState[2]) {
				fadeOutQuit = true;
			}
		}
		
		if(fadeOutQuit) {
//			game.removeKeyListener(input);
			GameManager.fadeAlpha += GameManager.FADE_SPEED;
			if(GameManager.fadeAlpha > 255) {
				GameManager.fadeAlpha = 255;
				Console.log("Quitting Other Words for Alone");
				System.exit(0);
			}
		}
		
		
		
		if(dir > buttons.length-1) dir = 0;
		if(dir < 0) dir = buttons.length-1;
		buttonsState[dir] = true;
	}
	
	public void render(RenderManager screen, GameManager game) {
		screen.renderBox(0, 0, Globals.WIDTH, Globals.HEIGHT, 0xffffffff, 0xff, true);
		font.setSize(2);
		font.color = 0xFF000000;
		String firstRow = "Other Words";
		String secondRow = "for Alone"; // Saints Row hhehehemmm
		
		font.draw(firstRow, Globals.CENTER_X - font.getTextWidth(firstRow) / 2, 10, false, screen);
		font.draw(secondRow, Globals.CENTER_X - font.getTextWidth(secondRow) / 2, (int)font.getSize()*18, false, screen);
		
		font.setSize(1);
		if(buttonsState[0]) font.setColor(0xff999999);
		else font.setColor(0xff000000);
		font.draw(buttons[0], Globals.CENTER_X - font.getTextWidth(buttons[0]) / 2, 100, false, screen);
		if(buttonsState[1]) font.setColor(0xff999999);
		else font.setColor(0xff000000);
		font.draw(buttons[1], Globals.CENTER_X - font.getTextWidth(buttons[1]) / 2, 120, false, screen);
		if(buttonsState[2]) font.setColor(0xff999999);
		else font.setColor(0xff000000);
		font.draw(buttons[2], Globals.CENTER_X - font.getTextWidth(buttons[2]) / 2, 140, false, screen);
		
		
		font.setColor(0xff000000);
		String devmode = "";
		if(GameManager.DEV_MODE) devmode = " | DEVELOPER MODE";
		font.setSize(0.6f);
		font.draw(Globals.TITLE + " PRE-ALPHA" + devmode, 0, Globals.HEIGHT-10, false, screen);
	}
}