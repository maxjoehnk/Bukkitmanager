package org.efreak1996.Bukkitmanager.Commands.Player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.Database;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPlayerHide {

	private static Database db;
	private static IOManager io;
	private static Permissions permHandler;

	public void initialize() {
		db = new Database();
		io = new IOManager();
		permHandler = new Permissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm player hide [player]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm player hide [player]");
			else {
				if (args.length == 2) {
					if (permHandler.has(sender, "bm.player.hide.your")) {
						Player p = ((Player) sender);
						if (Boolean.parseBoolean(db.getPlayer(p, "hidden").toString())) io.sendError(sender, io.translate("Command.Player.Hide.Already.You"));
						else {
							Player[] player = Bukkit.getOnlinePlayers();
							for (int i = 0; i < player.length; i++) {
								if (!(player[i].hasPermission("bm.see"))) player[i].hidePlayer(p);
							}
							db.setPlayer(p, "hidden", true);
							io.send(sender, io.translate("Command.Player.Hide.You"));
							io.sendConsole(io.translate("Command.Player.Hide.Console.You").replaceAll("%player%", p.getName()));
						}
					}
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.player.hide.other")) {
						Player p = Bukkit.getPlayer(args[2]);
						if (Boolean.parseBoolean(db.getPlayer(p, "hidden").toString())) io.sendError(sender, io.translate("Command.Player.Hide.Already.Other").replaceAll("%player%", p.getName()));
						else {
							Player[] player = Bukkit.getOnlinePlayers();
							for (int i = 0; i < player.length; i++) {
								if (!(player[i].hasPermission("bm.see"))) player[i].hidePlayer(p);
							}
							db.setPlayer(p, "hidden", true);
							io.send(sender, io.translate("Command.Player.Hide.Other").replaceAll("%player%", p.getName()));
							io.send(p, io.translate("Command.Player.Hide.ByOther").replaceAll("%player%", sender.getName()));
							io.sendConsole(io.translate("Command.Player.Hide.Console.Other").replaceAll("%player%", p.getName()).replaceAll("%causer%", sender.getName()));
						}
					}
				}
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/player hide [player]");
			else if (args.length > 2) io.sendManyArgs(sender, "/player hide [player]");
			else {
				if (args.length == 1) {
					if (permHandler.has(sender, "bm.player.hide.your")) {
						Player p = ((Player) sender);
						if (Boolean.parseBoolean(db.getPlayer(p, "hidden").toString())) io.sendError(sender, io.translate("Command.Player.Hide.Already.You"));
						else {
							Player[] player = Bukkit.getOnlinePlayers();
							for (int i = 0; i < player.length; i++) {
								if (!(player[i].hasPermission("bm.see"))) player[i].hidePlayer(p);
							}
							db.setPlayer(p, "hidden", true);
							io.send(sender, io.translate("Command.Player.Hide.You"));
							io.sendConsole(io.translate("Command.Player.Hide.Console.You").replaceAll("%player%", p.getName()));
						}
					}
				}else if (args.length == 2) {
					if (permHandler.has(sender, "bm.player.hide.other")) {
						Player p = Bukkit.getPlayer(args[1]);
						if (Boolean.parseBoolean(db.getPlayer(p, "hidden").toString())) io.sendError(sender, io.translate("Command.Player.Hide.Already.Other").replaceAll("%player%", p.getName()));
						else {
							Player[] player = Bukkit.getOnlinePlayers();
							for (int i = 0; i < player.length; i++) {
								if (!(player[i].hasPermission("bm.see"))) player[i].hidePlayer(p);
							}
							db.setPlayer(p, "hidden", true);
							io.send(sender, io.translate("Command.Player.Hide.Other").replaceAll("%player%", p.getName()));
							io.send(p, io.translate("Command.Player.Hide.ByOther").replaceAll("%player%", sender.getName()));
							io.sendConsole(io.translate("Command.Player.Hide.Console.Other").replaceAll("%player%", p.getName()).replaceAll("%causer%", sender.getName()));
						}
					}
				}
			}
		}
	}
}
