package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmLanguageCommandExecutor implements CommandExecutor {
	
	private static BmIOManager io;
	private static BmPermissions permHandler;

	public BmLanguageCommandExecutor() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/lang (set|get) [language]");
		else if (args.length > 2) io.sendManyArgs(sender, "/lang (set|get) [language]");
		else {
			if (args.length == 1 && args[0].equalsIgnoreCase("get")) {
				if (permHandler.has(sender, "bm.language.get")) io.send(sender, io.translate("Command.Language.Get").replaceAll("%lang%", io.getTranslator().getLanguage()));
			}else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
				if (permHandler.has(sender, "bm.language.set")) {
					if (io.getTranslator().setLanguage(args[1])) io.send(sender, io.translate("Command.Language.Set").replaceAll("%lang%", args[1]));
					else io.sendError(sender, io.translate("Command.Language.Error").replaceAll("%lang%", args[1]));
				}
			}
		}
		return true;
	}

}
