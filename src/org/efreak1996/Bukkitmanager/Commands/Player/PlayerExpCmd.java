package org.efreak1996.Bukkitmanager.Commands.Player;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.Database;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;
import org.efreak1996.Bukkitmanager.Util.ExperienceParser;

public class PlayerExpCmd extends Command {

	private static Database db;
	private static ExperienceParser expParser;

	public PlayerExpCmd() {
		super("exp", "Player.Exp", "bm.player.exp", Arrays.asList("(get|set|add)", "[exp]", "[player]"), CommandCategory.PLAYER);
		db = new Database();
		expParser = new ExperienceParser();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm player exp (get|set|add) [exp] [player]");
		else if (args.length > (4 + length)) io.sendManyArgs(sender, "/bm player exp (get|set|add) [exp] [player]");
		else {
			if (args[1 + length].equalsIgnoreCase("get")) {
				if (args.length == (2 + length) && sender instanceof Player) {
					if (has(sender, "bm.player.exp.get.your")) io.send(sender, io.translate("Command.Player.Exp.Get.Your").replaceAll("%exp%", String.valueOf(((Player) sender).getExp())));
				}
				else if (args.length == (3 + length)) {
					if (has(sender, "bm.player.exp.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2 + length]);
						if (offPlayer != null) {
							Player player = offPlayer.getPlayer();
							if (player != null) io.send(sender, io.translate("Command.Player.Exp.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", String.valueOf(player.getExp())));
							else io.send(sender, io.translate("Command.Player.Exp.Get.Other").replaceAll("%player%", args[2 + length]).replaceAll("%exp%", String.valueOf(db.getPlayer(offPlayer.getName(), "exp"))));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == (2 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1 + length].equalsIgnoreCase("set")) {
				if (args.length == (3 + length) && sender instanceof Player) {
					if (has(sender, "bm.player.exp.set.your")) {
						try {
							((Player) sender).setExp(new Integer(args[2 + length]));
							io.send(sender, io.translate("Command.Player.Exp.Set.Your").replaceAll("%exp%", args[2 + length]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Exp.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == (4 + length)) {
					if (has(sender, "bm.player.exp.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3 + length]);
						if (offPlayer != null) {
							try {
								Player player = offPlayer.getPlayer();
								if (player != null) {
									player.setExp(new Integer(args[2 + length]));
									io.send(sender, io.translate("Command.Player.Exp.Set.Other").replaceAll("%player%", args[3 + length]).replaceAll("%exp%", args[2 + length]));
								}else {
									db.setPlayer(offPlayer.getName(), "exp", new Integer(args[2 + length]));
									io.send(sender, io.translate("Command.Player.Exp.Set.Other").replaceAll("%player%", args[3 + length]).replaceAll("%exp%", args[2 + length]));
								}
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == (3 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1 + length].equalsIgnoreCase("add")) {
				if (args.length == (3 + length) && sender instanceof Player) {
					if (has(sender, "bm.player.exp.add.your")) {
						try {
							((Player) sender).giveExp(new Integer(args[2 + length]));
							io.send(sender, io.translate("Command.Player.Exp.Add.Your").replaceAll("%exp%", args[2 + length]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Exp.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == (4 + length)) {
					if (has(sender, "bm.player.exp.add.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3 + length]);
						if (offPlayer != null) {
							try {
								Player player = offPlayer.getPlayer();
								if (player != null) {									
									player.giveExp(new Integer(args[2 + length]));
									io.send(sender, io.translate("Command.Player.Exp.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", args[2 + length]));
								}else {
									db.setPlayer(offPlayer.getName(), "exp", ((Integer) db.getPlayer(offPlayer.getName(), "exp")) + Integer.valueOf(args[2 + length]));
									io.send(sender, io.translate("Command.Player.Exp.Add.Other").replaceAll("%player%", args[3 + length]).replaceAll("%exp%", args[2 + length]));
								}
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == (3 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
}
