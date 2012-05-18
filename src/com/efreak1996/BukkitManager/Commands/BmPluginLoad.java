package com.efreak1996.BukkitManager.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.BmPlugin;
import com.efreak1996.BukkitManager.PluginManager.BmPluginManager;

public class BmPluginLoad {

	private static BmIOManager io;
	private static Plugin bukkitmanager;
	private static BmPermissions permHandler;
	private static BmPluginManager pm;
	private static BmConfiguration config;
	
	public void initialize() {
		bukkitmanager = BmPlugin.getPlugin();
		io = new BmIOManager();
		permHandler = new BmPermissions();
		pm = BmPlugin.getPluginManager();
		config = new BmConfiguration();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 3) io.sendFewArgs(sender, "/bm plugin load (path) [file]");
			else if (args.length > 4) io.sendManyArgs(sender, "/bm plugin load (path) [file]");
			else {
				if (permHandler.has(sender, "bm.plugin.load")) {
					if (args.length == 3) {
						File dir = new File(bukkitmanager.getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), args[2]);
						System.out.println(dir);
						System.out.println(dir.toString());
						if (dir.exists()) {
							if (dir.isDirectory()) {
								try {
									Plugin[] plugins = pm.loadPlugins(dir);
									pm.enablePlugins(plugins);
									io.send(sender, io.translate("Command.Plugin.Load.Dir.Success").replaceAll("%dir%", dir.getAbsolutePath()).replaceAll("%plugins%", plugins.toString()));
								}catch (Exception e) {
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
						}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));
					}else {
						File dir = new File(bukkitmanager.getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), args[2]);
						if (dir.exists()) {
							if (dir.isDirectory()) {
								File jarFile = new File(dir, args[3]);
								if (jarFile.exists()) {
									if (jarFile.isFile()) {
										try {
											Plugin plugin = pm.loadPlugin(jarFile);
											pm.enablePlugin(plugin);
											io.send(sender, io.translate("Command.Plugin.Load.File.Success").replaceAll("%file%", jarFile.getName()).replaceAll("%plugin%", plugin.getDescription().getFullName()));								
										} catch (Exception e) {
											if (config.getDebug()) e.printStackTrace();
										}
									}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoFile").replaceAll("%file%", jarFile.getName()));
								}else io.sendError(sender, io.translate("Command.Plugin.Load.FileDoesntExists").replaceAll("%file%", jarFile.getName()));
							}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
						}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));
					}
				}
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/plugin load (path) [file]");
			else if (args.length > 3) io.sendManyArgs(sender, "/plugin load (path) [file]");
			else {
				if (permHandler.has(sender, "bm.plugin.load")) {
					if (args.length == 2) {
						File dir = new File(bukkitmanager.getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), args[1]);
						if (dir.exists()) {
							if (dir.isDirectory()) {
								try {
									Plugin[] plugins = pm.loadPlugins(dir);
									pm.enablePlugins(plugins);
									io.send(sender, io.translate("Command.Plugin.Load.Dir.Success").replaceAll("%dir%", dir.getAbsolutePath()).replaceAll("%plugins%", plugins.toString()));
								}catch (Exception e) {
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
						}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));
					}else {
						File dir = new File(bukkitmanager.getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), args[1]);
						if (dir.exists()) {
							if (dir.isDirectory()) {
								File jarFile = new File(dir, args[2]);
								if (jarFile.isFile()) {
									try {
										Plugin plugin = pm.loadPlugin(jarFile);
										pm.enablePlugin(plugin);
										io.send(sender, io.translate("Command.Plugin.Load.File.Success").replaceAll("%file%", jarFile.getName()).replaceAll("%plugin%", plugin.getDescription().getFullName()));								
									} catch (Exception e) {
										if (config.getDebug()) e.printStackTrace();
									}
								}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoFile").replaceAll("%file%", jarFile.getName()));
							}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
						}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));

					}
				}
			}
		}
	}
}
