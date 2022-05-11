package robatortas.code.files.project;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.RenderMethod;
import robatortas.code.files.core.utils.LoopingUtils;
import robatortas.code.files.core.utils.ThreadUtils;
import robatortas.code.files.project.settings.Constants;

// CLASS IS ALMOST DONE (Meaning I will almost never touch it again)

public class GameManager extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final boolean DEV_MODE = true;
	
	// DECLARATIONS
	
	// Screen declarations
	public DisplayManager display;
	public JFrame frame = new JFrame();

	public RenderManager screen;
	private RenderMethod renderMethod = new RenderMethod();
	
	protected BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	private Constants constants = new Constants();
	
	
	// General Declarations
	public LevelManager level;
	
	// DECLARATIONS END
	
	
	//TODO: CLEAN CODE TOMORROW!!!!!!
	
	// Main
	@SuppressWarnings("unused")
	public GameManager() {
		screen = new RenderManager(Constants.WIDTH, Constants.HEIGHT);
		level = LevelManager.level;
		display =  new DisplayManager(Constants.WIDTH, Constants.HEIGHT, Constants.TITLE, this);
		
		if(Constants.levelPath != "/textures/level/level/level.png") System.err.println("Level file location denied.");
		else {}
		
		addKeyListener(level.input);
	}
	
	
	// Threading and game loop
	protected ThreadUtils threadUtils = new ThreadUtils();
	
	public synchronized void start() {
		threadUtils.thread = new Thread(this, "Game");
		threadUtils.start();
	}
	
	public synchronized void stop() {
		threadUtils.stop();
	}
	
	private LoopingUtils looping = new LoopingUtils();
	
	public void run() {
		while(threadUtils.running) {
			String consolePrint = "ticks: " + looping.ticks + "  ||  " + "fps: " + looping.frames;
			looping.whileRunning();
			looping.deltaLoop(this);
			looping.timerLoop(consolePrint, this);
			
			// Rendering
			looping.frames++;
			render();
		}
		stop();
	}
	
	
	// Update
	public int x, y;
	public void update() {
		constants.ticks++;
		
		level.update();
	}
	
	
	// Render
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(new Color(0x1F1F1F));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int ww = Constants.WIDTH * 3;
		int hh = Constants.HEIGHT * 3;
		int xo = (getWidth() - ww) / 2;
		int yo = (getHeight() - hh) / 2;

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		g.drawImage(image, xo, yo, ww, hh, null);
		
		renderMethod.render(this);
		
		g.setColor(Color.magenta);
		g.setFont(new Font("Verdana", 1, 1).deriveFont(30f));
		g.drawString("X: " + (LevelManager.player.x >> 4) + " Y: " + (LevelManager.player.y >> 4), 20, 30);
		
		g.dispose();
		bs.show();
	}
	
	static java.awt.Dimension getWindowSize() {
		return new java.awt.Dimension((int) (Constants.WIDTH * Constants.SCALE), (int) (Constants.HEIGHT * Constants.SCALE));
	}
	
	// Main Method
	public static void main(String[] args) {
		new GameManager();
	}
}