package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmOutput;


public class BmPluginList {

	private static BmOutput out = new BmOutput();
	
	public static void initialize() {}
	public static void shutdown() {}
	
	public static void player(Player p, String[] args) {
		if (args.length == 2) p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Plugins: " + getPluginList());
		else if (args.length == 3) showDetailedList((CommandSender) p, Integer.parseInt(args[2]));
		else out.sendPlayerManyArgs(p, "/bm plugin list [#]");		
	}
	public static void console(ConsoleCommandSender c, String[] args) {
		if (args.length == 2) c.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Plugins: " + getPluginList());
		else if (args.length == 3) showDetailedList((CommandSender) c, Integer.parseInt(args[2]));
		//else out.sendConsoleManyArgs(c, "bm plugin list [#]");		
	}
	
	private static void showDetailedList(CommandSender sender, int page) {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
		pluginList.append(ChatColor.YELLOW + "---------------------------" + ChatColor.WHITE + " PLUGINS " + ChatColor.YELLOW + "---------------------------");
		for (Plugin plugin : plugins) {
			pluginList.append("\n");			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getFullName());
		}
		sender.sendMessage(pluginList.toString());
	}
		
	
	private static String getPluginList()
	{
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
