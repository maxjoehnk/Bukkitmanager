package org.efreak.bukkitmanager.commands.general;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PasswordCmd extends Command {
	
	public PasswordCmd() {
		super("password", "Password", "bm.password", Arrays.asList("(set|get)", "[password]", "[player]"), CommandCategory.GENERAL);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm password (get|set) [password] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm password (get|set) [password] [player]");
		else {
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					if (has(sender, "bm.password.get.your")) io.send(sender, "Your Password is: " + db.queryString("SELECT 'remote_password' FROM player WHERE name='" + sender.getName() + "';", "remote_password"));
				}else if (args.length == 2) {
					if (has(sender, "bm.password.get.other")) {
						if (db.tableContains("player", "name", args[1])) io.send(sender, "The Password of " + args[1] + " is: " + db.queryString("SELECT 'remote_password' FROM player WHERE name='" + args[1] + "';", "remote_password"));
						else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (has(sender, "bm.password.set.your")) {
						db.update("UPDATE player SET remote_password='" + args[1] + "' WHERE name='" + sender.getName() + "';");
						io.send(sender, "Your Password was set to: " + args[1]);
					}
				}else if (args.length == 3) {
					if (has(sender, "bm.password.set.other")) {
						if (db.tableContains("player", "name", args[1])) {
							db.update("UPDATE player SET remote_password='" + args[1] + "' WHERE name='" + args[2] + "';");
							io.send(sender, "The Password of " + args[2] + " was set to: " + args[1]);
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
}
