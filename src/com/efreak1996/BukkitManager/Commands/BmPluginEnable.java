//This is only a sample for a general Command Class

package com.efreak1996.BukkitManager.Commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmOutput;

public class BmPluginEnable {

	public static void initialize() {}
	public static void shutdown() {}
	public static void player(Player p, String[] args) {
		if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])) == null) {
			p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " This Plugin does not exists.");
			p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Available Plugins: " + getPluginList());
		}else
			if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])).isEnabled() == true)
				p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " This Plugin is already enabled.");
			else {
				if (args[2] == "Spout") {
					p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " You shouldn´t enable Spout, if Bukkit is running!");
					return;
				}else {
					Bukkit.getServer().getPluginManager().enablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
					p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Plugin: " + args[2] + " succesfully enabled.");
				}
			}
		}
	public static void console(ConsoleCommandSender c, String[] args) {
		if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])) == null) {
			out.sendConsole(ChatColor.RED + "This Plugin does not exists.");
			out.sendConsole("Available Plugins: " + getPluginList());
		}else {
			if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])).isEnabled() == true) {
				out.sendConsole(ChatColor.RED + "This Plugin is already enabled.");
			}else {
				Bukkit.getServer().getPluginManager().enablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
				out.sendConsole(ChatColor.GREEN + "Plugin: " + args[2] + " succesfully enabled.");
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
	
	public static BmOutput out = new BmOutput();
}
