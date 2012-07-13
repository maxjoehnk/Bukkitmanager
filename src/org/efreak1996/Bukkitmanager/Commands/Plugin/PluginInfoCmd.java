package org.efreak1996.Bukkitmanager.Commands.Plugin;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PluginInfoCmd extends Command {
	
	public PluginInfoCmd() {
		super("info", "Plugin.Info", Arrays.asList("[plugin]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm plugin info [plugin]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm plugin info [plugin]");
		else {
			if (has(sender, "bm.plugin.info")) {
				if (args.length == (2 + length)) {
					if (Bukkitmanager.getPluginManager().getPlugin(args[1 + length]) == null) {
						io.sendError(sender, "This Plugin does not exists.");
						return true;
					}
					PluginDescriptionFile pdfFile = Bukkitmanager.getPluginManager().getPlugin(args[1 + length]).getDescription();
					io.send(sender, ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------", false);
					io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
					io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
					io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
					if (pdfFile.getDescription() != null) {
						io.send(sender, ChatColor.RED + "Description:", false);
						io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
					}
				} else if (args.length == (1 + length)) {
					PluginDescriptionFile pdfFile = Bukkitmanager.getInstance().getDescription();
					io.send(sender, ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " Plugin Info " + ChatColor.YELLOW + "--------------", false);
					io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
					io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
					io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
					io.send(sender, ChatColor.RED + "Description:", false);
					io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
				}
			}
		}
		return true;
	}
}