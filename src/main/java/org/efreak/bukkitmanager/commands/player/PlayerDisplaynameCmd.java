package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerDisplaynameCmd extends Command {
	
	public PlayerDisplaynameCmd() {
		super("displayname", "Player.Displayname", "bm.player.displayname", Arrays.asList("(get|set|reset)", "[displayname]", "[player]"), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
		else if (args.length > 4) io.sendManyArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
		else {
			//get
			if (args[1].equalsIgnoreCase("get")) {
				if (args.length == 2 && sender instanceof Player) {
					if (has(sender, "bm.player.displayname.get.your")) io.send(sender, io.translate("Command.Player.Displayname.Get.Your").replaceAll("%displayname%", new BmPlayer((OfflinePlayer) sender).getDisplayName()));
				}else if (args.length == 3) {
					if (has(sender, "bm.player.displayname.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Displayname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getDisplayName()));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			//set 
			}else if (args[1].equalsIgnoreCase("set")) {
				if (args.length == 3 && sender instanceof Player) {
					if (has(sender, "bm.player.displayname.set.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						player.setDisplayName(args[2]);
						io.send(sender, io.translate("Command.Player.Displayname.Set.Your").replaceAll("%displayname%", player.getDisplayName()));
					}
				}else if (args.length == 4) {
					if (has(sender, "bm.player.displayname.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							player.setDisplayName(args[2]);
							io.send(sender, io.translate("Command.Player.Displayname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", args[2]));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			//reset
			}else if (args[1].equalsIgnoreCase("reset")) {
				if (args.length == 2 && sender instanceof Player) {
					if (has(sender, "bm.player.displayname.reset.your")) {
						BmPlayer player = new BmPlayer((Player) sender);
						player.resetDisplayName();
						io.send(sender, io.translate("Command.Player.Displayname.Reset.Your").replaceAll("%displayname%", player.getDisplayName()));
					}
				}else if (args.length == (3)) {
					if (has(sender, "bm.player.displayname.reset.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							player.resetDisplayName();
							io.send(sender, io.translate("Command.Player.Displayname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getDisplayName()));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));

					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
}
