package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmLanguage {

	private static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm lang (set|get) [language]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm lang (set|get) [language]");
		else {
			if (args.length == 2 && args[1].equalsIgnoreCase("get")) {
				if (permHandler.has(sender, "bm.language.get")) io.send(sender, io.translate("Command.Language.Get").replaceAll("%lang%", io.getTranslator().getLanguage()));
			}else if (args.length == 3 && args[1].equalsIgnoreCase("set")) {
				if (permHandler.has(sender, "bm.language.set")) {
					if (io.getTranslator().setLanguage(args[2])) io.send(sender, io.translate("Command.Language.Set").replaceAll("%lang%", args[2]));
					else io.sendError(sender, io.translate("Command.Language.Error").replaceAll("%lang%", args[2]));
				}
			}
		}
	}
}
