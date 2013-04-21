package org.efreak.bukkitmanager.commands.server;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.util.CpuUsage;

public class ServerInfoCmd extends Command {

	public ServerInfoCmd() {
		super("info", "Server.Info", "bm.server.info", new ArrayList<String>(), CommandCategory.SERVER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm server info");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm server info");
		else {
			if (has(sender, "bm.server.info")) {
				Long uptimeMilli = ManagementFactory.getRuntimeMXBean().getUptime();
				String uptime = String.format("%d hours %d min %d sec",
						TimeUnit.MILLISECONDS.toHours(uptimeMilli),
					    TimeUnit.MILLISECONDS.toMinutes(uptimeMilli),
					    TimeUnit.MILLISECONDS.toSeconds(uptimeMilli) - 
					    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptimeMilli))
					);
				String cpuUsage = "";
				if (config.getBoolean("General.CPULoad") && has(sender, "bm.server.load")) cpuUsage = String.format("%.2f", CpuUsage.getUsage()) + "%";
				io.sendHeader(sender, "SERVER INFOS");
				io.send(sender, "Operating System:    " + System.getProperty("os.name"), false);
				io.send(sender, "OS Architecture:     " + System.getProperty("os.arch"), false);
				io.send(sender, "JVM Name:            " + System.getProperty("java.vm.name"), false);
				io.send(sender, "JVM Version:         " + System.getProperty("java.vm.version"), false);
				io.send(sender, "Java Version:        " + System.getProperty("java.version"), false);
				io.send(sender, "Uptime:              " + uptime, false);
				io.send(sender, "CPU Count:           " + Runtime.getRuntime().availableProcessors(), false);
				if (has(sender, "bm.server.load")) {
					int mb = 1024*1024;
					long usedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/mb;
					if (config.getBoolean("General.CPULoad")) io.send(sender, "CPU Usage:           " + cpuUsage, false);
					io.send(sender, "Used Memory:         " + usedMem + "MB", false);
				}
			}
		}
		return true;
	}
}
