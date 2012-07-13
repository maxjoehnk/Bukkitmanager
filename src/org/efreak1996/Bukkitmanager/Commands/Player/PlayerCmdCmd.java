package org.efreak1996.Bukkitmanager.Commands.Player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PlayerCmdCmd extends Command {

	public PlayerCmdCmd() {
		super("cmd", "Player.Cmd", Arrays.asList("(player)", "(cmd)"), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (3 + length)) io.sendFewArgs(sender, "/bm player cmd (player) (cmd)");
		else {
			if (has(sender, "bm.player.cmd")) {
				if (Bukkit.getPlayer(args[1 + length]) != null) {
					Player player = Bukkit.getPlayer(args[1 + length]);
					String cmd = null;
					for (int i = (2 + length); i < args.length; i++) {
						if (cmd != null) cmd = cmd + " " + args[i];
						else cmd = args[i];
					}
					if (cmd != null) {
						if (player.performCommand(cmd)) io.sendTranslation(sender, "Command.Player.Cmd.Send");
						else io.sendError(sender, io.translate("Command.Player.Cmd.Error"));
					}
				}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
			}
		}
		return true;
	}
}
