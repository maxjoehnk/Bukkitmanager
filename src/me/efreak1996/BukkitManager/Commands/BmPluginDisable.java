//This is only a sample for a general Command Class

package me.efreak1996.BukkitManager.Commands;

import me.efreak1996.BukkitManager.BmOutput;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BmPluginDisable {

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
		if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])) == null)
		{
			p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " This Plugin does not exists.");
			p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Available Plugins: " + getPluginList());
		}else
			if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])).isEnabled() == false)
			{
				p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " This Plugin is already disabled.");
			}else
			{
				if (args[2] == "Spout")
				{
					p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " You shouldn´t disable Spout, if Bukkit is running!");
					return;
				}else
				{
					Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
				    p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Plugin: " + args[2] + " succesfully disabled.");
				}
			}
	}
	public static void console(ConsoleCommandSender c, String[] args)	{
		if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])) == null)
		{
			out.sendConsole(ChatColor.RED + "This Plugin does not exists.");
			out.sendConsole("Available Plugins: " + getPluginList());
		}else
			if ((Bukkit.getServer().getPluginManager().getPlugin(args[2])).isEnabled() == false)
			{
				out.sendConsole(ChatColor.RED + "This Plugin is already disabled.");
			}else {
				Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[2]));
				out.sendConsole(ChatColor.GREEN + "Plugin: " + args[2] + " succesfully disabled.");
			}
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
	
	public static BmOutput out = new BmOutput();
}
