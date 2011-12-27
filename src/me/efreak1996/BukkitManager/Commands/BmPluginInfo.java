//This is only a sample for a general Command Class

package me.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class BmPluginInfo {

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
		Plugin plugin;
		PluginDescriptionFile pdfFile;
		if (args.length == 3)
		{
			plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
			pdfFile = plugin.getDescription();
			p.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------");
			p.sendMessage(ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName());
			p.sendMessage(ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion());
			p.sendMessage(ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors());
			if (pdfFile.getDescription() != null)
			{
			p.sendMessage(ChatColor.RED + "Description:");
			p.sendMessage(ChatColor.DARK_RED + pdfFile.getDescription());
			}
		}else if (args.length == 2) {
			plugin = Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager");
			pdfFile = plugin.getDescription();
			p.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------");
			p.sendMessage(ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName());
			p.sendMessage(ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion());
			p.sendMessage(ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors());
			if (pdfFile.getDescription() != null)
			{
			p.sendMessage(ChatColor.RED + "Description:");
			p.sendMessage(ChatColor.DARK_RED + pdfFile.getDescription());
			}
		}
		
		
	}
	public static void console(ConsoleCommandSender c, String[] args)
	{
		Plugin plugin;
		PluginDescriptionFile pdfFile;
		if (args.length == 3)
		{
			plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
			pdfFile = plugin.getDescription();
			c.sendMessage("-------------- Plugin Info --------------");
			c.sendMessage("Name:          " + pdfFile.getName());
			c.sendMessage("Version:       " + pdfFile.getVersion());
			c.sendMessage("Author(s):     " + pdfFile.getAuthors());
			if (pdfFile.getDescription() != null)
			{
			c.sendMessage("Description:");
			c.sendMessage(pdfFile.getDescription());
			}
		}else if (args.length == 2)
		{
			plugin = Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager");
			pdfFile = plugin.getDescription();
			c.sendMessage("-------------- Plugin Info --------------");
			c.sendMessage("Name:          " + pdfFile.getName());
			c.sendMessage("Version:       " + pdfFile.getVersion());
			c.sendMessage("Author(s):     " + pdfFile.getAuthors());
			if (pdfFile.getDescription() != null)
			{
			c.sendMessage("Description:");
			c.sendMessage(pdfFile.getDescription());
			}
		}
		
		
	}
}