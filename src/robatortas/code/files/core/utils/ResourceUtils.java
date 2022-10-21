package robatortas.code.files.core.utils;

import java.lang.management.ManagementFactory;
import com.sun.management.*;
import java.lang.management.RuntimeMXBean;

public class ResourceUtils {
	
	public int memory = 0;
	public int maxMemory = 0;
	public double cpUsage = 0;
	public double threadCount = 0;
	public int upTime = 0;
	public int threads = 0;
	public String osName = "N/A";
	
	OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	
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
	
	public double getCPUsage() {
		this.cpUsage = osBean.getSystemCpuLoad()*125;
		return cpUsage;
	}
	
	public double getThreadCount() {
		this.threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
		return threadCount;
	}
	
	public int getThreads() {
		threads = ManagementFactory.getThreadMXBean().getThreadCount();
		return threads;
	}
	
	public String getOSName() {
		this.osName = System.getProperty("os.name");
		return osName;
	}
}