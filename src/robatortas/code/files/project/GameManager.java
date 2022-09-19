package robatortas.code.files.project;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.naming.InsufficientResourcesException;
import javax.swing.JFrame;

import robatortas.code.files.core.input.MouseManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.Fonts;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.RenderMethod;
import robatortas.code.files.core.utils.LoopingUtils;
import robatortas.code.files.core.utils.ResourceUtils;
import robatortas.code.files.core.utils.ThreadUtils;
import robatortas.code.files.project.settings.Constants;

/**<NEWLINE>
 * <b>GameManger class</b>
 * <br><br>
 * The core class, takes care of all the main OWFA handling.
 * <br>
 * All the code gets summed up here!
 */
public class GameManager extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static boolean DEV_MODE = true;
	private boolean DEBUG = false;

	
	public DisplayManager display;
	public JFrame frame = new JFrame();

	
	public RenderManager screen;
	private RenderMethod renderMethod = new RenderMethod();
	
	protected BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	public LevelManager level;
	
	public MouseManager mouse;
	
	public int xScroll, yScroll;
	
	
	public ResourceUtils resources = new ResourceUtils();
	public int memory = 0;
	
	
	/**<NEWLINE>
	 * <b>GameManager function in GameManager class</b>
	 * <br><br>
	 * The main function of OWFA's game logic.
	 * <br>
	 * Initializes all the necessary code for the correct functioning of the game
	 * 
	 */
	public GameManager() {
		screen = new RenderManager(Constants.WIDTH, Constants.HEIGHT);
		level = LevelManager.level;
		display =  new DisplayManager(Constants.WIDTH, Constants.HEIGHT, Constants.TITLE, this);
		
		mouse = new MouseManager();
		
		addKeyListener(level.input);
		addMouseListener(mouse);
		
		this.requestFocus();
	}
	
	// Threading and game loop
	protected ThreadUtils threadUtils = new ThreadUtils();
	
	/**<NEWLINE>
	 * <b>start function on the GameManager class</b>
	 * <br><br>
	 * Uses the ThreadUtils class {@link robatortas.code.files.core.utils.ThreadUtils}
	 * <br>
	 * to start and manage the thread startup.
	 * 
	 * @see ThreadUtils
	 */
	public synchronized void start() {
		threadUtils.thread = new Thread(this, "Game");
		threadUtils.start();
	}
	
	/**<NEWLINE>
	 * <b>stop function on the GameManager class</b>
	 * <br><br>
	 * Uses the ThreadUtils class {@link robatortas.code.files.core.utils.ThreadUtils}
	 * <br>
	 * to stop and join the thread killing.
	 * 
	 * @see ThreadUtils
	 */
	public synchronized void stop() {
		threadUtils.stop();
	}
	
	private LoopingUtils looping = new LoopingUtils();
	
	/**<NEWLINE>
	 * <b>run function in GameManger class</b>
	 * <br><br>
	 * Uses the LoopingUtils class {@link robatortas.code.files.core.utils.LoopingUtils} to handle the main game loop
	 * 
	 * @see LoopingUtils
	 */
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
	public int tickTime = 0;
	public void update() {
		tickTime++;
		level.update();
		generalPurposeKeys();
	}
	
	/**<NEWLINE>
	 * <b>render function in GameManager class</b>
	 * <br><br>
	 * The main render function that takes all the rendering done by the cpu
	 * <br>
	 * and passes it to the display buffer.
	 * <br><br>
	 * The core of the rendering, where all the rendering stuff should go to!
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(new Color(0x1F1F1F));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int w = getWindowSize().width;
	    int h = getWindowSize().height;
		
		int xo = (getWidth() - w) / 2;
		int yo = (getHeight() - h) / 2;

//		g.setComposite(opacity(0.01f));
		g.drawImage(image, xo, yo, w, h, null);
		
		renderMethod.render(this);
		xScroll = RenderMethod.xScroll;
		yScroll = RenderMethod.yScroll;
		
		Fonts font = new Fonts();

		font.setSize(8*2);
		if(DEBUG) {
			font.draw("E:" + level.entities.size(),0, 5, false, screen);
			font.draw("X:" + (LevelManager.player.x >> 4) + " Y:" + (LevelManager.player.y >> 4), 2, 5*3, false, screen);
			font.draw("Dev_Mode:" + DEV_MODE, 2, 5*5, false, screen);
		}
		
		font.setColor(0x6f0000ff);
		font.draw("MEMORY: " + this.memory, 2, 5*5, false, screen);
		
		// TODO: LATER!!!!
//		screen.debug(40, 40, 16, 16, 0xffff00ff, 1);
		
//		screen.renderBox(font.getX() + xScroll, font.getY()-19 + yScroll, 16, 16, 0xffff0000);
		
		g.dispose();
		bs.show();
	}
	
	private Composite opacity(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}
	
	public void generalPurposeKeys() {
		DEBUG = level.input.toggle(level.input.f3, DEBUG);
	}
	
	/**<NEWLINE>
	 * <b>getWindowSize function on the GameManager class</b>
	 * <br><br>
	 * Gets the game window size
	 * 
	 * @see java.awt.Dimension
	 */
	static java.awt.Dimension getWindowSize() {
		return new java.awt.Dimension((int) (Constants.WIDTH * Constants.SCALE), (int) (Constants.HEIGHT * Constants.SCALE));
	}
}