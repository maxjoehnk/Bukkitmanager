package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerLevelCmd extends Command {
	
	public PlayerLevelCmd() {
		super("level", "Player.Level", "bm.player.level", Arrays.asList("(get|set)", "[level]", "[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player level (get|set) [level] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm player level (get|set) [level] [player]");
		else {
			if (args[1].equalsIgnoreCase("get")) {
				if (args.length == 2 && sender instanceof Player) {
					if (has(sender, "bm.player.level.get.your")) io.send(sender, io.translate("Command.Player.Level.Get.Your").replaceAll("%level%", String.valueOf(new BmPlayer((OfflinePlayer) sender).getLevel())));
				}
				else if (args.length == 3) {
					if (has(sender, "bm.player.level.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Level.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%level%", String.valueOf(player.getLevel())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("set")) {
				if (args.length == 3 && sender instanceof Player) {
					if (has(sender, "bm.player.level.set.your")) {
						try {
							new BmPlayer((OfflinePlayer) sender).setLevel(new Integer(args[2]));
							io.send(sender, io.translate("Command.Player.Level.Set.Your").replaceAll("%level%", args[2]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Level.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 4) {
					if (has(sender, "bm.player.level.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
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
		return true;
	}
}
