package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPlayerLastseen {

	private static IOManager io;
	private static Permissions permHandler;

	public void initialize() {
		io = new IOManager();
		permHandler = new Permissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm player lastseen [player]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm player lastseen [player]");
			else {
				if (args.length == 2 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.lastseen.your")) io.send(sender, io.translate("Command.Player.Lastseen.Your").replaceAll("%lastseen%", String.valueOf(((Player) sender).getLastPlayed())));
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.player.lastseen.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) io.send(sender, io.translate("Command.Player.Lastseen.Other").replaceAll("%player%", offPlayer.getName()).replaceAll("%lastseen%", String.valueOf(offPlayer.getLastPlayed())));
						else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/player lastseen [player]");
			else if (args.length > 2) io.sendManyArgs(sender, "/player lastseen [player]");
			else {
				if (args.length == 1 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.lastseen.your")) io.send(sender, io.translate("Command.Player.Lastseen.Your").replaceAll("%lastseen%", String.valueOf(((Player) sender).getLastPlayed())));
				}
				else if (args.length == 2) {
					if (permHandler.has(sender, "bm.player.lastseen.other")) {
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
						if (player != null) io.send(sender, io.translate("Command.Player.Lastseen.Other").replaceAll("%player%", player.getName()).replaceAll("%lastseen%", String.valueOf(player.getLastPlayed())));
						else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
	}
}
