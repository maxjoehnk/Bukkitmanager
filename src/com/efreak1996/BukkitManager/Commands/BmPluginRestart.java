package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BmPluginRestart {

	public static void initialize() {}
	public static void shutdown() {}
	
	public static void player(Player p, String[] args) {
		if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])) == null)
		{
			p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " This Plugin does not exists.");
			p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Available Plugins: " + getPluginList());
		}else
				if (args[2] == "Spout")
				{
					p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " You shouldn´t restart Spout!");
					return;
				}else
				{
					Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
					Bukkit.getServer().getPluginManager().enablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
				    p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Plugin: " + args[2] + " succesfully restarted.");
				}
	}
	public static void console(ConsoleCommandSender c, String[] args) {
		if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])) == null)
		{
			c.sendMessage("[BukkitManager] This Plugin does not exists.");
			c.sendMessage("[BukkitManager] Available Plugins: " + getPluginList());
		}else
				if (args[2] == "Spout")
				{
					c.sendMessage("[BukkitManager] You shouldn´t restart Spout!");
					return;
				}else
				{
					Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
					Bukkit.getServer().getPluginManager().enablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
				    c.sendMessage("[BukkitManager] Plugin: " + args[2] + " succesfully restarted.");
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
