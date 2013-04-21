package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerHideCmd extends Command {

	public PlayerHideCmd() {
		super("hide", "Player.Hide", "bm.player.hide", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm player hide [player]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm player hide [player]");
		else {
			if (args.length == (1 + length) && sender instanceof Player) {
				if (has(sender, "bm.player.hide.your")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					if (!player.isVisible()) io.sendError(sender, io.translate("Command.Player.Hide.Already.You"));
					else {
						player.hide();
						io.send(sender, io.translate("Command.Player.Hide.You"));
						io.sendConsole(io.translate("Command.Player.Hide.Console.You").replaceAll("%player%", player.getName()));
					}
				}
			}else if (args.length == (2 + length)) {
				if (has(sender, "bm.player.hide.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1 + length]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						if (!player.isVisible()) io.sendError(sender, io.translate("Command.Player.Hide.Already.Other").replaceAll("%player%", player.getName()));
						else {
							player.hide();
							io.send(sender, io.translate("Command.Player.Hide.Other").replaceAll("%player%", player.getName()));
							io.send(player.getPlayer(), io.translate("Command.Player.Hide.ByOther").replaceAll("%player%", sender.getName()));
							io.sendConsole(io.translate("Command.Player.Hide.Console.Other").replaceAll("%player%", player.getName()).replaceAll("%causer%", sender.getName()));
						}
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}
		}
		return false;
	}
}
