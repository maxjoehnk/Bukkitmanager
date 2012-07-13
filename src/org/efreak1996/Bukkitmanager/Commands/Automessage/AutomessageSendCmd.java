package org.efreak1996.Bukkitmanager.Commands.Automessage;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutomessageReader;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutomessageSendCmd extends Command {

	private static AutomessageReader msgReader;
	
	public AutomessageSendCmd() {
		super("send", "Automessage.Send", Arrays.asList("(index)"), CommandCategory.AUTOMESSAGE);
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
