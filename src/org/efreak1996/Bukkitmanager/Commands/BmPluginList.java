package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.BmPlugin;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmPluginList {

	private static Plugin plugin;
	private static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		plugin = BmPlugin.getPlugin();
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm plugin list [#]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm plugin list [#]");
			else {
				if (args.length == 2) {
					if (permHandler.has(sender, "bm.plugin.list.normal")) io.send(sender, "Plugins: " + getPluginList());
				}
				else if (args.length == 3) {
					if (permHandler.has(sender, "bm.plugin.list.detail")) showDetailedList(sender, Integer.parseInt(args[2]));
				}
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/plugin list [#]");
			else if (args.length > 2) io.sendManyArgs(sender, "/plugin list [#]");
			else {
				if (args.length == 1) {
					if (permHandler.has(sender, "bm.plugin.list.normal")) io.send(sender, "Plugins: " + getPluginList());
				}
				else if (args.length == 2) {
					if (permHandler.has(sender, "bm.plugin.list.detail")) showDetailedList(sender, Integer.parseInt(args[1]));
				}
			}
		}
	}
	
	private static void showDetailedList(CommandSender sender, int page) {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
		pluginList.append(ChatColor.YELLOW + "---------------------------" + ChatColor.WHITE + " PLUGINS " + ChatColor.YELLOW + "---------------------------");
		for (Plugin plugin : plugins) {
			pluginList.append("\n");			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getFullName());
		}
		io.send(sender, pluginList.toString(), false);
	}
		
	private static String getPluginList() {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = plugin.getServer().getPluginManager().getPlugins();
		
		for (Plugin plugin : plugins) {
			if (pluginList.length() > 0) {
				pluginList.append(ChatColor.WHITE);
				pluginList.append(", ");
			}
			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getFullName());
		}
		return pluginList.toString();
	}
}
