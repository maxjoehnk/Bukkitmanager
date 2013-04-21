package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerLastseenCmd extends Command {

	public PlayerLastseenCmd() {
		super("lastseen", "Player.Lastseen", "bm.player.lastseen", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm player lastseen [player]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm player lastseen [player]");
		else {
			if (args.length == (1 + length) && sender instanceof Player) {
				if (has(sender, "bm.player.lastseen.your")) io.send(sender, io.translate("Command.Player.Lastseen.Your").replaceAll("%lastseen%", String.valueOf(((Player) sender).getLastPlayed())));
			}
			else if (args.length == (2 + length)) {
				if (has(sender, "bm.player.lastseen.other")) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[1 + length]);
					if (player != null) io.send(sender, io.translate("Command.Player.Lastseen.Other").replaceAll("%player%", player.getName()).replaceAll("%lastseen%", String.valueOf(player.getLastPlayed())));
					else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else if (args.length == (1 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}
}
