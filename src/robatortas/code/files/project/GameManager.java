package robatortas.code.files.project;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.RenderMethod;
import robatortas.code.files.core.utils.LoopingUtils;
import robatortas.code.files.core.utils.ThreadUtils;
import robatortas.code.files.project.settings.Constants;

// CLASS IS ALMOST DONE (Meaning I will almost never touch it again)

public class GameManager extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// DECLARATIONS
	
	// Screen declarations
	public DisplayManager display;
	public static JFrame frame = new JFrame();

	public RenderManager screen;
	private RenderMethod renderMethod = new RenderMethod();
	
	protected BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	// Input Declarations
	private InputManager input = new InputManager();
	
	
	private Constants constants = new Constants();
	
	
	// General Declarations
	public LevelManager level;
	
	// DECLARATIONS END
	
	
	// Main
	@SuppressWarnings("unused")
	public GameManager() {
		screen = new RenderManager(Constants.WIDTH, Constants.HEIGHT);
		level = LevelManager.level;
		display =  new DisplayManager(Constants.WIDTH, Constants.HEIGHT, Constants.TITLE, this);
		
		
		if(Constants.levelPath != "/textures/level/level/level.png") System.err.println("Level file location denied.");
		else {}
		
		addKeyListener(input);
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
			looping.timerLoop(consolePrint);
			
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
		
		input.update();
		if(input.up) y--;
		if(input.down) y++;
		if(input.left) x--;
		if(input.right) x++;
	}
	
	
	// Render
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		renderMethod.render(this);
		
		g.dispose();
		bs.show();
	}
	
	
	// Main Method
	public static void main(String[] args) {
		new GameManager();
	}
}