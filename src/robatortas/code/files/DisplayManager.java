package robatortas.code.files;

import java.awt.Dimension;

import javax.swing.JFrame;

public class DisplayManager {
	
	public JFrame frame = new JFrame();
	
	public DisplayManager(int width, int height, String title, GameManager game) {
		
		Dimension size = new Dimension(width*GameManager.SCALE, height*GameManager.SCALE);
		
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
}
