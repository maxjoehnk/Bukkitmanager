package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
				if (has(sender, "bm.player.firstseen.your")) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(((OfflinePlayer) sender).getFirstPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
					io.send(sender, io.translate("Command.Player.Firstseen.Your").replaceAll("%firstseen_date%", date).replaceAll("%firstseen_time%", time));
				}
			}else if (args.length == 2) {
				if (has(sender, "bm.player.firstseen.other")) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(player.getFirstPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
					if (player != null) io.send(sender, io.translate("Command.Player.Firstseen.Other").replaceAll("%player%", player.getName()).replaceAll("%firstseen_date%", date).replaceAll("%firstseen_time%", time));
					else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}
}
