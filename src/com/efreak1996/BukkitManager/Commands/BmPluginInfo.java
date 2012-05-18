package com.efreak1996.BukkitManager.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.BmPlugin;

public class BmPluginInfo {

	private static Plugin plugin;
	private static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		plugin = BmPlugin.getPlugin();
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	public static void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm plugin info [plugin]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm plugin info [plugin]");
			else {
				if (permHandler.has(sender, "bm.plugin.info")) {
					if (args.length == 3) {
						if (plugin.getServer().getPluginManager().getPlugin(args[2]) == null) {
							io.sendError(sender, "This Plugin does not exists.");
							return;
						}
						PluginDescriptionFile pdfFile = plugin.getServer().getPluginManager().getPlugin(args[2]).getDescription();
						io.send(sender, ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------", false);
						io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
						io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
						io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
						if (pdfFile.getDescription() != null) {
							io.send(sender, ChatColor.RED + "Description:", false);
							io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
						}
					} else if (args.length == 2) {
						PluginDescriptionFile pdfFile = plugin.getDescription();
						io.send(sender, ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------", false);
						io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
						io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
						io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
						io.send(sender, ChatColor.RED + "Description:", false);
						io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
					}
				}
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/plugin info [plugin]");
			else if (args.length > 2) io.sendManyArgs(sender, "/plugin info [plugin]");
			else {
				if (permHandler.has(sender, "bm.plugin.info")) {
					if (args.length == 2) {
						if (plugin.getServer().getPluginManager().getPlugin(args[1]) == null) {
							io.sendError(sender, "This Plugin does not exists.");
							return;
						}
						PluginDescriptionFile pdfFile = plugin.getServer().getPluginManager().getPlugin(args[1]).getDescription();
						io.send(sender, ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------", false);
						io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
						io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
						io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
						if (pdfFile.getDescription() != null) {
							io.send(sender, ChatColor.RED + "Description:", false);
							io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
						}
					} else if (args.length == 1) {
						PluginDescriptionFile pdfFile = plugin.getDescription();
						io.send(sender, ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------", false);
						io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
						io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
						io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
						io.send(sender, ChatColor.RED + "Description:", false);
						io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
					}
				}
			}
		}
	}
}