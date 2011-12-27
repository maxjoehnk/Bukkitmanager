package me.efreak1996.BukkitManager.Commands;

import me.efreak1996.BukkitManager.BmOutput;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BmBukkit {

	public static void initialize() {}
	
	public static void shutdown() {}
	
	public static void player(Player p, String[] args)
	{
		if (args[1].equalsIgnoreCase("info")) if (hasPerm(p, "bm.bukkit.info")) BmBukkitInfo.player(p, args);
		else if (args[1].equalsIgnoreCase("update")) if (hasPerm(p, "bm.bukkit.update")) BmBukkitUpdate.player(p, args);
		else if (args[1].equalsIgnoreCase("config")) if (hasPerm(p, "bm.bukkit.config")) BmBukkitConfig.player(p, args);
		else out.sendPlayer(p, ChatColor.RED + " Invalid Arguments!");
	}

	public static void console(ConsoleCommandSender c, String[] args)	{
		//if (!(args.length == 1)) {
			if (args[1].equalsIgnoreCase("info")) BmBukkitInfo.console(c, args);
			else if (args[1].equalsIgnoreCase("update")) BmBukkitUpdate.console(c, args);
			else if (args[1].equalsIgnoreCase("config")) BmBukkitConfig.console(c, args);
			else out.sendConsole(ChatColor.RED + "Invalid Arguments!");
		//}else BmHelp.pluginHelpConsole(c);
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
