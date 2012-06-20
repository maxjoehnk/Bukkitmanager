package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.BmPlugin;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmPluginRestart {

	private static Plugin plugin;
	private static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
		plugin = BmPlugin.getPlugin();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm plugin restart [plugin]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm plugin restart [plugin]");
			else {
				if (permHandler.has(sender, "bm.plugin.restart")) {
					if (args.length == 2) {
						plugin.getServer().getPluginManager().disablePlugin(plugin);
						plugin.getServer().getPluginManager().enablePlugin(plugin);
						io.send(sender, io.translate("Command.Plugin.Restart.Success").replaceAll("%plugin%", "Bukkitmanager"));
					}else {
						if (plugin.getServer().getPluginManager().getPlugin(args[2]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						}else {
							if (args[2] == "Spout") io.sendWarning(sender, io.translate("Command.Plugin.Restart.Spout"));
							else {
								plugin.getServer().getPluginManager().disablePlugin(plugin.getServer().getPluginManager().getPlugin(args[2]));
								plugin.getServer().getPluginManager().enablePlugin(plugin.getServer().getPluginManager().getPlugin(args[2]));
								io.send(sender, io.translate("Command.Plugin.Restart.Success").replaceAll("%plugin%", args[2]));
							}
						}
					}
				}
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/plugin restart [plugin]");
			else if (args.length > 2) io.sendManyArgs(sender, "/plugin restart [plugin]");
			else {
				if (permHandler.has(sender, "bm.plugin.restart")) {
					if (args.length == 1) {
						plugin.getServer().getPluginManager().disablePlugin(plugin);
						plugin.getServer().getPluginManager().enablePlugin(plugin);
						io.send(sender, io.translate("Command.Plugin.Restart.Success").replaceAll("%plugin%", "Bukkitmanager"));
					}else {
						if (plugin.getServer().getPluginManager().getPlugin(args[1]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						}else {
							if (args[1] == "Spout") io.sendWarning(sender, io.translate("Command.Plugin.Restart.Spout"));
							else {
								plugin.getServer().getPluginManager().disablePlugin(plugin.getServer().getPluginManager().getPlugin(args[1]));
								plugin.getServer().getPluginManager().enablePlugin(plugin.getServer().getPluginManager().getPlugin(args[1]));
								io.send(sender, io.translate("Command.Plugin.Restart.Success").replaceAll("%plugin%", args[1]));
							}
						}
					}
				}
			}
		}
	}
	
	private String getPluginList() {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
		
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
