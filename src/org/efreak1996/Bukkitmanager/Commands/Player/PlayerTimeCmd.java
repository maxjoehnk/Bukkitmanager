package org.efreak1996.Bukkitmanager.Commands.Player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;
import org.efreak1996.Bukkitmanager.Util.TimeParser;


public class PlayerTimeCmd extends Command {

	private static TimeParser timeParser;
	
	public PlayerTimeCmd() {
		super("time", "Player.Time", "bm.player.time", Arrays.asList("(get|set|reset)", "[time]", "[player]"), CommandCategory.PLAYER);
		timeParser = new TimeParser();
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm player time (get|set|reset) [time] [player]");
		else if (args.length > (4 + length)) io.sendManyArgs(sender, "/player time (get|set|reset) [time] [player]");
		else {
			if (args[1].equalsIgnoreCase("get")) {
				if (args.length == 2 && sender instanceof Player) {
					if (has(sender, "bm.player.time.get.your")) io.send(sender, io.translate("Command.Player.Time.Get.Your").replaceAll("%time%", String.valueOf(((Player) sender).getPlayerTime())));
				}
				else if (args.length == 3) {
					if (has(sender, "bm.player.time.get.other")) {
						Player player = Bukkit.getPlayer(args[2]);
						if (player != null) {
							io.send(sender, io.translate("Command.Player.Time.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%time%", String.valueOf(player.getPlayerTime())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("set")) {
				if (args.length == 3 && sender instanceof Player) {
					if (has(sender, "bm.player.time.set.your")) {
						((Player) sender).setPlayerTime(Long.parseLong(String.valueOf(timeParser.matchTime(args[2]))), false);
						//((Player) sender).setPlayerTime(new Long(args[2]), false);
						io.send(sender, io.translate("Command.Player.Time.Set.Your").replaceAll("%time%", args[2]));
					}
				}else if (args.length == 4) {
					if (has(sender, "bm.player.time.set.other")) {
						Player player = Bukkit.getPlayer(args[3]);
						if (player != null) {
							player.setPlayerTime(Long.parseLong(String.valueOf(timeParser.matchTime(args[2]))), false);
							//player.setPlayerTime(new Long(args[2]), false);
							io.send(sender, io.translate("Command.Player.Time.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%time%", args[2]));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("reset")) {
				if (args.length == 2 && sender instanceof Player) {
					if (has(sender, "bm.player.time.reset.your")) {
						((Player) sender).resetPlayerTime();
						io.send(sender, io.translate("Command.Player.Time.Reset.Your").replaceAll("%time%", String.valueOf(((Player) sender).getWorld().getFullTime())));
					}
				}else if (args.length == 3) {
					if (has(sender, "bm.player.time.reset.other")) {
						Player player = Bukkit.getPlayer(args[2]);
						if (player != null) {
							player.resetPlayerTime();
							io.send(sender, io.translate("Command.Player.Time.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%time%", String.valueOf(player.getWorld().getFullTime())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
}
