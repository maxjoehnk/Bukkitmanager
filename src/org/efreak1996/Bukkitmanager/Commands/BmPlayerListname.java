package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.BmDatabase;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmPlayerListname {
	
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
			if (args.length < 3) io.sendFewArgs(sender, "/bm player listname (get|set|reset) [listname] [player]");
			else if (args.length > 5) io.sendManyArgs(sender, "/bm player listname (get|set|reset) [listname] [player]");
			else {
				if (args[2].equalsIgnoreCase("get")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.listname.get.your")) io.send(sender, io.translate("Command.Player.Listname.Get.Your").replaceAll("%listname%", ((Player) sender).getPlayerListName()));
					}
					else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.listname.get.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) io.send(sender, io.translate("Command.Player.Listname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", player.getPlayerListName()));
								else io.send(sender, io.translate("Command.Player.Listname.Get.Other").replaceAll("%player%", args[3]).replaceAll("%listname%", String.valueOf(db.getPlayer(args[3], "listname"))));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("set")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.listname.set.your")) {
							((Player) sender).setPlayerListName(args[3]);
							db.setPlayer(((Player) sender), "listname", args[3]);
							io.send(sender, io.translate("Command.Player.Listname.Set.Your").replaceAll("%listname%", args[3]));
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.listname.set.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) {
									player.setPlayerListName(args[3]);
									db.setPlayer(player, "listname", args[3]);
									io.send(sender, io.translate("Command.Player.Listname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", args[3]));
								}else {
									db.setPlayer(args[4], "listname", args[3]);
									io.send(sender, io.translate("Command.Player.Listname.Set.Other").replaceAll("%player%", args[4]).replaceAll("%listname%", args[3]));
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("reset")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.listname.reset.your")) {
							((Player) sender).setPlayerListName(((Player) sender).getName());
							db.setPlayer(((Player) sender), "listname", ((Player) sender).getName());
							io.send(sender, io.translate("Command.Player.Listname.Reset.Your").replaceAll("%listname%", ((Player) sender).getName()));
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.listname.reset.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) {
									player.setPlayerListName(player.getName());
									db.setPlayer(player, "listname", player.getName());
									io.send(sender, io.translate("Command.Player.Listname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", player.getName()));
								}else {
									db.setPlayer(args[3], "listname", args[3]);
									io.send(sender, io.translate("Command.Player.Listname.Reset.Other").replaceAll("%player%", args[3]).replaceAll("%listname%", args[3]));
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/player listname (get|set|reset) [listname] [player]");
			else if (args.length > 4) io.sendManyArgs(sender, "/player listname (get|set|reset) [listname] [player]");
			else {
				if (args[1].equalsIgnoreCase("get")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.listname.get.your")) io.send(sender, io.translate("Command.Player.Listname.Get.Your").replaceAll("%listname%", ((Player) sender).getPlayerListName()));
					}
					else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.listname.get.other")) {
							Player player = Bukkit.getPlayer(args[2]);
							if (player != null) io.send(sender, io.translate("Command.Player.Listname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", player.getPlayerListName()));
							else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("set")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.listname.set.your")) {
							((Player) sender).setPlayerListName(args[2]);
							db.setPlayer(((Player) sender), "listname", args[2]);
							io.send(sender, io.translate("Command.Player.Listname.Set.Your").replaceAll("%listname%", args[2]));
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.listname.set.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								player.setPlayerListName(args[2]);
								db.setPlayer(player, "listname", args[2]);
								io.send(sender, io.translate("Command.Player.Listname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", args[2]));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("reset")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.listname.reset.your")) {
							((Player) sender).setPlayerListName(((Player) sender).getName());
							db.setPlayer(((Player) sender), "listname", ((Player) sender).getName());
							io.send(sender, io.translate("Command.Player.Listname.Reset.Your").replaceAll("%listname%", ((Player) sender).getName()));
						}
					}else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.listname.reset.other")) {
							Player player = Bukkit.getPlayer(args[2]);
							if (player != null) {
								player.setPlayerListName(player.getName());
								db.setPlayer(player, "listname", player.getName());
								io.send(sender, io.translate("Command.Player.Listname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", player.getName()));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}
	}
}
