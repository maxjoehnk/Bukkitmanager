package org.efreak.bukkitmanager.commands.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginConfigCmd extends Command {

	public PluginConfigCmd() {
		super("config", "Plugin.Config", "bm.plugin.config", Arrays.asList("(plugin)", "(entry|list)", "[value]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 3) io.sendFewArgs(sender, "/bm plugin config (plugin) (entry|list) [value]");
		else if (args.length > 4) io.sendManyArgs(sender, "/bm plugin config (plugin) (entry|list) [value]");
		else {
			if (args.length == 3 && args [2].equalsIgnoreCase("list")) {
				if (has(sender, "bm.plugin.config.get")) {
					if (Bukkit.getServer().getPluginManager().getPlugin(args[1]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						return true;
					}else {
						Plugin plugin = PluginManager.getPlugin(args[1]);
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
							/*Object[] keys = pluginConfig.getKeys(true).toArray();
							for (int i = 0; i < keys.length; i++) {
								io.send(sender, keys[i].toString());
							}
							io.send(sender, pluginConfig.saveToString());*/
							Object[] keys = pluginConfig.getKeys(true).toArray();
							StringBuilder output = new StringBuilder();
							output.append(keys[1].toString() + "=" + pluginConfig.get(keys[1].toString()));
							for (int i = 2; i < keys.length; i++) {
								String value = pluginConfig.get(keys[i].toString()).toString();
								if (!value.startsWith("MemorySection")) output.append(", " + keys[i] + "=" + value);
							}
							io.send(sender, io.translate("Command.Plugin.Config.List").replaceAll("%plugin%", plugin.getName()).replaceAll("%items%", output.toString()));
						}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
					}
				}
			}else if (args.length == 3) {
				if (has(sender, "bm.plugin.config.get")) {
					if (Bukkit.getServer().getPluginManager().getPlugin(args[1]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						return true;
					}else {
						Plugin plugin = PluginManager.getPlugin(args[1]);
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
				if (has(sender, "bm.plugin.config.set")) {
					if (Bukkit.getServer().getPluginManager().getPlugin(args[1]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.sendError(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						return true;
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
								io.send(sender, io.translate("Command.Plugin.Config.Set").replaceAll("%entry%", args[2]).replaceAll("%value_old%", old).replaceAll("%value_new%", args[3]));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
						}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
					}
				}
			}
		}
		return true;
	}

	private String getPluginList() {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = PluginManager.getPlugins();
		
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
