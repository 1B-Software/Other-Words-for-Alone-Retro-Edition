package robatortas.code.files.core.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.PrintWriter;
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

public class CrashHandler implements Runnable {
	
	public Thread thread;
	public boolean running = false;
	
	private ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("textures/icon/warning.png"));
	
	private JFrame frame;
	
	/**<NEWLINE>
	 * <b>handle on the CrashHandler class</b>
	 * <br><br>
	 * Handles all exceptions.
	 * Only the player can close the game.
	 * And a gui has been applied for the client to choose between closing the game and to keep playing.
	 * 
	 * Unexpected game closings only happen if they are fatal to the system resources,
	 * for example memory overload or too many entities loaded.
	 * 
	 * @param throwable Checks for thowings that the JVM might do when encountering an issue.
	 * @param info Specify the information about the possible crash report.
	 * @param errorType The type of error.
	 * 
	 * @see ErrorType
	 */
	public void handle(Throwable throwable, String info, ErrorType errorType) {
		System.out.println("\n");
		Console.logError(info + "\n\nLOGS:\n");
		
		throwable.printStackTrace();
		
		int width = 500;
		int height = 400;
		
		frame = new JFrame();
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("ERROR");
		JLabel type = new JLabel(errorType.type);
		JTextArea text = new JTextArea(2, 2);
		JButton close = new JButton("Close Game");
		JButton keep = new JButton("Keep Playing");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(86, 86, 86));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 10, 0);
		
		panel.setVisible(true);
		
		label.setForeground(new Color(150, 64, 64));
		label.setFont(new Font("Arial", 1, 30));
		type.setFont(new Font("Arial", 3, 15));
		
		text.setEditable(false);
		text.setForeground(new Color(30, 30, 30));
		text.setFont(new Font("Arial", 1, 10));
		text.setBackground(new Color(78, 78, 78));
		text.setBorder(null);
		
		StringWriter exception = new StringWriter();
		throwable.printStackTrace(new PrintWriter(exception));
		String stackTrace = exception.toString();
		text.setText("LOG:\n\n"
				+ "Reason: " + info + "\n\n" + "Exit_Code: " + errorType.exitCode + "\n\n" + stackTrace);
		scroll.setPreferredSize(new Dimension(400, 200));
		scroll.setBorder(null);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 15, 0));
		buttonPanel.setOpaque(false);
		
		keep.setSize(10, 10);
		keep.setBorderPainted(false);
		keep.setFocusable(false);
		keep.setBackground(new Color(125, 64, 64));
		keep.setPressedIcon(null);
		keep.addActionListener(e -> {
			frame.dispose();
			this.stop();
		});
		
		close.setSize(10, 10);
		close.setBorderPainted(false);
		close.setFocusable(false);
		close.setBackground(new Color(125, 64, 64));
		close.addActionListener(e -> {
			System.exit(0);
		});
		
		buttonPanel.setVisible(true);
		
		panel.add(label, gbc);
		panel.add(type, gbc);
		panel.add(scroll, gbc);
		buttonPanel.add(keep);
		buttonPanel.add(close);
		panel.add(buttonPanel, gbc);
		
		frame.setIconImage(windowIcon.getImage());
		
		frame.setTitle("ERROR");
		frame.setSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		this.start();
		frame.setVisible(true);
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	/**<NEWLINE>
	 * <b>ErrorType enum on the CrashHandler class</b>
	 * <br><br>
	 * Used to give a small description about the error that occurred
	 * <br><br>
	 * UNHANDLED: Not handled by system.
	 * <br>
	 * UNEXPECTED: The error wasn't expected.
	 * <br>
	 * IRREDEEMABLE: Not able to be handled by system.
	 * <br>
	 * SERIOUS: Potential for crash, probably should exit the program.
	 * <br>
	 * HANDLED: The system handled the exception.
	 * <br><br>
	 * Exit Code Meanings: https://www.techiedelight.com/exit-codes-java-system-exit-method/ 
	 */
	public static enum ErrorType {
		
		UNHANDLED("Unhandled Error: Not handled.", -3),
		UNEXPECTED("Unexpected Error: This shouldn't happen.", -2),
		IRREDEEMABLE("Irredeemable Error: Not able to be handled", -1),
		SERIOUS("Serious Error: Possible unplayable content", 1),
		HANDLED("Handled Error: Solved by system", 2);
		
		public int exitCode;
		public String type;
		
		ErrorType(String type, int exitCode) {
			this.type = type;
			this.exitCode = exitCode;
		}
	}
	
	// Crash Handler THREAD MANAGER
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "CrashHandler");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int tickTime = 0;
	public void run() {
		while(running) {
			tickTime++;
			if(tickTime < 10) {
			frame.requestFocus();
			}
		}
	}
}