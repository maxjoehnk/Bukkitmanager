package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmDatabase;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmPlayerHide {

	private static BmDatabase db;
	private static BmIOManager io;
	private static BmPermissions permHandler;

	public void initialize() {
		db = new BmDatabase();
		io = new BmIOManager();
		permHandler = new BmPermissions();
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
						if (Boolean.parseBoolean(db.getPlayer(p, "hidden").toString())) io.sendError(sender, io.translate("Command.Player.Hide.Already.Your"));
						else {
							Player[] player = Bukkit.getOnlinePlayers();
							for (int i = 0; i < player.length; i++) {
								if (!(player[i].hasPermission("bm.see"))) player[i].hidePlayer(p);
							}
							db.setPlayer(p, "hidden", true);
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
						if (Boolean.parseBoolean(db.getPlayer(p, "hidden").toString())) io.sendError(sender, io.translate("Command.Player.Hide.Already.Your"));
						else {
							Player[] player = Bukkit.getOnlinePlayers();
							for (int i = 0; i < player.length; i++) {
								if (!(player[i].hasPermission("bm.see"))) player[i].hidePlayer(p);
							}
							db.setPlayer(p, "hidden", true);
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
						}
					}
				}
			}
		}
	}
}
