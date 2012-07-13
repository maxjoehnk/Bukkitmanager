package org.efreak1996.Bukkitmanager.Commands.Automessage;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutomessageReader;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutomessageRemoveCmd extends Command {

	private static AutomessageReader msgReader;
	
	public AutomessageRemoveCmd() {
		super("remove", "Automessage.Remove", Arrays.asList("(index)"), CommandCategory.AUTOMESSAGE);
		msgReader = new AutomessageReader();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm automessage remove (index)");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm automessage remove (index)");
		else {
			if (has(sender, "bm.automessage.remove")) {
				if (msgReader.remMessage(new Integer(args[1 + length])) != null) io.send(sender, io.translate("Command.Automessage.Remove").replaceAll("%index%", args[1 + length]));
				else io.sendError(sender, io.translate("Command.Automessage.Remove.NotFound"));
			}
		}
		return true;
	}
}
