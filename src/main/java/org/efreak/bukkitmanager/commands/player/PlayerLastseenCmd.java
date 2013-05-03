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

public class PlayerLastseenCmd extends Command {

	public PlayerLastseenCmd() {
		super("lastseen", "Player.Lastseen", "bm.player.lastseen", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player lastseen [player]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm player lastseen [player]");
		else {
			if (args.length == 1 && sender instanceof Player) {
				if (has(sender, "bm.player.lastseen.your")) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(((OfflinePlayer) sender).getLastPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR_OF_DAY) + ":";
					if (calendar.get(Calendar.MINUTE) < 10) time += "0";
					time += calendar.get(Calendar.MINUTE);
					io.send(sender, io.translate("Command.Player.Lastseen.Your").replaceAll("%lastseen_date%", date).replaceAll("%lastseen_time%", time));
				}
			}
			else if (args.length == 2) {
				if (has(sender, "bm.player.lastseen.other")) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(player.getLastPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR_OF_DAY) + ":";
					if (calendar.get(Calendar.MINUTE) < 10) time += "0";
					time += calendar.get(Calendar.MINUTE);
					if (player != null) io.send(sender, io.translate("Command.Player.Lastseen.Other").replaceAll("%player%", player.getName()).replaceAll("%lastseen_date%", date).replaceAll("%lastseen_time%", time));
					else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}
}
