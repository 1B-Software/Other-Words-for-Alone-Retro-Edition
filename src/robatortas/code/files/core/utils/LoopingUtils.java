package robatortas.code.files.core.utils;

import robatortas.code.files.project.GameManager;

public class LoopingUtils {
	
	public long lastTime = System.nanoTime();
	public long timer = System.currentTimeMillis();
	public double ns = 1000000000.0/60.0;
	public double delta = 0;
	public int frames = 0;
	public int ticks = 0;
	
	
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
	
	public void timerLoop() {
		if(System.currentTimeMillis() - timer > 1000) {
			timer+=1000;
			System.out.println("ticks: " + ticks + "  ||  " + "fps: " + frames);
			frames = 0;
			ticks = 0;
		}
	}
}
