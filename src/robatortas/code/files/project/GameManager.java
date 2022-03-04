package robatortas.code.files.project;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.Renderer;
import robatortas.code.files.core.utils.LoopingUtils;
import robatortas.code.files.core.utils.ThreadUtils;
import robatortas.code.files.project.settings.Constants;

// CLASS IS ALMOST DONE (Meaning I will almost never touch it again)

public class GameManager extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// DECLARATIONS
	
	// Screen declarations
	public DisplayManager display;

	public RenderManager renderManager;
	private Renderer render = new Renderer();
	
	protected BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	// Input Declarations
	private InputManager input = new InputManager();
	
	
	private Constants constants = new Constants();
	
	// DECLARATIONS END
	
	
	// Main
	public GameManager() {
		renderManager = new RenderManager(Constants.WIDTH, Constants.HEIGHT);
		display =  new DisplayManager(Constants.WIDTH, Constants.HEIGHT, Constants.TITLE, this);

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
			looping.whileRunning();
			looping.deltaLoop(this);
			looping.timerLoop();
			
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
		
		render.render(this);
		
		g.dispose();
		bs.show();
	}
	
	
	// Main Method
	public static void main(String[] args) {
		new GameManager();
	}
}