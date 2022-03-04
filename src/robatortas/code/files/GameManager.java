package robatortas.code.files;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.utils.ThreadManager;

public class GameManager extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// Screen declarations
	private DisplayManager display;
	public static final String TITLE = "Other Words for Alone CleanUp";
	public static final int WIDTH = 250;
	public static final int HEIGHT = 150;
	public static final int SCALE = 3;

	private RenderManager renderer;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	// Input Declarations
	private InputManager input = new InputManager();
	
	// Main
	public GameManager() {
		renderer = new RenderManager(WIDTH, HEIGHT);
		setDisplay(new DisplayManager(WIDTH, HEIGHT, TITLE, this));

		addKeyListener(input);
	}
	
	// Thread Managing
	private ThreadManager threadManager = new ThreadManager();
	public synchronized void start() {
		threadManager.thread = new Thread(this, "Game");
		threadManager.start();
	}
	
	public synchronized void stop() {
		threadManager.stop();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000000000.0/60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		
		while(threadManager.running) {
			long now = System.nanoTime();
			long passedTime = now-lastTime;
			delta += passedTime/ns;
			
			lastTime = now;
			
			if(delta >= 1) {
				update();
				ticks++;
				delta--;
			}
			
			frames++;
			render();
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer+=1000;
				System.out.println("ticks: " + ticks + "  ||  " + "fps: " + frames);
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	
	private int ticks = 0;
	
	private int x,y;
	
	public void update() {
		ticks++;
		
		input.update();
		
		if(input.up) {
			y--;
		}
		if(input.down) {
			y++;
		}
		if(input.left) {
			x--;
		}
		if(input.right) {
			x++;
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = renderer.pixels[i];
		}
		
		requestFocus();
		
		renderer.clear();
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
//		renderer.renderPixel(x, y);
		renderer.renderSprite(x, y, SpriteManager.grass);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new GameManager();
		
	}

	public DisplayManager getDisplay() {
		return display;
	}

	public void setDisplay(DisplayManager display) {
		this.display = display;
	}
}