package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerFirstseenCmd extends Command {

	public PlayerFirstseenCmd() {
		super("firstseen", "Player.Firstseen", "bm.player.firstseen", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/player firstseen [player]");
		else if (args.length > 2) io.sendManyArgs(sender, "/player firstseen [player]");
		else {
			if (args.length == 1 && sender instanceof Player) {
				if (has(sender, "bm.player.firstseen.your")) io.send(sender, io.translate("Command.Player.Firstseen.Your").replaceAll("%firstseen%", String.valueOf(((Player) sender).getFirstPlayed())));
			}else if (args.length == 2) {
				if (has(sender, "bm.player.firstseen.other")) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
					if (player != null) io.send(sender, io.translate("Command.Player.Firstseen.Other").replaceAll("%player%", player.getName()).replaceAll("%firstseen%", String.valueOf(player.getFirstPlayed())));
					else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}
}
