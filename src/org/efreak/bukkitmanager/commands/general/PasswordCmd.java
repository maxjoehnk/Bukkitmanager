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
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm password (get|set) [password] [player]");
		else if (args.length > (3 + length)) io.sendManyArgs(sender, "/bm password (get|set) [password] [player]");
		else {
			if (args[0 + length].equalsIgnoreCase("get")) {
				if (args.length == (1 + length) && sender instanceof Player) {
					if (has(sender, "bm.password.get.your")) io.send(sender, "Your Password is: " + db.queryString("SELECT 'remote_password' FROM player WHERE name='" + sender.getName() + "';", "remote_password"));
				}else if (args.length == (2 + length)) {
					if (has(sender, "bm.password.get.other")) {
						if (db.tableContains("player", "name", args[1 + length])) io.send(sender, "The Password of " + args[1 + length] + " is: " + db.queryString("SELECT 'remote_password' FROM player WHERE name='" + args[1 + length] + "';", "remote_password"));
						else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == (1 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0 + length].equalsIgnoreCase("set")) {
				if (args.length == (2 + length) && sender instanceof Player) {
					if (has(sender, "bm.password.set.your")) {
						db.update("UPDATE player SET remote_password='" + args[1 + length] + "' WHERE name='" + sender.getName() + "';");
						io.send(sender, "Your Password was set to: " + args[1 + length]);
					}
				}else if (args.length == (3  + length)) {
					if (has(sender, "bm.password.set.other")) {
						if (db.tableContains("player", "name", args[1 + length])) {
							db.update("UPDATE player SET remote_password='" + args[1 + length] + "' WHERE name='" + args[2 + length] + "';");
							io.send(sender, "The Password of " + args[2 + length] + " was set to: " + args[1 + length]);
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == (2 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
}
