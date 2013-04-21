package org.efreak.bukkitmanager.util;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

@SuppressWarnings("restriction")
public class CpuUsage {

	public static float getUsage() {
		float usage = 0.001F;
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
		int nCPUs = osbean.getAvailableProcessors();
		long prevUpTime = runbean.getUptime();
		long prevProcessCpuTime = osbean.getProcessCpuTime();
		try {
			Thread.sleep(50);
		} catch (Exception e) {e.printStackTrace();}
		osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		long upTime = runbean.getUptime();
		long processCpuTime = osbean.getProcessCpuTime();
		if (prevUpTime > 0L && upTime > prevUpTime) {
			long elapsedCpu = processCpuTime - prevProcessCpuTime;
			long elapsedTime = upTime - prevUpTime;
			usage = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * nCPUs));
		} else {
			usage = 0.001F;
		}
		return usage;
	}
	
}
