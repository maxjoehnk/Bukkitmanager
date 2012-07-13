package org.efreak1996.Bukkitmanager.Commands.Player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPlayerLoad {

	private static IOManager io;
	private static Permissions permHandler;
	
	public void initialize() {
		io = new IOManager();
		permHandler = new Permissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm player load [player]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm player load [player]");
			else {
				if (args.length == 2 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.load")) {
						((Player) sender).loadData();
						io.send(sender, io.translate("Command.Player.Load").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 3 && Bukkit.getPlayer(args[2]) != null) {
					if (permHandler.has(sender, "bm.player.load.other")) {
						Player player = Bukkit.getPlayer(args[2]);
						player.loadData();
						io.send(sender, io.translate("Command.Player.Load").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/player load [player]");
			else if (args.length > 2) io.sendManyArgs(sender, "/player load [player]");
			else {
				if (args.length == 1 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.load")) {
						((Player) sender).loadData();
						io.send(sender, io.translate("Command.Player.Load").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 2 && Bukkit.getPlayer(args[1]) != null) {
					if (permHandler.has(sender, "bm.player.load.other")) {
						Player player = Bukkit.getPlayer(args[1]);
						player.loadData();
						io.send(sender, io.translate("Command.Player.Load").replaceAll("%player%", sender.getName()));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
			}
		}
	}
}
