package org.efreak.bukkitmanager.commands;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.util.CpuUsage;

public class ServerCmd extends CommandHandler {

	public ServerCmd() {
		super();
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		if (args.length == 1) {
			for (String subCommand : subCommands.keySet()) {
				if (subCommand.startsWith(args[0])) tabs.add(subCommand);
			}
		}else if (subCommands.containsKey(args[0])) {
			try {
				CommandHandler handler = (CommandHandler) subCommands.get(args[0]).getDeclaringClass().newInstance();
				return handler.onTabComplete(sender, cmd, label, args);
			}catch(Exception e) {
				io.sendConsoleWarning("Error on TabCompletion: " + e.getLocalizedMessage());
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return tabs;
	}
	
	@Command(label = "server", helpNode = "Server", hideHelp = true, usage = "/server <info|load|network> [list] [#]")
	public boolean serverCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@SubCommand(label = "load", helpNode = "Server.Load", permission = "bm.server.load", usage = "server load")
	public boolean serverLoadCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm server load");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm server load");
		else {
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
		return true;
	}
	
	@SubCommand(label = "info", helpNode = "Server.Info", permission = "bm.server.info", usage = "server info")
	public boolean serverInfoCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm server info");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm server info");
		else {
			Long uptimeMilli = ManagementFactory.getRuntimeMXBean().getUptime();
			String uptime = String.format("%d hours %d min %d sec",
					TimeUnit.MILLISECONDS.toHours(uptimeMilli),
				    TimeUnit.MILLISECONDS.toMinutes(uptimeMilli),
				    TimeUnit.MILLISECONDS.toSeconds(uptimeMilli) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptimeMilli))
				);
			String cpuUsage = "";
			io.sendHeader(sender, "SERVER INFOS");
			io.send(sender, "Operating System:    " + System.getProperty("os.name"), false);
			io.send(sender, "OS Architecture:     " + System.getProperty("os.arch"), false);
			io.send(sender, "JVM Name:            " + System.getProperty("java.vm.name"), false);
			io.send(sender, "JVM Version:         " + System.getProperty("java.vm.version"), false);
			io.send(sender, "Java Version:        " + System.getProperty("java.version"), false);
			io.send(sender, "Uptime:              " + uptime, false);
			io.send(sender, "CPU Count:           " + Runtime.getRuntime().availableProcessors(), false);
			if (Permissions.has(sender, "bm.server.load")) {
				int mb = 1024*1024;
				long usedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/mb;
				if (config.getBoolean("General.CPULoad")) {
					cpuUsage = String.format("%.2f", CpuUsage.getUsage()) + "%";
					io.send(sender, "CPU Usage:           " + cpuUsage, false);
				}
				io.send(sender, "Used Memory:         " + usedMem + "MB", false);
			}
		}
		return true;
	}
	
	@SubCommand(label = "network", helpNode = "Server.Network", permission = "bm.server.network", usage = "server network [list] [#]")
	public boolean serverNetworkCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm server network [list] [#]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm server network [list] [#]");
		else {
			if (args.length == 0) {
				try {
					int pages = Collections.list(NetworkInterface.getNetworkInterfaces()).size();
					io.sendHeader(sender, "NETWORK INFOS (1/" + pages + ")");
				} catch (SocketException e) {
					io.sendHeader(sender, "NETWORK INFOS");
					if (config.getDebug()) e.printStackTrace();
				}
				try {
					Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
					NetworkInterface netInterface = networkInterfaces.nextElement();
					io.send(sender, "#1: " + netInterface.getDisplayName(), false);
					io.send(sender, "Is Up:              " + netInterface.isUp(), false);
					io.send(sender, "MTU:                " + netInterface.getMTU(), false);
					io.send(sender, "Point-to-Point:     " + netInterface.isPointToPoint(), false);	
					io.send(sender, "Supports Multicast: " + netInterface.supportsMulticast(), false);
					io.send(sender, "Loopback:           " + netInterface.isLoopback(), false);
					io.send(sender, "Virtual:            " + netInterface.isVirtual(), false);
				}catch (SocketException e) {
					io.send(sender, "Can't load Networkinformation", false);
					if (config.getDebug()) e.printStackTrace();
				}
			}else if (args[0].equalsIgnoreCase("list")) {
				if (Permissions.has(sender, "bm.server.network.list")) {
					int pages = 1;
					try {
						pages = Collections.list(NetworkInterface.getNetworkInterfaces()).size()/9;
						if (Collections.list(NetworkInterface.getNetworkInterfaces()).size() % 9 > 0) pages++;
					}catch (SocketException e) {
						if (config.getDebug()) e.printStackTrace();
					}
					if (args.length == 1) {
						io.sendHeader(sender, "NETWORK LIST (1/" + pages + ")");
						int i = 1;
						try {
							Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
							while (networkInterfaces.hasMoreElements() && i < 10) {
								io.send(sender, "#" + i + ": " + networkInterfaces.nextElement().getDisplayName(), false);
								i++;
							}
						}catch (SocketException e) {
							io.send(sender, "Can't load Networkinformation", false);
							if (config.getDebug()) e.printStackTrace();
						}
					}else {
						io.sendHeader(sender, "NETWORK LIST (" + args[1] + "/" + pages + ")");
						int i = 1;
						int min = (Integer.parseInt(args[1])-1)*9;
						int max = Integer.parseInt(args[1])*9;
						try {
							Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
							while (networkInterfaces.hasMoreElements() && i <= max) {
								if (i > min) io.send(sender, "#" + i + ": " + networkInterfaces.nextElement().getDisplayName(), false);
								i++;
							}
						}catch (SocketException e) {
							io.send(sender, "Can't load Networkinformation", false);
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}
			}else {
				try {
					int pages = Collections.list(NetworkInterface.getNetworkInterfaces()).size();
					io.sendHeader(sender, "NETWORK INFOS (" + args[0] + "/" + pages + ")");
				} catch (SocketException e) {
					io.sendHeader(sender, "NETWORK INFOS");
					if (config.getDebug()) e.printStackTrace();
				}
				try {
					Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
					int i = 1;
					while (networkInterfaces.hasMoreElements()) {
						NetworkInterface netInterface = networkInterfaces.nextElement();
						if (args[0].equals(String.valueOf(i))) {
							io.send(sender, "#" + i + ": " + netInterface.getDisplayName(), false);
							io.send(sender, "Is Up:              " + netInterface.isUp(), false);
							io.send(sender, "MTU:                " + netInterface.getMTU(), false);
							io.send(sender, "Point-to-Point:     " + netInterface.isPointToPoint(), false);	
							io.send(sender, "Supports Multicast: " + netInterface.supportsMulticast(), false);
							io.send(sender, "Loopback:           " + netInterface.isLoopback(), false);
							io.send(sender, "Virtual:            " + netInterface.isVirtual(), false);
							break;
						}
						i++;
					}
				}catch (SocketException e) {
					io.send(sender, "Can't load Networkinformation", false);
					if (config.getDebug()) e.printStackTrace();
				}
			}
		}
		return true;
	}
	
}
