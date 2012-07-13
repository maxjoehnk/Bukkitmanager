package org.efreak1996.Bukkitmanager.Commands.General;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class LanguageCmd extends Command{
	
	public LanguageCmd() {
		super("language", "Language", Arrays.asList("(get|set)", "[language]"), CommandCategory.GENERAL);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm lang (set|get) [language]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm lang (set|get) [language]");
		else {
			if (args.length == (1 + length) && args[0 + length].equalsIgnoreCase("get")) {
				if (has(sender, "bm.language.get")) io.send(sender, io.translate("Command.Language.Get").replaceAll("%lang%", io.getTranslator().getLanguage()));
			}else if (args.length == (2 + length) && args[0 + length].equalsIgnoreCase("set")) {
				if (has(sender, "bm.language.set")) {
					if (io.getTranslator().setLanguage(args[1 + length])) io.send(sender, io.translate("Command.Language.Set").replaceAll("%lang%", args[1 + length]));
					else io.sendError(sender, io.translate("Command.Language.Error").replaceAll("%lang%", args[1 + length]));
				}
			}
		}
		return false;
	}
}
