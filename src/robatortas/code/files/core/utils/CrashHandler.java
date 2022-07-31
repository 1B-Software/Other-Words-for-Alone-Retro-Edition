package robatortas.code.files.core.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.StringWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import robatortas.code.files.core.console.Console;

public class CrashHandler {
	
	private ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("textures/icon/warning.png"));
	
	public void handle(Throwable throwable, String info, ErrorType errorType) {
		System.out.println("\n");
		Console.writeErr(info);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		int width = 400;
		int height = 300;
		
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("ERROR");
		JLabel type = new JLabel(errorType.type);
		JTextArea text = new JTextArea(2, 2);
		JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JButton close = new JButton("Close");
		JButton keep = new JButton("Keep Playing");
		
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(86, 86, 86));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 10, 0);
		
		panel.setVisible(true);
		
		label.setForeground(new Color(150, 64, 64));
		label.setFont(new Font("Arial", 1, 30));
		panel.add(label, gbc);
		type.setFont(new Font("Arial", 3, 13));
		panel.add(type, gbc);
		
		text.setEditable(false);
		text.setBackground(new Color(78, 78, 78));
		text.setBorder(null);
		
		StringWriter exception = new StringWriter();
		
		text.setText("LOG:\n");
		scroll.setPreferredSize(new Dimension(350, 130));
		scroll.setBorder(null);
		panel.add(scroll, gbc);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 15, 0));
		buttonPanel.setOpaque(false);
		
		keep.setSize(10, 10);
		keep.setBorderPainted(false);
		keep.setFocusable(false);
		keep.setBackground(new Color(125, 64, 64));
		keep.setPressedIcon(null);
		buttonPanel.add(keep);
		keep.addActionListener(e -> {
			frame.dispose();
		});
		
		close.setSize(10, 10);
		close.setBorderPainted(false);
		close.setFocusable(false);
		close.setBackground(new Color(125, 64, 64));
		buttonPanel.add(close);
		close.addActionListener(e -> {
			System.exit(0);
		});
		
		buttonPanel.setVisible(true);
		panel.add(buttonPanel, gbc);
		
		
		frame.setIconImage(windowIcon.getImage());
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