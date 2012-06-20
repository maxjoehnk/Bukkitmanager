package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.Database;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPassword {

	private static IOManager io;
	private static Database db;
	private static Permissions permHandler;
	
	public void initialize() {
		io = new IOManager();
		db = new Database();
		permHandler = new Permissions();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm password (get|set) [password] [player]");
		else if (args.length > 4) io.sendManyArgs(sender, "/bm password (get|set) [password] [player]");
		else {
			if (args[1].equalsIgnoreCase("get")) {
				if (args.length == 2 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.password.get.your")) io.send(sender, "Your Password is: " + db.getRemote(sender.getName(), "password"));
				}
				else if (args.length == 3) {
					if (permHandler.has(sender, "bm.password.get.other")) {
						if (db.getRemote(args[2])) io.send(sender, "The Password of " + args[2] + " is: " + db.getRemote(args[2], "password"));
						else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("set")) {
				if (args.length == 3 && sender instanceof Player) {
					if (permHandler.has(sender, "bm.password.set.your")) {
						db.setRemote(sender.getName(), "password", args[2]);
						io.send(sender, "Your Password was set to: " + args[2]);
					}
				}else if (args.length == 4) {
					if (permHandler.has(sender, "bm.password.set.other")) {
						if (db.getRemote(args[3])) {
							db.setRemote(args[3], "password", args[2]);
							io.send(sender, "The Password of " + args[3] + " was set to: " + db.getRemote(args[3], "password"));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
	}
}
