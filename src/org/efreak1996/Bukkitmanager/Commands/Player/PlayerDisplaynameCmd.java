package org.efreak1996.Bukkitmanager.Commands.Player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak1996.Bukkitmanager.Database;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PlayerDisplaynameCmd extends Command {
	
	private static Database db;

	public PlayerDisplaynameCmd() {
		super("displayname", "Player.Displayname", Arrays.asList("(get|set|reset)", "[displayname]", "[player]"), CommandCategory.PLAYER);
		db = new Database();
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
		else if (args.length > (4 + length)) io.sendManyArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
		else {
			//get
			if (args[1].equalsIgnoreCase("get")) {
				if (args.length == (2 + length) && sender instanceof Player) {
					if (has(sender, "bm.player.displayname.get.your")) io.send(sender, io.translate("Command.Player.Displayname.Get.Your").replaceAll("%displayname%", ((Player) sender).getDisplayName()));
				}
				else if (args.length == (3 + length)) {
					if (has(sender, "bm.player.displayname.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2 + length]);
						if (offPlayer != null) {
							Player player = offPlayer.getPlayer();
							if (player != null) io.send(sender, io.translate("Command.Player.Displayname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getDisplayName()));
							else io.send(sender, io.translate("Command.Player.Displayname.Get.Other").replaceAll("%player%", args[2 + length]).replaceAll("%displayname%", String.valueOf(db.getPlayer(args[2 + length], "displayname"))));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == (2 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			//set 
			}else if (args[1 + length].equalsIgnoreCase("set")) {
				if (args.length == (3 + length) && sender instanceof Player) {
					if (has(sender, "bm.player.displayname.set.your")) {
						((Player) sender).setDisplayName(args[2 + length]);
						db.setPlayer(((Player) sender), "displayname", args[2 + length]);
						io.send(sender, io.translate("Command.Player.Displayname.Set.Your").replaceAll("%displayname%", args[2 + length]));
					}
				}else if (args.length == (4 + length)) {
					if (has(sender, "bm.player.displayname.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3 + length]);
						if (offPlayer != null) {
							Player player = offPlayer.getPlayer();
							if (player != null) {
								player.setDisplayName(args[2 + length]);
								db.setPlayer(player, "displayname", args[2 + length]);
								io.send(sender, io.translate("Command.Player.Displayname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", args[2 + length]));
							}else {
								db.setPlayer(args[3 + length], "displayname", args[2 + length]);
								io.send(sender, io.translate("Command.Player.Displayname.Set.Other").replaceAll("%player%", args[3 + length]).replaceAll("%displayname%", args[2 + length]));
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == (3 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			//reset
			}else if (args[1 + length].equalsIgnoreCase("reset")) {
				if (args.length == (2 + length) && sender instanceof Player) {
					if (has(sender, "bm.player.displayname.reset.your")) {
						((Player) sender).setDisplayName(((Player) sender).getName());
						db.setPlayer(((Player) sender), "displayname", ((Player) sender).getName());
						io.send(sender, io.translate("Command.Player.Displayname.Reset.Your").replaceAll("%displayname%", ((Player) sender).getName()));
					}
				}else if (args.length == (3 + length)) {
					if (has(sender, "bm.player.displayname.reset.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2 + length]);
						if (offPlayer != null) {
							Player player = offPlayer.getPlayer();
							if (player != null) {
								player.setDisplayName(player.getName());
								db.setPlayer(player, "displayname", player.getName());
								io.send(sender, io.translate("Command.Player.Displayname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getName()));
							}else {
								db.setPlayer(args[2 + length], "displayname", args[2 + length]);
								io.send(sender, io.translate("Command.Player.Displayname.Reset.Other").replaceAll("%player%", args[2 + length]).replaceAll("%displayname%", args[2 + length]));
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));

					}
				}else if (args.length == (2 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
}
