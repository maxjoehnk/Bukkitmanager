package org.efreak1996.Bukkitmanager.Commands.Automessage;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutomessageReader;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutomessageAddCmd extends Command {

	private static AutomessageReader msgReader;

	public AutomessageAddCmd() {
		super("add", "Automessage.Add", Arrays.asList("(msg)"), CommandCategory.AUTOMESSAGE);
		msgReader = new AutomessageReader();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm automessage add (msg)");
		else {
			if (has(sender, "bm.automessage.add")) {
				String msg = args[1 + length];
				if (args.length > (2 + length)) {
					StringBuilder msgBuilder = new StringBuilder();
					for (int i = (1 + length); i < args.length; i++) msgBuilder.append(args[i]);
					msg = msgBuilder.toString();
				}
				io.send(sender, io.translate("Command.Automessage.Add").replaceAll("%index%", String.valueOf(msgReader.addMessage(msg))));
			}
		}
		return true;
	}
}
