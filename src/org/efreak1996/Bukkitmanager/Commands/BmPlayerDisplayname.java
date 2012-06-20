package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.BmDatabase;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmPlayerDisplayname {
	
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
			if (args.length < 3) io.sendFewArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
			else if (args.length > 5) io.sendManyArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
			else {
				if (args[2].equalsIgnoreCase("get")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.displayname.get.your")) io.send(sender, io.translate("Command.Player.Displayname.Get.Your").replaceAll("%displayname%", ((Player) sender).getDisplayName()));
					}
					else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.displayname.get.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) io.send(sender, io.translate("Command.Player.Displayname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getDisplayName()));
								else io.send(sender, io.translate("Command.Player.Displayname.Get.Other").replaceAll("%player%", args[3]).replaceAll("%displayname%", String.valueOf(db.getPlayer(args[3], "displayname"))));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("set")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.displayname.set.your")) {
							((Player) sender).setDisplayName(args[3]);
							db.setPlayer(((Player) sender), "displayname", args[3]);
							io.send(sender, io.translate("Command.Player.Displayname.Set.Your").replaceAll("%displayname%", args[3]));
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.displayname.set.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) {
									player.setDisplayName(args[3]);
									db.setPlayer(player, "displayname", args[3]);
									io.send(sender, io.translate("Command.Player.Displayname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", args[3]));
								}else {
									db.setPlayer(args[4], "displayname", args[3]);
									io.send(sender, io.translate("Command.Player.Displayname.Set.Other").replaceAll("%player%", args[4]).replaceAll("%displayname%", args[3]));
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("reset")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.displayname.reset.your")) {
							((Player) sender).setDisplayName(((Player) sender).getName());
							db.setPlayer(((Player) sender), "displayname", ((Player) sender).getName());
							io.send(sender, io.translate("Command.Player.Displayname.Reset.Your").replaceAll("%displayname%", ((Player) sender).getName()));
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.displayname.reset.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) {
									player.setDisplayName(player.getName());
									db.setPlayer(player, "displayname", player.getName());
									io.send(sender, io.translate("Command.Player.Displayname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getName()));
								}else {
									db.setPlayer(args[3], "displayname", args[3]);
									io.send(sender, io.translate("Command.Player.Displayname.Reset.Other").replaceAll("%player%", args[3]).replaceAll("%displayname%", args[3]));
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/player displayname (get|set|reset) [displayname] [player]");
			else if (args.length > 4) io.sendManyArgs(sender, "/player displayname (get|set|reset) [displayname] [player]");
			else {
				if (args[1].equalsIgnoreCase("get")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.displayname.get.your")) io.send(sender, io.translate("Command.Player.Displayname.Get.Your").replaceAll("%displayname%", ((Player) sender).getDisplayName()));
					}
					else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.displayname.get.other")) {
							Player player = Bukkit.getPlayer(args[2]);
							if (player != null) io.send(sender, io.translate("Command.Player.Displayname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getDisplayName()));
							else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("set")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.displayname.set.your")) {
							((Player) sender).setDisplayName(args[2]);
							db.setPlayer(((Player) sender), "displayname", args[2]);
							io.send(sender, io.translate("Command.Player.Displayname.Set.Your").replaceAll("%displayname%", args[2]));
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.displayname.set.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								player.setDisplayName(args[2]);
								db.setPlayer(player, "displayname", args[2]);
								io.send(sender, io.translate("Command.Player.Displayname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", args[2]));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("reset")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.displayname.reset.your")) {
							((Player) sender).setDisplayName(((Player) sender).getName());
							db.setPlayer(((Player) sender), "displayname", ((Player) sender).getName());
							io.send(sender, io.translate("Command.Player.Displayname.Reset.Your").replaceAll("%displayname%", ((Player) sender).getName()));
						}
					}else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.displayname.reset.other")) {
							Player player = Bukkit.getPlayer(args[2]);
							if (player != null) {
								player.setDisplayName(player.getName());
								db.setPlayer(player, "displayname", player.getName());
								io.send(sender, io.translate("Command.Player.Displayname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getName()));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}
	}
}
