package com.efreak1996.BukkitManager.Commands;


import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmOutput;

public class BmPlugin {

	public static void initialize() {
		BmPluginConfig.initialize();
		BmPluginDisable.initialize();
		BmPluginEnable.initialize();
		BmPluginInfo.initialize();
		BmPluginInstall.initialize();
		BmPluginList.initialize();
		BmPluginRestart.initialize();
		BmPluginUpdate.initialize();
	}
	
	public static void shutdown() {}
	
	public static void player(Player p, String[] args)
	{
		if (args.length == 1) out.sendPlayer(p, ChatColor.RED + "Too few Arguments!");
		else {
			if (args[1].equalsIgnoreCase("list")) if (hasPerm(p, "bm.plugin.list")) BmPluginList.player(p, args);
			else if (args[1].equalsIgnoreCase("info")) if (hasPerm(p, "bm.plugin.info")) BmPluginInfo.player(p, args);
			else if (args[1].equalsIgnoreCase("enable")) if (hasPerm(p, "bm.plugin.enable")) BmPluginEnable.player(p, args);
			else if (args[1].equalsIgnoreCase("disable")) if (hasPerm(p, "bm.plugin.disable")) BmPluginDisable.player(p, args);
			else if (args[1].equalsIgnoreCase("restart")) if (hasPerm(p, "bm.plugin.restart")) BmPluginRestart.player(p, args);
			else if (args[1].equalsIgnoreCase("install")) if (hasPerm(p, "bm.plugin.install")) BmPluginInstall.player(p, args);
			else if (args[1].equalsIgnoreCase("update")) if (hasPerm(p, "bm.plugin.update")) BmPluginUpdate.player(p, args);
			else if (args[1].equalsIgnoreCase("config")) if (hasPerm(p, "bm.plugin.config")) BmPluginConfig.player(p, args);
			else out.sendPlayer(p, ChatColor.RED + " Invalid Arguments!");
		}
	}

	public static void console(ConsoleCommandSender c, String[] args)	{
		if (!(args.length == 1)) {
			if (args[1].equalsIgnoreCase("list")) BmPluginList.console(c, args);
			else if (args[1].equalsIgnoreCase("info")) BmPluginInfo.console(c, args);
			else if (args[1].equalsIgnoreCase("enable")) BmPluginEnable.console(c, args);
			else if (args[1].equalsIgnoreCase("disable")) BmPluginDisable.console(c, args);
			else if (args[1].equalsIgnoreCase("restart")) BmPluginRestart.console(c, args);
			else if (args[1].equalsIgnoreCase("install")) BmPluginInstall.console(c, args);
			else if (args[1].equalsIgnoreCase("update")) BmPluginUpdate.console(c, args);
			else if (args[1].equalsIgnoreCase("config")) BmPluginConfig.console(c, args);
		}else BmHelp.pluginHelpConsole(c);
	}
	
	public static boolean hasPerm(Player p, String perm) {
		if (!p.hasPermission(perm)){
			out.sendPlayer(true, p, ChatColor.RED + " You don't have Permission to do that!");
			return false;
		}else {
			return true;
		}
	}
	
	public static BmOutput out = new BmOutput();
}
