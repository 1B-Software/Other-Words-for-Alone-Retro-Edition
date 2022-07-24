package robatortas.code.files.project;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import robatortas.code.files.project.settings.Constants;

public class DisplayManager {
	
	private GameManager game;
	
	private ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("textures/icon/window_icon.png"));
	
	public DisplayManager(int width, int height, String title, GameManager game) {
		this.game = game;
		
//		NoiseMap.main(null);
		
		Dimension size = new Dimension(width*Constants.SCALE, height*Constants.SCALE);
		
		game.frame.setIconImage(windowIcon.getImage());
		game.frame.setTitle(title);
		game.frame.pack();
		game.frame.add(game);
		game.start();
		game.frame.setSize(size);
		game.frame.setResizable(true);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public DisplayManager getDisplay() {
		return game.display;
	}

	public void setDisplay(DisplayManager display) {
		game.display = display;
	}
}