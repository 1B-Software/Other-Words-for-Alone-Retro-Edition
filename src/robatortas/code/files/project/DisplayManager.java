package robatortas.code.files.project;

import java.awt.Dimension;

import javax.swing.JFrame;

import robatortas.code.files.project.settings.Constants;

public class DisplayManager {
	
	public JFrame frame = new JFrame();
	
	private GameManager game;
	
	public DisplayManager(int width, int height, String title, GameManager game) {
		this.game = game;
		
		Dimension size = new Dimension(width*Constants.SCALE, height*Constants.SCALE);
		
		frame.setTitle(title);
		frame.pack();
		frame.add(game);
		game.start();
		frame.setSize(size);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public DisplayManager getDisplay() {
		return game.display;
	}

	public void setDisplay(DisplayManager display) {
		game.display = display;
	}
}
