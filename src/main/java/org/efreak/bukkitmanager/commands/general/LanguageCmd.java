package org.efreak.bukkitmanager.commands.general;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class LanguageCmd extends Command{
	
	public LanguageCmd() {
		super("language", "Language", "bm.language", Arrays.asList("(get|set)", "[language]"), CommandCategory.GENERAL);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm lang (set|get) [language]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm lang (set|get) [language]");
		else {
			if (args.length == 1 && args[0].equalsIgnoreCase("get")) {
				if (has(sender, "bm.language.get")) io.send(sender, io.translate("Command.Language.Get").replaceAll("%lang%", io.getTranslator().getLanguage()));
			}else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
				if (has(sender, "bm.language.set")) {
					if (io.getTranslator().setLanguage(args[1])) io.send(sender, io.translate("Command.Language.Set").replaceAll("%lang%", args[1]));
					else io.sendError(sender, io.translate("Command.Language.Error").replaceAll("%lang%", args[1]));
				}
			}
		}
		return true;
	}
}
