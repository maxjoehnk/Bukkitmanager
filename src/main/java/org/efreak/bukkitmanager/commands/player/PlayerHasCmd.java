package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerHasCmd extends Command {

	public PlayerHasCmd() {
		super("has", "Player.Has", "bm.player.has", Arrays.asList("(node) [player]"), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player has (node) [player]");
		else if (args.length > 3) io.sendFewArgs(sender, "/bm player tp (node) [player]");
		else {
			if (args.length == 2 && sender instanceof Player) {
				if (has(sender, "bm.player.has.your")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					if (player.hasPerm(args[1])) io.send(sender, io.translate("Command.Player.Has.Your.Has").replaceAll("%node%", args[1]));
					else io.send(sender, io.translate("Command.Player.Has.Your.Hasnt").replaceAll("%node%", args[1]));
				}
			}else if (args.length == 3) {
				if (has(sender, "bm.player.has.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						if (player.hasPerm(args[1])) io.send(sender, io.translate("Command.Player.Has.Other.Has").replaceAll("%player%", args[2]).replaceAll("%node%", args[1]));
						else io.send(sender, io.translate("Command.Player.Has.Other.Hasnt").replaceAll("%player%", args[2]).replaceAll("%node%", args[1]));
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer").replaceAll("%player%", args[2]));
				}
			}
		}
		return true;
	}
}
