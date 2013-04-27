package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerInfoCmd extends Command {

	public PlayerInfoCmd() {
		super("info", "Player.Info", "bm.player.info", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player info [player]");
		else if (args.length > 2) io.sendFewArgs(sender, "/bm player info [player]");
		else {
			if (args.length == 1 && sender instanceof Player) {
				if (has(sender, "bm.player.info.own")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					io.sendHeader(sender, "PLAYER: " + sender.getName().toUpperCase());
					io.send(sender, "Health:     " + player.getHealth() + "/" + player.getMaxHealth(), false);
					io.send(sender, "Foodlevel: " + player.getFoodLevel() + "/20", false);
					io.send(sender, "Gamemode: " + player.getGameMode().toString(), false);
					io.send(sender, "Level:      " + player.getLevel(), false);
					io.send(sender, "World:      " + player.getWorld().getName(), false);
					io.send(sender, "Position:   {x=" + player.getX() + "|y=" + player.getY() + "|z=" + player.getZ() + "}", false);
				}else io.sendError(sender, "You don't have permission to do that");
			}else if (args.length == 2) {
				if (has(sender, "bm.player.info.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						io.sendHeader(sender, "PLAYER: " + sender.getName().toUpperCase());
						io.send(sender, "Health:    " + player.getHealth() + "/" + player.getMaxHealth(), false);
						io.send(sender, "Foodlevel: " + player.getFoodLevel() + "/20", false);
						io.send(sender, "Gamemode:  " + player.getGameMode().toString(), false);
						io.send(sender, "Level:     " + player.getLevel(), false);
						io.send(sender, "World:     " + player.getWorld().getName(), false);
						io.send(sender, "Position:  {x=" + player.getX() + "|y=" + player.getY() + "|z=" + player.getZ() + "}", false);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}else io.sendError(sender, "You don't have permission to do that");
			}

		}
		return true;
	}
}
