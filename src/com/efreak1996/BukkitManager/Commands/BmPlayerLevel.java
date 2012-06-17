package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmDatabase;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmPlayerLevel {
	
	private static BmConfiguration config;
	private static BmIOManager io;
	private static BmPermissions permHandler;
	private static BmDatabase db;
	
	public void initialize() {
		config = new BmConfiguration();
		io = new BmIOManager();
		permHandler = new BmPermissions();
		db = new BmDatabase();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 3) io.sendFewArgs(sender, "/bm player level (get|set) [level] [player]");
			else if (args.length > 4) io.sendManyArgs(sender, "/bm player level (get|set) [level] [player]");
			else {
				if (args[2].equalsIgnoreCase("get")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.level.get.your")) io.send(sender, io.translate("Command.Player.Level.Get.Your").replaceAll("%level%", String.valueOf(((Player) sender).getLevel())));
					}
					else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.level.get.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) io.send(sender, io.translate("Command.Player.Level.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%level%", String.valueOf(player.getLevel())));
								else io.send(sender, io.translate("Command.Player.Level.Get.Other").replaceAll("%player%", args[3]).replaceAll("%level%", String.valueOf(db.getPlayer(args[3], "level"))));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("set")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.level.set.your")) {
							try {
								((Player) sender).setLevel(new Integer(args[3]));
								io.send(sender, io.translate("Command.Player.Level.Set.Your").replaceAll("%level%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Level.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.level.set.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								try {
									if (player != null) {
										player.setLevel(new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Level.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%level%", args[3]));
									}else {
										db.setPlayer(args[4], "level", new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Level.Set.Other").replaceAll("%player%", args[4]).replaceAll("%level%", args[3]));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Level.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/player level (get|set) [level] [player]");
			else if (args.length > 3) io.sendManyArgs(sender, "/player level (get|set) [level] [player]");
			else {
				if (args[1].equalsIgnoreCase("get")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.level.get.your")) io.send(sender, io.translate("Command.Player.Level.Get.Your").replaceAll("%level%", String.valueOf(((Player) sender).getLevel())));
					}
					else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.level.get.other")) {
							Player player = Bukkit.getPlayer(args[2]);
							if (player != null) {
								io.send(sender, io.translate("Command.Player.Level.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%level%", String.valueOf(player.getLevel())));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("set")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.level.set.your")) {
							try {
								((Player) sender).setLevel(new Integer(args[2]));
								io.send(sender, io.translate("Command.Player.Level.Set.Your").replaceAll("%level%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Level.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.level.set.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								try {
									player.setLevel(new Integer(args[2]));
									io.send(sender, io.translate("Command.Player.Level.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%level%", args[2]));
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Level.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}

		}
	}
}
