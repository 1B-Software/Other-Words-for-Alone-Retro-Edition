package robatortas.code.files.core.utils;

import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.settings.Globals;

public class LoopingUtils {
	
	public static long lastTime = System.nanoTime();
	public static long timer = System.currentTimeMillis();
	public static double ns = 1000000000.0/60.0;
	public double delta = 0;
	public static int frames = 0;
	public static int ticks = 0;
	
	public static int fps;
	public static int tps;
	
	
	public void whileRunning() {
		long now = System.nanoTime();
		long passedTime = now-lastTime;
		delta += passedTime/ns;
		
		lastTime = now;
	}
	
	public void deltaLoop(GameManager game) {
		if(delta >= 1) {
			game.update();
			ticks++;
			delta--;
		}
	}
	
	public void timerLoop(String consolePrint, GameManager game, @SuppressWarnings("rawtypes") CustFunc function) {
		if(System.currentTimeMillis() - timer > 1000) {
			timer+=1000;
			
			game.frame.setTitle(Globals.TITLE + "  ||  " + tps + " TPS " + fps + " FPS");
			function.func();
			

			fps = frames;
			tps = ticks;
			
			frames = 0;
			ticks = 0;
		}
	}
}
