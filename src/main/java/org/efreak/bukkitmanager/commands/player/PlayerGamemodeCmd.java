package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerGamemodeCmd extends Command {
	
	public PlayerGamemodeCmd() {
		super("gamemode", "Player.Gamemode", "bm.player.gamemode", Arrays.asList("(gamemode)", "[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player gamemode (gamemode) [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm player gamemode (gamemode) [player]");
		else {
			int gamemodeInt = -1;
			GameMode gamemode;
			try {
				gamemodeInt = new Integer(args[1]);
				if (GameMode.getByValue(gamemodeInt) != null) gamemode = GameMode.getByValue(gamemodeInt);
				else {
					io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
					return true;
				}
			}catch (NumberFormatException e) {
				if (GameMode.valueOf(args[1].toUpperCase()) == null) {
					io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
					return true;
				}else gamemode = GameMode.valueOf(args[1].toUpperCase());
			}
			if (args.length == 2 && sender instanceof Player) {
				if (has(sender, "bm.player.gamemode.your")) { 
					new BmPlayer((OfflinePlayer) sender).setGameMode(gamemode);
				}
			}else if (args.length == 3) {
				if (has(sender, "bm.player.gamemode.other")) { 
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						player.setGameMode(gamemode);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}	
}
