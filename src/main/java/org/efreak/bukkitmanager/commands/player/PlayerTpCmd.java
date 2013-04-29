package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerTpCmd extends Command {

	public PlayerTpCmd() {
		super("tp", "Player.Tp", "bm.player.tp", Arrays.asList("(player1) [player2]"), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player tp (player1) [player2]");
		else if (args.length > 3) io.sendFewArgs(sender, "/bm player tp (player1) [player2]");
		else {
			if (args.length == 2 && sender instanceof Player) {
				if (has(sender, "bm.player.tp.self")) {
					BmPlayer player1 = new BmPlayer((OfflinePlayer) sender);
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
					if (offPlayer != null) {
						BmPlayer player2 = new BmPlayer(offPlayer);
						player1.teleport(player2.getLocation(), TeleportCause.COMMAND);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}else io.sendError(sender, "You don't have permission to do that");
			}else if (args.length == 3) {
				if (has(sender, "bm.player.tp.other")) {
					OfflinePlayer offPlayer1 = Bukkit.getOfflinePlayer(args[1]);
					if (offPlayer1 != null) {
						BmPlayer player1 = new BmPlayer(offPlayer1);
						OfflinePlayer offPlayer2 = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer2 != null) {
							BmPlayer player2 = new BmPlayer(offPlayer2);
							player1.teleport(player2.getLocation(), TeleportCause.COMMAND);
							io.send(player1.getPlayer(), "You were teleported to " + player2.getName() + " by " + sender.getName());
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer").replaceAll("%player%", args[1]));
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer").replaceAll("%player%", args[2]));
				}else io.sendError(sender, "You don't have permission to do that");
			}

		}
		return true;
	}
}
