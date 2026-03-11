package robatortas.code.files.project;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import robatortas.code.files.core.input.MouseManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.RenderMethod;
import robatortas.code.files.core.utils.LoopingUtils;
import robatortas.code.files.core.utils.ResourceUtils;
import robatortas.code.files.core.utils.ThreadUtils;
import robatortas.code.files.project.menu.main_menu.MainMenu;
import robatortas.code.files.project.settings.Globals;

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
	public boolean DEBUG = false;
	
	public DisplayManager display;
	public JFrame frame = new JFrame();

	public static enum PLAYSTATE {
		MAIN_MENU(true), 
		IN_GAME(false), 
		PAUSE_MENU(false);
		
		public boolean state;
		
		PLAYSTATE(boolean state) {
			this.state = state;
		}
	}
	
	public RenderManager screen;
	private RenderMethod renderMethod = new RenderMethod();
	
	protected BufferedImage image = new BufferedImage(Globals.WIDTH * Globals.RENDER_SCALE, Globals.HEIGHT * Globals.RENDER_SCALE, BufferedImage.TYPE_INT_ARGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	public LevelManager level;

	public MouseManager mouse;

	// Fade transition
	public static int  fadeAlpha  = 0;
	public static boolean fadingOut = false; // menu → black
	public static boolean fadingIn  = false; // black → game
	public static final int FADE_SPEED = 6;

	public void startFadeToGame() {
		fadingOut = true;
		fadeAlpha = 0;
	}
	
	public float xScroll, yScroll;
	
	
	public ResourceUtils resources = new ResourceUtils();
	
	public MainMenu mainMenu;
	
	/**<NEWLINE>
	 * <b>GameManager function in GameManager class</b>
	 * <br><br>
	 * The main function of OWFA's game logic.
	 * <br>
	 * Initializes all the necessary code for the correct functioning of the game
	 * 
	 */
	public GameManager() {
		screen = new RenderManager(Globals.WIDTH, Globals.HEIGHT);
		if(PLAYSTATE.MAIN_MENU.state) this.mainMenu = new MainMenu(this);
		level = LevelManager.level;
//		level = LevelManager.level;
		display = new DisplayManager(Globals.WIDTH, Globals.HEIGHT, Globals.TITLE, this);
		
		mouse = new MouseManager();
		
		if(PLAYSTATE.MAIN_MENU.state) addKeyListener(mainMenu.input);
		
//		if(PLAYSTATE.IN_GAME.state) {
//			addKeyListener(level.input);
//			addMouseListener(mouse);
//		}
		
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
	
	public LoopingUtils looping = new LoopingUtils();
	
	/**<NEWLINE>
	 * <b>run function in GameManger class</b>
	 * <br><br>
	 * Uses the LoopingUtils class {@link LoopingUtils} to handle the main game loop
	 * 
	 * @see LoopingUtils
	 */
	public void run() {
		while(threadUtils.running) {
			String consolePrint = "ticks: " + LoopingUtils.ticks + "  ||  " + "fps: " + LoopingUtils.frames;
			looping.whileRunning();
			
			if(looping.delta >= 1) {
				this.update();
				LoopingUtils.ticks++;
				looping.delta--;
				// Rendering
				LoopingUtils.frames++;
				render();
			}
			
			looping.timerLoop(consolePrint, this, () -> {
				resources.memory = resources.getMemory();
				resources.cpUsage = resources.getCPUsage();
				resources.threadCount = resources.getThreadCount();
				resources.maxMemory = resources.getMaxMemory();
			});
		}
		stop();
	}
	
	
	// Update
	public int tickTime = 0;
	public void update() {
		tickTime++;
		if(PLAYSTATE.IN_GAME.state) {
			level.update();
			generalPurposeKeys();
		}
		if(PLAYSTATE.MAIN_MENU.state) {
			mainMenu.update();
		}

		if(fadingOut) {
			fadeAlpha += FADE_SPEED;
			if(fadeAlpha >= 255) {
				fadeAlpha = 255;
				fadingOut = false;
				fadingIn  = true;
				if(LevelManager.pendingDoor != null) {
					// Door transition — swap level at the black frame
					LevelManager.level.unload();
					LevelManager.level.load(LevelManager.pendingDoor.getTargetLevelPath());
					LevelManager.player.x = LevelManager.pendingDoor.getSpawnX() << 4;
					LevelManager.player.y = LevelManager.pendingDoor.getSpawnY() << 4;
					LevelManager.pendingDoor = null;
				} else {
					// Menu → game transition
					removeKeyListener(mainMenu.input);
					addKeyListener(level.input);
					PLAYSTATE.IN_GAME.state   = true;
					PLAYSTATE.MAIN_MENU.state = false;
				}
			}
		} else if(fadingIn) {
			fadeAlpha -= FADE_SPEED;
			if(fadeAlpha <= 0) {
				fadeAlpha = 0;
				fadingIn  = false;
			}
		}
		
//		System.out.println(MathUtils.power(10.1, 3));
		
//		System.out.println(MathUtils.squareRoot(124));
//		MathUtils.squareRoot(124);
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
		g.setColor(new Color(0xFF000000)); // 0x1F1F1F
		g.fillRect(0, 0, getWidth(), getHeight());
		
		float targetAspect = (float) Globals.WIDTH / Globals.HEIGHT; // 250/220
		float windowAspect = (float) getWidth() / getHeight();
		
		int w = getWindowSize().width;
	    int h = getWindowSize().height;
		
	    if (windowAspect > targetAspect) {
	        // window is wider than the game → pillarbox (black bars on sides)
	        h = getHeight();
	        w = (int) (h * targetAspect);
	    } else {
	        // window is taller than the game → letterbox (black bars top/bottom)
	        w = getWidth();
	        h = (int) (w / targetAspect);
	    }
	    
		int xo = (getWidth() - w) / 2;
		int yo = (getHeight() - h) / 2;

		if(PLAYSTATE.MAIN_MENU.state) {
			this.mainMenu.render(screen, this);
			System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
		}
		
//		g.setComposite(opacity(0.01f));
		g.drawImage(image, xo, yo, w, h, null);
		
		if(PLAYSTATE.IN_GAME.state) {
			renderMethod.render(this);
			xScroll = RenderMethod.xScroll;
			yScroll = RenderMethod.yScroll;
		}

		if(fadeAlpha > 0) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha / 255f));
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}

		g.dispose();
		bs.show();
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
		return new java.awt.Dimension((int) (Globals.WIDTH * Globals.SCALE), (int) (Globals.HEIGHT * Globals.SCALE));
	}
}