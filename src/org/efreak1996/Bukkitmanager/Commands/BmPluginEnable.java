package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;
import org.efreak1996.Bukkitmanager.BmPlugin;


public class BmPluginEnable {

	private static IOManager io;
	private static Plugin plugin;
	private static Permissions permHandler;
	
	public void initialize() {
		plugin = BmPlugin.getPlugin();
		io = new IOManager();
		permHandler = new Permissions();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm plugin enable [plugin]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm plugin enable [plugin]");
			else {
				if (permHandler.has(sender, "bm.plugin.enable")) {
					if (args.length == 2) {
						plugin.getServer().getPluginManager().enablePlugin(plugin);
						io.send(sender, io.translate("Command.Plugin.Enable.Success").replaceAll("%plugin%", "Bukkitmanager"));
					}else {
						if (plugin.getServer().getPluginManager().getPlugin(args[2]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						}else {
							if ((plugin.getServer().getPluginManager().getPlugin(args[2])).isEnabled()) io.sendError(sender, io.translate("Command.Plugin.Enable.Already"));
							else {
								plugin.getServer().getPluginManager().enablePlugin(plugin.getServer().getPluginManager().getPlugin(args[2]));
								io.send(sender, io.translate("Command.Plugin.Enable.Success").replaceAll("%plugin%", args[2]));
							}
						}
					}
				}
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/plugin enable [plugin]");
			else if (args.length > 2) io.sendManyArgs(sender, "/plugin enable [plugin]");
			else {
				if (permHandler.has(sender, "bm.plugin.enable")) {
					if (args.length == 1) {
						plugin.getServer().getPluginManager().enablePlugin(plugin);
						io.send(sender, io.translate("Command.Plugin.Enable.Success").replaceAll("%plugin%", "Bukkitmanager"));
					}else {
						if (plugin.getServer().getPluginManager().getPlugin(args[1]) == null) {
							io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
							io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
						}else {
							if ((plugin.getServer().getPluginManager().getPlugin(args[1])).isEnabled()) io.sendError(sender, io.translate("Command.Plugin.Enable.Already"));
							else {
								plugin.getServer().getPluginManager().enablePlugin(plugin.getServer().getPluginManager().getPlugin(args[1]));
								io.send(sender, io.translate("Command.Plugin.Enable.Success").replaceAll("%plugin%", args[1]));
							}
						}
					}
				}
			}
		}
	}
	
	private static String getPluginList() {
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
