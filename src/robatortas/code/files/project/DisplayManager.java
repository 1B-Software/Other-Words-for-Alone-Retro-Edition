package robatortas.code.files.project;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import robatortas.code.files.core.render.gl.GLWindow;
import robatortas.code.files.project.level.world.Noise;
import robatortas.code.files.project.settings.Globals;

public class DisplayManager {
	
	private GameManager game;
	
	public GLWindow window;
	
	private ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("textures/icon/window_icon.png"));
	
	public DisplayManager(int width, int height, String title, GameManager game) {
		this.game = game;
		
		window = new GLWindow();
		window.create();
	}
	
	public DisplayManager getDisplay() {
		return game.display;
	}

	public void setDisplay(DisplayManager display) {
		game.display = display;
	}
}