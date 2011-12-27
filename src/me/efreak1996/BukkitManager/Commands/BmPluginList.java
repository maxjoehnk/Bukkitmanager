package me.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BmPluginList {

	public static void initialize()
	{
		//here are the events for starting of bukkit
	}
	public static void shutdown()
	{
		//here are the events for stopping bukkit
	}
	public static void player(Player p, String[] args)
	{
		p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Plugins: " + getPluginList());
	}
	public static void console(ConsoleCommandSender c, String[] args)
	{
		c.sendMessage("[BukkitManager] Plugins: " + getPluginList());
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
