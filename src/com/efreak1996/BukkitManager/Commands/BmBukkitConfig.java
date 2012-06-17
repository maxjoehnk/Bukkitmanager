package com.efreak1996.BukkitManager.Commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmBukkitConfig {
	
	public static FileInputStream streamIn = null;
	public static FileOutputStream streamOut = null;
	public static Properties propertie;
	private static BmConfiguration config;
	private static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		propertie = new Properties();
		config = new BmConfiguration();
		permHandler = new BmPermissions();
		io = new BmIOManager();
		try {
			streamIn = new FileInputStream("server.properties");
			propertie.load(streamIn);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		try {
			streamIn = new FileInputStream("server.properties");
			propertie.load(streamIn);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		if (prefixed) {
			if (args.length < 3) io.sendFewArgs(sender, "/bm bukkit config (entry) [value]");
			else if (args.length > 4) io.sendManyArgs(sender, "/bm bukkit config (entry) [value]");
			else {
				if (args.length == 3) {
					if (permHandler.has(sender, "bm.bukkit.config.get")) {
						if (propertie.getProperty(args[2]) != null) io.send(sender, io.translate("Command.Bukkit.Config.Get").replaceAll("%entry%", args[2]).replaceAll("%value%", propertie.getProperty(args[2])));
						else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[2]));
					}
				}
				else if (args.length == 4) {
					if (permHandler.has(sender, "bm.bukkit.config.set")) {
						if (propertie.getProperty(args[2]) != null) {
							propertie.setProperty(args[2], args[3]);
							io.send(sender, io.translate("Command.Bukkit.Config.Set").replaceAll("%entry%", args[2]).replaceAll("%value%", args[3]));
							try {
								streamOut = new FileOutputStream("server.properties");
							} catch (FileNotFoundException e) {
								if (config.getDebug()) e.printStackTrace();
							}
							propertie.save(streamOut, null);
							try {
								streamOut.flush();
								streamOut.close();
							} catch (IOException e) {
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[2]));
					}
				}
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/bukkit config (entry) [value]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bukkit config (entry) [value]");
			else {
				if (args.length == 2) {
					if (permHandler.has(sender, "bm.bukkit.config.get")) {
						if (propertie.getProperty(args[1]) != null) io.send(sender, io.translate("Command.Bukkit.Config.Get").replaceAll("%entry%", args[1]).replaceAll("%value%", propertie.getProperty(args[1])));
						else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[1]));
					}
				}
				else if (args.length == 3) {
					if (permHandler.has(sender, "bm.bukkit.config.set")) {
						if (propertie.getProperty(args[1]) != null) {
							propertie.setProperty(args[1], args[2]);
							io.send(sender, io.translate("Command.Bukkit.Config.Set").replaceAll("%entry%", args[1]).replaceAll("%value%", args[2]));
							try {
								streamOut = new FileOutputStream("server.properties");
							} catch (FileNotFoundException e) {
								if (config.getDebug()) e.printStackTrace();
							}
							propertie.save(streamOut, null);
							try {
								streamOut.flush();
								streamOut.close();
							} catch (IOException e) {
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[1]));
					}
				}
			}
		}
		try {
			streamIn.close();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
}
