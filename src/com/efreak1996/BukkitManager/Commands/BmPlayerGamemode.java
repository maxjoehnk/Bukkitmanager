package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmDatabase;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmPlayerGamemode {
	
	private static BmPermissions permHandler;
	private static BmIOManager io;
	private static BmDatabase db;

	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
		db = new BmDatabase();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 3) io.sendFewArgs(sender, "/bm player gamemode (gamemode) [player]");
			else if (args.length > 4) io.sendManyArgs(sender, "/bm player gamemode (gamemode) [player]");
			else {
				
				int gamemode = -1;
				try {
					gamemode = new Integer(args[2]);
				}catch (NumberFormatException e) {
					io.sendError(sender, io.translate("Command.Player.Gamemode.Error"));
					return;
				}
				if (args.length == 3 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.gamemode.your")) { 
						if (gamemode != 0 && gamemode != 1) io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
						else ((Player) sender).setGameMode(GameMode.getByValue(gamemode));
					}
				}else if (args.length == 4) {
					if (permHandler.has(sender, "bm.player.gamemode.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
						if (offPlayer != null) {
							Player player = offPlayer.getPlayer();
							if (gamemode != 0 && gamemode != 1) io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
							else {
								if (player != null) player.setGameMode(GameMode.getByValue(gamemode));
								else db.setPlayer(args[3], "gamemode", GameMode.getByValue(gamemode));
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/player gamemode (gamemode) [player]");
			else if (args.length > 3) io.sendManyArgs(sender, "/player gamemode (gamemode) [player]");
			else {
				int gamemode = -1;
				try {
					gamemode = new Integer(args[1]);
				}catch (NumberFormatException e) {
					io.sendError(sender, io.translate("Command.Player.Gamemode.Error"));
					return;
				}
				if (args.length == 2 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.player.gamemode.your")) { 
						if (gamemode != 0 && gamemode != 1) io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
						else ((Player) sender).setGameMode(GameMode.getByValue(gamemode));
					}
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.player.gamemode.other")) { 
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							Player player = offPlayer.getPlayer();
							if (gamemode != 0 && gamemode != 1) io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
							else {
								if (player != null) player.setGameMode(GameMode.getByValue(gamemode));
								else db.setPlayer(args[2], "gamemode", GameMode.getByValue(gamemode));
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
	}	
}
