package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerLocationCmd extends Command {

	public PlayerLocationCmd() {
		super("location", "Player.Location", "bm.player.location", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player location [player]");
		else if (args.length > 2) io.sendFewArgs(sender, "/bm player location [player]");
		else {
			if (args.length == 1 && sender instanceof Player) {
				if (has(sender, "bm.player.location.own")) {
					Location location = new BmPlayer((OfflinePlayer) sender).getLocation();
					io.sendHeader(sender, "LOCATION OF " + sender.getName().toUpperCase());
					io.send(sender, "World:    " + location.getWorld().getName(), false);
					io.send(sender, "Position: {x=" + location.getBlockX() + "|y=" + location.getBlockY() + "|z=" + location.getBlockZ() + "}", false);
					io.send(sender, "Chunk:   {x=" + location.getChunk().getX() + "|z=" + location.getChunk().getZ() + "}", false);
				}else io.sendError(sender, "You don't have permission to do that");
			}else if (args.length == 2) {
				if (has(sender, "bm.player.location.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
					if (offPlayer != null) {
						Location location = new BmPlayer(offPlayer).getLocation();
						io.sendHeader(sender, "LOCATION OF " + offPlayer.getName().toUpperCase());
						io.send(sender, "World:    " + location.getWorld(), false);
						io.send(sender, "Position: {x=" + location.getBlockX() + "|y=" + location.getBlockY() + "|z=" + location.getBlockZ() + "}", false);
						io.send(sender, "Chunk:   {x=" + location.getChunk().getX() + "|z=" + location.getChunk().getZ() + "}", false);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}else io.sendError(sender, "You don't have permission to do that");
			}
		}
		return true;
	}
}
