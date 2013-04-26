package org.efreak.bukkitmanager.remoteserver.commands;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.remoteserver.RemoteCommand;
import org.efreak.bukkitmanager.remoteserver.RemoteCommandHandler;
import org.efreak.bukkitmanager.remoteserver.ReturnCodes;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerCommands extends RemoteCommandHandler {

	@RemoteCommand(name="serverinfo")
	public String serverinfo() {
		JSONObject json = new JSONObject();
		try {
			json.put("mc", Bukkit.getBukkitVersion().split("-")[0]);
			json.put("api", Bukkit.getBukkitVersion());
			json.put("build", Bukkit.getVersion().split("-b")[1].split("jnks")[0]);
			json.put("os", System.getProperty("os.name"));
			json.put("arch", System.getProperty("os.arch"));
			json.put("jvmname", System.getProperty("java.vm.name"));
			json.put("jvmversion", System.getProperty("java.vm.version"));
			json.put("javaversion", System.getProperty("java.version"));
			Long uptimeMilli = ManagementFactory.getRuntimeMXBean().getUptime();
			String uptime = String.format("%d hours %d min %d sec",
				TimeUnit.MILLISECONDS.toHours(uptimeMilli),
			    TimeUnit.MILLISECONDS.toMinutes(uptimeMilli),
			    TimeUnit.MILLISECONDS.toSeconds(uptimeMilli) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptimeMilli))
			);
			json.put("uptime", uptime);
			int mb = 1024*1024;
			long maxMem = Runtime.getRuntime().maxMemory()/mb
				,totalMem = Runtime.getRuntime().totalMemory()/mb
				,freeMem = Runtime.getRuntime().freeMemory()/mb
				,usedMem = totalMem - freeMem;
			json.put("maxmem", maxMem);
			json.put("allocatedmem", totalMem);
			json.put("usedmem", usedMem);
			json.put("freemem", freeMem);
			json.put("plugins", PluginManager.getPlugins().length);
			json.put("cpuload", 25);
			json.put("cpucount", Runtime.getRuntime().availableProcessors());
		}catch(JSONException e) {
			if (config.getDebug()) e.printStackTrace();
			return ReturnCodes.echo500();
		}
		return json.toString();
	}
	
	@RemoteCommand(name="shutdown")
	public String shutdown() {
		Bukkit.shutdown();
		return ReturnCodes.echo200();
	}

	/*@RemoteCommand(name="restart")
	public String restart() {
		return ReturnCodes.echo200();
	}
	
	@RemoteCommand(name="start")
	public String start() {
		return ReturnCodes.echo200();
	}*/
}
