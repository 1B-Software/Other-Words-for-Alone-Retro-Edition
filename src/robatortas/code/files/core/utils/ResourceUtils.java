package robatortas.code.files.core.utils;

public class ResourceUtils {
	
	public int memory = 0;
	public int maxMemory = 0;
	public int processors = 0;
	
	/**<NEWLINE>
	 * <b>getMaxMemory function in ResourceUtils class</b>
	 * <br><br>
	 * Gets the maximum amount of memory the JVM will attempt to use.
	 * 
	 * @see Runtime
	 */
	public int getMaxMemory() {
		this.maxMemory = (int) Runtime.getRuntime().maxMemory()/1024;
		return maxMemory;
	}
	
	public int getMemory() {
		this.memory = (int) (((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) / 1024);
		return this.memory;
	}
	
	public int getProcessors() {
		this.processors = Runtime.getRuntime().availableProcessors();
		return processors;
	}
}