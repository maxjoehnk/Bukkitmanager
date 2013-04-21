package org.efreak.bukkitmanager.commands.automessage;


import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.AutomessageReader;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageGetCmd extends Command {

	private static AutomessageReader msgReader;
	
	public AutomessageGetCmd() {
		super("get", "Automessage.Get", "bm.automessage.get", Arrays.asList("(index)"), CommandCategory.AUTOMESSAGE);
		msgReader = new AutomessageReader();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm automessage get (index)");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm automessage get (index)");
		else {
			if (has(sender, "bm.automessage.get")) {
				io.send(sender, io.translate("Command.Automessage.Get").replaceAll("%index", args[1 + length]));
				io.send(sender, msgReader.getMessage(new Integer(args[1 + length])));
			}
		}
		return true;
	}
}
