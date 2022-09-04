package robatortas.code.files.core.utils;

import robatortas.code.files.project.GameManager;

/**<NEWLINE>
 * <b>ThreadUtils class</b>
 * <br><br>
 * Takes care of handling threads
 */
public class ThreadUtils{
	
	public GameManager game;
	
	public Thread thread;
	public boolean running = false;
	
	/**<NEWLINE>
	 * <b>start function on the ThreadUtils class</b>
	 * <br><br>
	 * Starts the thread.
	 * <br><br>
	 * Global function, meaning it can be used to create infinite threads
	 */
	public synchronized void start() {
		thread.start();
		running = true;
	}
	
	/**<NEWLINE>
	 * <b>stopfunction on the ThreadUtils class</b>
	 * <br><br>
	 * Stops the thread.
	 * <br><br>
	 * Global function, meaning it can be used to stop any threads
	 */
	public synchronized void stop() {
		try {
			thread.join();
		} catch(Exception e) {
			e.printStackTrace();
		}
		running = false;
	}
}
