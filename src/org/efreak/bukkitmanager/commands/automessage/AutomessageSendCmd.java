package org.efreak.bukkitmanager.commands.automessage;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.AutomessageReader;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageSendCmd extends Command {

	private static AutomessageReader msgReader;
	
	public AutomessageSendCmd() {
		super("send", "Automessage.Send", "bm.automessage.send", Arrays.asList("(index)"), CommandCategory.AUTOMESSAGE);
		msgReader = new AutomessageReader();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm automessage send (index)");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm automessage send (index)");
		else {
			if (has(sender, "bm.automessage.send")) msgReader.sendMessage(new Integer(args[1 + length]), true);
		}
		return true;
	}
}
