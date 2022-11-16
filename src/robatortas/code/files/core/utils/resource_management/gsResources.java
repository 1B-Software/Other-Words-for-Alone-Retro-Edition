package robatortas.code.files.core.utils.resource_management;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.sun.management.OperatingSystemMXBean;

import robatortas.code.files.core.utils.ResourceUtils;

/**<NEWLINE>
 * <b>gsResources class</b>
 * <br><br>
 * The GS (Getters and Setters) Resources class gets you the necessary tools for
 * <br>
 * resources management.
 */
public class gsResources extends ResourceUtils {
	
	OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	
	RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
	
	/**<NEWLINE>
	 * <b>getMaxMemory function in ResourceUtils class</b>
	 * <br><br>
	 * Gets the maximum amount of memory the JVM will attempt to use.
	 * 
	 * @see Runtime
	 */
	public int getMaxMemory() {
		return maxMemory;
	}
	
	public int getMemory() {
		return this.memory;
	}
	
	public double getCPUsage() {
		return cpUsage;
	}
	
	public int getThreads() {
		return threads;
	}
	
	public String getOSName() {
		return osName;
	}
	
	/**<NEWLINE>
	 * <b>getUptime function in ResourceUtils class</b>
	 * <br><br>
	 * Gets the maximum amount of memory the JVM will attempt to use.
	 * 
	 * @see Runtime
	 */
	public long getUptime() {
		this.upTime = (int) runtimeBean.getUptime();
		return upTime;
	}
	
	// SETTERS
	
	/**<NEWLINE>
	 * <b>setMaxMemory function in ResourceUtils class</b>
	 * <br><br>
	 * Sets the maximum amount of memory the JVM will attempt to use.
	 * 
	 * @see Runtime
	 */
	public void setMaxMemory() {
		this.maxMemory = (int) Runtime.getRuntime().maxMemory()/1024;
	}
	
	/**<NEWLINE>
	 * <b>setMaxMemory function in ResourceUtils class</b>
	 * <br><br>
	 * Gets the maximum amount of memory the JVM will attempt to use.
	 * 
	 * @see Runtime
	 */
	public void setMemory() {
		this.memory = (int) (((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) / 1024);
	}
	
	public void setCPUsage() {
		this.cpUsage = osBean.getSystemCpuLoad()*100;
	}
	
	public void setThreads() {
		threads = ManagementFactory.getThreadMXBean().getThreadCount();
	}
	
	public void setOSName() {
		this.osName = System.getProperty("os.name");
	}
}