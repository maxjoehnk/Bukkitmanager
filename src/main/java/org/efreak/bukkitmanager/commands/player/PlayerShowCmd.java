package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerShowCmd extends Command {

	public PlayerShowCmd() {
		super("show", "Player.Show", "bm.player.show", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player show [player]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm player show [player]");
		else {
			if (args.length == 1 && sender instanceof Player) {
				if (has(sender, "bm.player.show.your")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					if (player.isVisible()) io.sendError(sender, io.translate("Command.Player.Show.Already.You"));
					else {
						player.show();
						io.send(sender, io.translate("Command.Player.Show.You"));
						io.sendConsole(io.translate("Command.Player.Show.Console.You").replaceAll("%player%", player.getName()));
					}
				}
			}else if (args.length == 2) {
				if (has(sender, "bm.player.show.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						if (player.isVisible()) io.sendError(sender, io.translate("Command.Player.Show.Already.Other").replaceAll("%player%", player.getName()));
						else {
							player.show();
							io.send(sender, io.translate("Command.Player.Show.Other").replaceAll("%player%", player.getName()));
							io.send(player.getPlayer(), io.translate("Command.Player.Show.ByOther").replaceAll("%player%", sender.getName()));
							io.sendConsole(io.translate("Command.Player.Show.Console.Other").replaceAll("%player%", player.getName()).replaceAll("%causer%", sender.getName()));
						}
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}
		}
		return false;
	}
}
