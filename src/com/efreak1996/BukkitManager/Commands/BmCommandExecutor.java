package com.efreak1996.BukkitManager.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmOutput;

public class BmCommandExecutor implements CommandExecutor {

	public static void initialize() {}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String [] args) {
		p = null;
		c = null;
		if (sender instanceof Player) p = (Player) sender;
		if (sender instanceof ConsoleCommandSender) c = (ConsoleCommandSender) sender;
		if (p == c) return false;
		if (args[0].equalsIgnoreCase("bukkit")) {
			if (!(p == null)) BmBukkit.player(p, args);
			if (!(c == null)) BmBukkit.console(c, args);
		}else if (args[0].equalsIgnoreCase("plugin")) {
			if (!(p == null)) BmPlugin.player(p, args);
			if (!(c == null)) BmPlugin.console(c, args);
		}else if (args[0].equalsIgnoreCase("automessage")) {			
			  if (!(p == null)) if (hasPerm(p, "bm.automessage.admin")) BmAutomessage.player(p, args);
			  if (!(c == null)) BmAutomessage.console(c, args);
		}else if (args[0].equalsIgnoreCase("help")) {			
			  if (!(p == null)) if (hasPerm(p, "bm.help")) BmHelp.player(p, args);
			  if (!(c == null)) BmHelp.console(c, args);
		}else if (args[0].equalsIgnoreCase("gui")) {
			if (!(p == null)) out.sendPlayer(p, ChatColor.RED + "Not available yet");
			if (!(c == null)) out.sendConsole(ChatColor.RED + "Only for Spoutcraft");
			//BmGui.command(args, plugin, p);
		}else return false;
		return true;
	}
	
	public static boolean hasPerm(Player p, String perm) {
		if (!p.hasPermission(perm)){
			out.sendPlayer(p, ChatColor.RED + " You don't have Permission to do that!");
			return false;
		}else {
			return true;
		}
	}
	
	public static Player p;
	public static ConsoleCommandSender c;
	public static BmOutput out = new BmOutput();
}
