package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Permissions;

public class LanguageCmd extends CommandHandler {

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		List<String> langs = new ArrayList<String>(io.getTranslator().getLanguages().keySet());
		for (String language : langs) {
			if (language.startsWith(args[0])) tabs.add(language);
		}
		return tabs;
	}

	@Command(label = "lang", alias = false, helpNode = "Language", usage = "lang [language]", permission = "bm.language")
	public boolean langCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm lang [language]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm lang [language]");
		else {
			if (args.length == 0) {
				if (Permissions.has(sender, "bm.language.get")) io.send(sender, io.translate("Command.Language.Get").replaceAll("%lang%", io.getTranslator().getLanguage()));
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.language.set")) {
					if (io.getTranslator().setLanguage(args[0])) io.send(sender, io.translate("Command.Language.Set").replaceAll("%lang%", args[0]));
					else io.sendError(sender, io.translate("Command.Language.Error").replaceAll("%lang%", args[0]));
				}
			}
		}
		return true;
	}
	
}
