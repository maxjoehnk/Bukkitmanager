package org.efreak.bukkitmanager.commands.server;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.util.CpuUsage;

public class ServerLoadCmd extends Command {

	public ServerLoadCmd() {
		super("load", "Server.Load", "bm.server.load", new ArrayList<String>(), CommandCategory.SERVER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm server info");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm server info");
		else {
			if (has(sender, "bm.server.load")) {
				int mb = 1024*1024;
				long maxMem = Runtime.getRuntime().maxMemory()/mb
					,totalMem = Runtime.getRuntime().totalMemory()/mb
					,freeMem = Runtime.getRuntime().freeMemory()/mb
					,usedMem = totalMem - freeMem;
				String cpuUsage = "";
				if (config.getBoolean("General.CPULoad")) cpuUsage = String.format("%.2f", CpuUsage.getUsage()) + "%";
				io.sendHeader(sender, "SERVER LOAD");
				if (config.getBoolean("General.CPULoad")) io.send(sender, "CPU Usage:        " + cpuUsage, false);
				io.send(sender, "Max Memory:       " + maxMem + "MB", false);
				io.send(sender, "Allocated Memory: " + totalMem + "MB", false);
				io.send(sender, "Used Memory:      " + usedMem + "MB", false);
				io.send(sender, "Free Memory:      " + freeMem + "MB", false);
			}
		}
		return true;
	}
}
