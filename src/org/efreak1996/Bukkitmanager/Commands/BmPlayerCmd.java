package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPlayerCmd {
	
	private static IOManager io;
	private static Permissions permHandler;

	public void initialize() {
		io = new IOManager();
		permHandler = new Permissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 4) io.sendFewArgs(sender, "/bm player cmd (player) (cmd)");
			else {
				if (permHandler.has(sender, "bm.player.cmd")) {
					if (Bukkit.getPlayer(args[2]) != null) {
						Player player = Bukkit.getPlayer(args[2]);
						String cmd = null;
						for (int i = 3; i < args.length; i++) {
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
		}else {
			if (args.length < 3) io.sendFewArgs(sender, "/player cmd (player) (cmd)");
			else {
				if (permHandler.has(sender, "bm.player.cmd")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player player = Bukkit.getPlayer(args[1]);
						String cmd = null;
						for (int i = 2; i < args.length; i++) {
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
		}
	}
}
