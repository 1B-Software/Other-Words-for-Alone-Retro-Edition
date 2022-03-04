package robatortas.code.files.core.utils;

import robatortas.code.files.GameManager;

public class ThreadManager {
	
	public GameManager game;
	
	public Thread thread;
	public boolean running = false;
	
	public synchronized void start() {
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		} catch(Exception e) {
			e.printStackTrace();
		}
		running = false;
	}
}
