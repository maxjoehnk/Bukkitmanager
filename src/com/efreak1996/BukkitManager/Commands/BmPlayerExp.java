package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmDatabase;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmExperienceParser;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmPlayerExp {
	
	private static BmConfiguration config;
	private static BmIOManager io;
	private static BmPermissions permHandler;
	private static BmDatabase db;
	private static BmExperienceParser expParser;

	public void initialize() {
		config = new BmConfiguration();
		io = new BmIOManager();
		permHandler = new BmPermissions();
		db = new BmDatabase();
		expParser = new BmExperienceParser();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm player exp (get|set|add) [exp] [player]");
			else if (args.length > 5) io.sendManyArgs(sender, "/bm player exp (get|set|add) [exp] [player]");
			else {
				if (args[2].equalsIgnoreCase("get")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.exp.get.your")) io.send(sender, io.translate("Command.Player.Exp.Get.Your").replaceAll("%exp%", String.valueOf(((Player) sender).getExp())));
					}
					else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.exp.get.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) io.send(sender, io.translate("Command.Player.Exp.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", String.valueOf(player.getExp())));
								else io.send(sender, io.translate("Command.Player.Exp.Get.Other").replaceAll("%player%", args[3]).replaceAll("%exp%", String.valueOf(db.getPlayer(offPlayer.getName(), "exp"))));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("set")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.exp.set.your")) {
							try {
								((Player) sender).setExp(new Integer(args[3]));
								io.send(sender, io.translate("Command.Player.Exp.Set.Your").replaceAll("%exp%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.exp.set.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								try {
									Player player = offPlayer.getPlayer();
									if (player != null) {
										player.setExp(new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Exp.Set.Other").replaceAll("%player%", args[4]).replaceAll("%exp%", args[3]));
									}else {
										db.setPlayer(offPlayer.getName(), "exp", new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Exp.Set.Other").replaceAll("%player%", args[4]).replaceAll("%exp%", args[3]));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Exp.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("add")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.exp.add.your")) {
							try {
								expParser.awardExperience(((Player) sender), new Integer(args[3]));
								//((Player) sender).giveExp(new Integer(args[3]));
								io.send(sender, io.translate("Command.Player.Exp.Add.Your").replaceAll("%exp%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.exp.add.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								try {
									Player player = offPlayer.getPlayer();
									if (player != null) {									
										expParser.awardExperience(player, new Integer(args[3]));
										//player.giveExp(new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Exp.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", args[3]));
									}else {
										db.setPlayer(offPlayer.getName(), "exp", ((Integer) db.getPlayer(offPlayer.getName(), "exp")) + Integer.valueOf(args[3]));
										io.send(sender, io.translate("Command.Player.Exp.Add.Other").replaceAll("%player%", args[4]).replaceAll("%exp%", args[3]));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Exp.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/player exp (get|set|add) [exp] [player]");
			else if (args.length > 4) io.sendManyArgs(sender, "/player exp (get|set|add) [exp] [player]");
			else {
				if (args[1].equalsIgnoreCase("get")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.exp.get.your")) io.send(sender, io.translate("Command.Player.Exp.Get.Your").replaceAll("%exp%", String.valueOf(((Player) sender).getExp())));
					}
					else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.exp.get.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) io.send(sender, io.translate("Command.Player.Exp.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", String.valueOf(player.getExp())));
								else io.send(sender, io.translate("Command.Player.Exp.Get.Other").replaceAll("%player%", args[2]).replaceAll("%exp%", String.valueOf(db.getPlayer(offPlayer.getName(), "exp"))));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("set")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.exp.set.your")) {
							try {
								((Player) sender).setExp(new Integer(args[2]));
								io.send(sender, io.translate("Command.Player.Exp.Set.Your").replaceAll("%exp%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.exp.set.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								try {
									Player player = offPlayer.getPlayer();
									if (player != null) {
										player.setExp(new Integer(args[2]));
										io.send(sender, io.translate("Command.Player.Exp.Set.Other").replaceAll("%player%", args[3]).replaceAll("%exp%", args[2]));
									}else {
										db.setPlayer(offPlayer.getName(), "exp", new Integer(args[2]));
										io.send(sender, io.translate("Command.Player.Exp.Set.Other").replaceAll("%player%", args[3]).replaceAll("%exp%", args[2]));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Exp.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("add")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.exp.add.your")) {
							try {
								((Player) sender).giveExp(new Integer(args[2]));
								io.send(sender, io.translate("Command.Player.Exp.Add.Your").replaceAll("%exp%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.exp.add.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								try {
									Player player = offPlayer.getPlayer();
									if (player != null) {									
										player.giveExp(new Integer(args[2]));
										io.send(sender, io.translate("Command.Player.Exp.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", args[2]));
									}else {
										db.setPlayer(offPlayer.getName(), "exp", ((Integer) db.getPlayer(offPlayer.getName(), "exp")) + Integer.valueOf(args[2]));
										io.send(sender, io.translate("Command.Player.Exp.Add.Other").replaceAll("%player%", args[3]).replaceAll("%exp%", args[2]));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Exp.Error"));
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
