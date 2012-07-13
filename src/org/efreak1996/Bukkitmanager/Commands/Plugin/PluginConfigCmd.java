package org.efreak1996.Bukkitmanager.Commands.Plugin;

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
import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PluginConfigCmd extends Command {

	public PluginConfigCmd() {
		super("config", "Plugin.Config", Arrays.asList("(plugin)", "(entry)", "[value]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (3 + length)) io.sendFewArgs(sender, "/bm plugin config (plugin) (entry) [value]");
		else if (args.length > (4 + length)) io.sendManyArgs(sender, "/bm plugin config (plugin) (entry) [value]");
		else {
			if (args.length == (3 + length)) {
				if (has(sender, "bm.plugin.config.get")) {
					if (Bukkit.getServer().getPluginManager().getPlugin(args[1 + length]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						return true;
					}else {
						Plugin plugin = Bukkitmanager.getPluginManager().getPlugin(args[1 + length]);
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
							if (pluginConfig.get(args[2 + length]) != null) {
								io.send(sender, io.translate("Command.Plugin.Config.Get").replaceAll("%entry%", args[2 + length]).replaceAll("%value%", String.valueOf(pluginConfig.get(args[2 + length]))));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
						}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
					}
				}
			}else if (args.length == (4 + length)) {
				if (has(sender, "bm.plugin.config.set")) {
					if (Bukkit.getServer().getPluginManager().getPlugin(args[1 + length]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.sendError(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						return true;
					}else {
						Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[1 + length]);
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
							if (pluginConfig.get(args[2 + length]) != null) {
								String old = pluginConfig.get(args[2 + length]).toString();
								pluginConfig.set(args[2 + length], args[3 + length]);
								try {
									pluginConfig.save(pluginFolder + File.separator + "config.yml");
								} catch (IOException e) {
									if (config.getDebug()) e.printStackTrace();
								}
								io.send(sender, io.translate("Command.Plugin.Config.Set").replaceAll("%entry%", args[2 + length]).replaceAll("%value%", String.valueOf(pluginConfig.get(args[2 + length]))));
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
		Plugin[] plugins = Bukkitmanager.getPluginManager().getPlugins();
		
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
