package com.efreak1996.BukkitManager.Commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;

public class BmPluginConfig {
	
	private static BmIOManager io;
	private static BmConfiguration config;
	private static BmPermissions permHandler;
	
	public void shutdown() {}
	
	public void initialize() {
		config = new BmConfiguration();
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 4) io.sendFewArgs(sender, "/bm plugin config (plugin) (entry) [value]");
			else if (args.length > 5) io.sendManyArgs(sender, "/bm plugin config (plugin) (entry) [value]");
			else {
				if (args.length == 4) {
					if (permHandler.has(sender, "bm.plugin.config.get")) {
						if (Bukkit.getServer().getPluginManager().getPlugin(args[2]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
							return;
						}else {
							Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
							File pluginFolder = plugin.getDataFolder();
							if (new File(pluginFolder + File.separator + "config.yml").exists()) {
								FileConfiguration pluginConfig = plugin.getConfig();
								try {
									pluginConfig.load(pluginFolder + File.separator + "config.yml");
								} catch (FileNotFoundException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (IOException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (InvalidConfigurationException e) {
									if (config.getDebug()) e.printStackTrace();
								}
								if (pluginConfig.get(args[3]) != null) {
									io.send(sender, io.translate("Command.Plugin.Config.Get").replaceAll("%entry%", args[3]).replaceAll("%value%", String.valueOf(pluginConfig.get(args[3]))));
								}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
						}
					}
				}else if (args.length == 5) {
					if (permHandler.has(sender, "bm.plugin.config.set")) {
						if (Bukkit.getServer().getPluginManager().getPlugin(args[2]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.sendError(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
							return;
						}else {
							Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
							File pluginFolder = plugin.getDataFolder();
							if (new File(pluginFolder + File.separator + "config.yml").exists()) {
								FileConfiguration pluginConfig = plugin.getConfig();
								try {
									pluginConfig.load(pluginFolder + File.separator + "config.yml");
								} catch (FileNotFoundException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (IOException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (InvalidConfigurationException e) {
									if (config.getDebug()) e.printStackTrace();
								}
								if (pluginConfig.get(args[3]) != null) {
									String old = pluginConfig.get(args[3]).toString();
									pluginConfig.set(args[3], args[4]);
									try {
										pluginConfig.save(pluginFolder + File.separator + "config.yml");
									} catch (IOException e) {
										if (config.getDebug()) e.printStackTrace();
									}
									io.send(sender, io.translate("Command.Plugin.Config.Set").replaceAll("%entry%", args[3]).replaceAll("%value%", String.valueOf(pluginConfig.get(args[3]))));
								}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
						}
					}
				}
			}
		}else {
			if (args.length < 3) io.sendFewArgs(sender, "/plugin config (plugin) (entry) [value]");
			else if (args.length > 4) io.sendManyArgs(sender, "/plugin config (plugin) (entry) [value]");
			else {
				if (args.length == 3) {
					if (permHandler.has(sender, "bm.plugin.config.get")) {
						if (Bukkit.getServer().getPluginManager().getPlugin(args[1]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
							return;
						}else {
							Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[1]);
							File pluginFolder = plugin.getDataFolder();
							if (new File(pluginFolder + File.separator + "config.yml").exists()) {
								FileConfiguration pluginConfig = plugin.getConfig();
								try {
									pluginConfig.load(pluginFolder + File.separator + "config.yml");
								} catch (FileNotFoundException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (IOException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (InvalidConfigurationException e) {
									if (config.getDebug()) e.printStackTrace();
								}
								if (pluginConfig.get(args[2]) != null) {
									io.send(sender, io.translate("Command.Plugin.Config.Get").replaceAll("%entry%", args[2]).replaceAll("%value%", String.valueOf(pluginConfig.get(args[2]))));
								}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
						}
					}
				}else if (args.length == 4) {
					if (permHandler.has(sender, "bm.plugin.config.set")) {
						if (Bukkit.getServer().getPluginManager().getPlugin(args[1]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.sendError(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
							return;
						}else {
							Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[1]);
							File pluginFolder = plugin.getDataFolder();
							if (new File(pluginFolder + File.separator + "config.yml").exists()) {
								FileConfiguration pluginConfig = plugin.getConfig();
								try {
									pluginConfig.load(pluginFolder + File.separator + "config.yml");
								} catch (FileNotFoundException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (IOException e) {
									if (config.getDebug()) e.printStackTrace();
								} catch (InvalidConfigurationException e) {
									if (config.getDebug()) e.printStackTrace();
								}
								if (pluginConfig.get(args[2]) != null) {
									String old = pluginConfig.get(args[2]).toString();
									pluginConfig.set(args[2], args[3]);
									try {
										pluginConfig.save(pluginFolder + File.separator + "config.yml");
									} catch (IOException e) {
										if (config.getDebug()) e.printStackTrace();
									}
									io.send(sender, io.translate("Command.Plugin.Config.Set").replaceAll("%entry%", args[2]).replaceAll("%value%", String.valueOf(pluginConfig.get(args[2]))));
								}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
						}
					}
				}
			}
		}
	}
	
	private String getPluginList() {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
		
		for (Plugin plugin : plugins) {
			if (pluginList.length() > 0) {
				pluginList.append(ChatColor.WHITE);
				pluginList.append(", ");
			}
			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getName());
		}
		return pluginList.toString();
	}
}
