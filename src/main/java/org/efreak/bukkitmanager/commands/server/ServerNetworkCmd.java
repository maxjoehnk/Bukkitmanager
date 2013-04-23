package org.efreak.bukkitmanager.commands.server;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class ServerNetworkCmd extends Command {

	public ServerNetworkCmd() {
		super("network", "Server.Network", "bm.server.network", new ArrayList<String>(), CommandCategory.SERVER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm server network [list] [#]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm server network [list] [#]");
		else {
			if (args.length == 1) {
				if (has(sender, "bm.server.network")) {
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
						io.send(sender, "Is Up:          " + netInterface.isUp(), false);
						io.send(sender, "MTU:            " + netInterface.getMTU(), false);
						io.send(sender, "Point-to-Point: " + netInterface.isPointToPoint(), false);	
						io.send(sender, "Supports Multicast: " + netInterface.supportsMulticast(), false);
						io.send(sender, "Loopback:       " + netInterface.isLoopback(), false);
						io.send(sender, "Virtual:        " + netInterface.isVirtual(), false);
					}catch (SocketException e) {
						io.send(sender, "Can't load Networkinformation", false);
						if (config.getDebug()) e.printStackTrace();
					}
				}
			}else if (args[1].equalsIgnoreCase("list")) {
				if (has(sender, "bm.server.network.list")) {
					if (args.length == (2)) {
						try {
							int pages = Collections.list(NetworkInterface.getNetworkInterfaces()).size()/9;
							io.sendHeader(sender, "NETWORK LIST (1/" + pages + ")");
						} catch (SocketException e) {
							io.sendHeader(sender, "NETWORK LIST");
							if (config.getDebug()) e.printStackTrace();
						}
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
						try {
							int pages = Collections.list(NetworkInterface.getNetworkInterfaces()).size()/9;
							io.sendHeader(sender, "NETWORK LIST (" + args[2] + "/" + pages + ")");
						} catch (SocketException e) {
							io.sendHeader(sender, "NETWORK LIST");
							if (config.getDebug()) e.printStackTrace();
						}
						int i = 1;
						int min = (Integer.parseInt(args[2])-1)*9;
						int max = Integer.parseInt(args[2])*9;
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
				if (has(sender, "bm.server.network")) {
					try {
						int pages = Collections.list(NetworkInterface.getNetworkInterfaces()).size();
						io.sendHeader(sender, "NETWORK INFOS (" + args[1] + "/" + pages + ")");
					} catch (SocketException e) {
						io.sendHeader(sender, "NETWORK INFOS");
						if (config.getDebug()) e.printStackTrace();
					}
					try {
						Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
						int i = 1;
						while (networkInterfaces.hasMoreElements()) {
							NetworkInterface netInterface = networkInterfaces.nextElement();
							if (args[1].equals(String.valueOf(i))) {
								io.send(sender, "#" + i + ": " + netInterface.getDisplayName(), false);
								io.send(sender, "Is Up:          " + netInterface.isUp(), false);
								io.send(sender, "MTU:            " + netInterface.getMTU(), false);
								io.send(sender, "Point-to-Point: " + netInterface.isPointToPoint(), false);	
								io.send(sender, "Supports Multicast: " + netInterface.supportsMulticast(), false);
								io.send(sender, "Loopback:       " + netInterface.isLoopback(), false);
								io.send(sender, "Virtual:        " + netInterface.isVirtual(), false);
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
		}
		return true;
	}
}
