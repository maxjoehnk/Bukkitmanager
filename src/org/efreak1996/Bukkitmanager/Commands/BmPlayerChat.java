package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPlayerChat {
	
	private static IOManager io;
	private static Permissions permHandler;

	public void initialize() {
		io = new IOManager();
		permHandler = new Permissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 4) io.sendFewArgs(sender, "/bm player chat (player) (msg)");
			else {
				if (permHandler.has(sender, "bm.player.chat")) {
					if (Bukkit.getPlayer(args[2]) != null) {
						Player player = Bukkit.getPlayer(args[2]);
						String msg = null;
						for (int i = 3; i < args.length; i++) {
							if (msg != null) msg = msg + " " + args[i];
							else msg = args[i];
						}
						if (msg != null) player.chat(msg);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}
		}else {
			if (args.length < 3) io.sendFewArgs(sender, "/player chat (player) (msg)");
			else {
				if (permHandler.has(sender, "bm.player.chat")) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player player = Bukkit.getPlayer(args[1]);
						String msg = null;
						for (int i = 2; i < args.length; i++) {
							if (msg != null) msg = msg + " " + args[i];
							else msg = args[i];
						}
						if (msg != null) player.chat(msg);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}
		}
	}
}
