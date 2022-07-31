package robatortas.code.files.core.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CrashHandler {
	//Throwable throwable, 
	public void handle(String info) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		int width = 400;
		int height = 200;
		
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel(info);
		JTextArea text = new JTextArea("dgrbvedgbvedrgvedgrfeg");
		JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JButton close = new JButton("Close");
		
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(86, 86, 86));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.insets = new Insets(0, 0, 20, 0);
		
		panel.setVisible(true);
		
		panel.add(label, gbc);
		
		text.setEditable(false);
		text.setBackground(new Color(86, 86, 86));
		text.setBorder(null);
		scroll.setBorder(null);
		panel.add(scroll, gbc);
		
		panel.add(close, gbc);
		
		close.setSize(10, 10);
		close.addActionListener(e -> {
			System.exit(0);
		});
		
		frame.requestFocus();
		
		frame.setTitle("ERROR");
		frame.setSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	// Exit Code Meanings: https://www.techiedelight.com/exit-codes-java-system-exit-method/
	public static enum ErrorType {
		/*
		 * UNHANDLED: Not handled by system.
		 * UNEXPECTED: The error wasn't expected.
		 * IRREDEEMABLE: Not able to be handled by system.
		 * SERIOUS: Potential for crash, probably should exit the program.
		 * HANDLED: The system handled the exception.
		 */
		
		UNHANDLED("Unhandled Error: Not handled.", -1),
		UNEXPECTED("Unexpected Error: This shouldn't happen.", -2),
		IRREDEEMABLE("Irredeemable Error: Not able to be handled", -3),
		SERIOUS("Serious Error: Potential for crash", 1),
		HANDLED("Handled Error: Solved by system", 2);
		
		public int exitCode;
		public String type;
		
		ErrorType(String type, int exitCode) {
			this.type = type;
			this.exitCode = exitCode;
		}
	}
}