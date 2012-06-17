package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmPlayerSave {

	public static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm player save [player]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm player save [player]");
			else {
				if (args.length == 2 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.save")) {
						((Player) sender).saveData();
						io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 3 && Bukkit.getPlayer(args[2]) != null) {
					if (permHandler.has(sender, "bm.player.save.other")) {
						Player player = Bukkit.getPlayer(args[2]);
						player.saveData();
						io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/player save [player]");
			else if (args.length > 2) io.sendManyArgs(sender, "/player save [player]");
			else {
				if (args.length == 1 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.save")) {
						((Player) sender).saveData();
						io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 2 && Bukkit.getPlayer(args[1]) != null) {
					if (permHandler.has(sender, "bm.player.save.other")) {
						Player player = Bukkit.getPlayer(args[1]);
						player.saveData();
						io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
			}
		}
	}
}
