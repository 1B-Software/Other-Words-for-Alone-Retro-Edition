package robatortas.code.files.project;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.util.List;

import robatortas.code.files.core.input.MouseManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.RenderMethod;
import robatortas.code.files.core.render.gl.GLWindow;
import robatortas.code.files.core.render.gl.LightRenderer;
import robatortas.code.files.core.render.gl.RainRenderer;
import robatortas.code.files.core.render.gl.ShaderProgram;
import robatortas.code.files.core.render.gl.SpriteBatch;
import robatortas.code.files.core.utils.LoopingUtils;
import robatortas.code.files.core.utils.ResourceUtils;
import robatortas.code.files.core.utils.ThreadUtils;
import robatortas.code.files.project.level.Level;
import robatortas.code.files.project.menu.main_menu.MainMenu;
import robatortas.code.files.project.settings.Globals;

/**
 * <b>GameManger class</b>
 * <br><br>
 * The core class, takes care of all the main OWFA handling.
 * <br>
 * All the code gets summed up here!
 */
public class GameManager implements Runnable {

	private static final long serialVersionUID = 1L;

	public static boolean DEV_MODE = true;
	public boolean DEBUG = false;

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

	public static ShaderProgram spriteShader;
	private ShaderProgram lightShader;
	private SpriteBatch spriteBatch;
	private LightRenderer lightRenderer;
	public static RainRenderer rainRenderer;
	public static ShaderProgram rainShader;

	public LevelManager level;

	public MouseManager mouse;
	public DisplayManager display;

	// Fade transition
	public static int fadeAlpha = 0;
	public static boolean fadingOut = false;
	public static boolean fadingIn = false;
	public static final int FADE_SPEED = 6;

	public void startFadeToGame() {
		fadingOut = true;
		fadeAlpha = 0;
	}

	public float xScroll, yScroll;

	public static GLWindow window;

	public ResourceUtils resources = new ResourceUtils();

	public MainMenu mainMenu;
	
	public static float renderDt;

	public GameManager() {
		window = new GLWindow();
		screen = new RenderManager(Globals.WIDTH, Globals.HEIGHT);
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

	public LoopingUtils looping = new LoopingUtils();

	/**
	 * Called after window.create() to initialize all OpenGL resources.
	 * This is where shaders, batches, textures get set up.
	 */
	private void initGL() {
		// Load shaders from classpath resources
		rainShader = new ShaderProgram("/shaders/rain.vert", "/shaders/rain.frag");
		spriteShader = new ShaderProgram("/shaders/sprite.vert", "/shaders/sprite.frag");
		lightShader = new ShaderProgram("/shaders/light.vert", "/shaders/light.frag");

		// Create sprite batch and set orthographic projection to game coordinates
		spriteBatch = new SpriteBatch(spriteShader);
		spriteBatch.setProjection(Globals.WIDTH, Globals.HEIGHT);

		// Initialize RenderManager's GL resources (white texture)
		screen.initGL();
		screen.setBatch(spriteBatch);

		// Create lightmap renderer (FBO at game resolution)
		lightRenderer = new LightRenderer(Globals.WIDTH, Globals.HEIGHT,
				lightShader, spriteBatch, screen.getWhiteTextureId());

		// Create rain renderer (FBO at game resolution, GL_NEAREST for pixel-perfect upscale)
		rainRenderer = new RainRenderer(Globals.WIDTH, Globals.HEIGHT);

		// Set up game state now that GL is ready
		level = LevelManager.level;
		if (PLAYSTATE.MAIN_MENU.state) this.mainMenu = new MainMenu(this);
	}

	public void run() {
		window.create();
		initGL();
		long lastRenderTime = System.nanoTime();

		while (threadUtils.running && !window.shouldClose()) {
			String consolePrint = "ticks: " + LoopingUtils.ticks + "  ||  " + "fps: " + LoopingUtils.frames;
			looping.whileRunning();

			if (looping.delta >= 1) {
				this.update();
				LoopingUtils.ticks++;
				looping.delta--;
			}
			// Rendering
			long now = System.nanoTime();
			renderDt = (now - lastRenderTime) / 1_000_000_000f;
			lastRenderTime = now;
			render();
			LoopingUtils.frames++;
			window.pollEvents();
			looping.timerLoop(consolePrint, this, () -> {
				resources.memory = resources.getMemory();
				resources.cpUsage = resources.getCPUsage();
				resources.threadCount = resources.getThreadCount();
				resources.maxMemory = resources.getMaxMemory();
			});
		}

		// Cleanup
		lightRenderer.dispose();
		spriteBatch.dispose();
		spriteShader.dispose();
		lightShader.dispose();
		window.destroy();
		stop();
	}

	// Update
	public int tickTime = 0;

	public void update() {
		tickTime++;
		if (PLAYSTATE.IN_GAME.state) {
			level.update();
			generalPurposeKeys();
		}
		if (PLAYSTATE.MAIN_MENU.state) {
			if (mainMenu != null) mainMenu.update();
		}

		if (fadingOut) {
			fadeAlpha += FADE_SPEED;
			if (fadeAlpha >= 255) {
				fadeAlpha = 255;
				fadingOut = false;
				fadingIn = true;
				if (LevelManager.pendingDoor != null) {
					LevelManager.level.unload();
					LevelManager.level.load(LevelManager.pendingDoor.getTargetLevelPath());
					LevelManager.player.x = LevelManager.pendingDoor.getSpawnX() << 4;
					LevelManager.player.y = LevelManager.pendingDoor.getSpawnY() << 4;
					LevelManager.pendingDoor = null;
				} else {
					PLAYSTATE.IN_GAME.state = true;
					PLAYSTATE.MAIN_MENU.state = false;
				}
			}
		} else if (fadingIn) {
			fadeAlpha -= FADE_SPEED;
			if (fadeAlpha <= 0) {
				fadeAlpha = 0;
				fadingIn = false;
			}
		}
	}

	/**
	 * Main render function. Renders scene, applies lightmap, then overlays GUI.
	 *
	 * Pipeline:
	 *   1. Render scene (level + entities) — lights are queued during this pass
	 *   2. Render queued lights into lightmap FBO
	 *   3. Multiply lightmap onto the scene (GL_DST_COLOR blend)
	 *   4. Render GUI + fade overlay on top (unlit)
	 */
	public void render() {
		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);

		// === PASS 1: Scene ===
		spriteBatch.begin();

		if (PLAYSTATE.IN_GAME.state) {
			renderMethod.render(this);
			xScroll = RenderMethod.xScroll;
			yScroll = RenderMethod.yScroll;
		}

		if (PLAYSTATE.MAIN_MENU.state && mainMenu != null) {
			mainMenu.render(screen, this);
		}

		spriteBatch.end();

		// PASS 2: Lighting (only when in-game and lights were queued)
		List<float[]> lights = screen.getPendingLights();
		if (PLAYSTATE.IN_GAME.state && !lights.isEmpty()) {
			// Render lights into lightmap FBO
			lightRenderer.begin(Level.environmentLight);
			for (float[] light : lights) {
				lightRenderer.addLight(
					light[0], light[1], light[2], light[3],
					light[4], light[5], light[6], light[7]
				);
			}
			lightRenderer.end();
			screen.clearPendingLights();

			// Restore window viewport after FBO rendering
			window.updateViewport();

			// Multiply lightmap onto the scene
			glBlendFunc(GL_DST_COLOR, GL_ZERO);
			spriteBatch.begin();
			// V-flipped because FBO textures are Y-up but our projection is Y-down
			spriteBatch.draw(0, 0, Globals.WIDTH, Globals.HEIGHT,
					0, 1, 1, 0, 1, 1, 1, 1, lightRenderer.getLightTexture());
			spriteBatch.end();
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		} else if (PLAYSTATE.IN_GAME.state && Level.environmentLight < 1.0f) {
			// No dynamic lights but environment is dark — still apply darkening
			lightRenderer.begin(Level.environmentLight);
			lightRenderer.end();
			screen.clearPendingLights();

			window.updateViewport();

			glBlendFunc(GL_DST_COLOR, GL_ZERO);
			spriteBatch.begin();
			spriteBatch.draw(0, 0, Globals.WIDTH, Globals.HEIGHT,
					0, 1, 1, 0, 1, 1, 1, 1, lightRenderer.getLightTexture());
			spriteBatch.end();
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}

		// === PASS 3: GUI overlay (unlit) + fade ===
		spriteBatch.begin();

		if (PLAYSTATE.IN_GAME.state) {
			renderMethod.renderOverlay();
		}

		// Fade overlay
		if (fadeAlpha > 0) {
			float a = fadeAlpha / 255f;
			spriteBatch.drawColor(0, 0, Globals.WIDTH, Globals.HEIGHT,
					0, 0, 0, a, screen.getWhiteTextureId());
		}

		spriteBatch.end();

		window.swapBuffers();
	}

	public void generalPurposeKeys() {
		DEBUG = level.input.toggle(level.input.f3, DEBUG);
	}
}
