package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerChatCmd extends Command {

	public PlayerChatCmd() {
		super("chat", "Player.Chat", "bm.player.chat", Arrays.asList("(player)", "(msg)"), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (3 + length)) io.sendFewArgs(sender, "/bm player chat (player) (msg)");
		else {
			if (has(sender, "bm.player.chat")) {
				if (Bukkit.getPlayer(args[1 + length]) != null) {
					Player player = Bukkit.getPlayer(args[1 + length]);
					StringBuilder msg = new StringBuilder();
					for (int i = (2 + length); i < args.length; i++) msg.append(args[i] + " ");
					if (msg != null) player.chat(msg.substring(0, msg.lastIndexOf(" ")));
				}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
			}
		}
		return true;
	}
}
