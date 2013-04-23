package org.efreak.bukkitmanager.commands.automessage;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.AutomessageReader;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageAddCmd extends Command {

	private static AutomessageReader msgReader;

	public AutomessageAddCmd() {
		super("add", "Automessage.Add", "bm.automessage.add", Arrays.asList("(msg)"), CommandCategory.AUTOMESSAGE);
		msgReader = new AutomessageReader();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm automessage add (msg)");
		else {
			if (has(sender, "bm.automessage.add")) {
				String msg = args[1];
				if (args.length > 2) {
					StringBuilder msgBuilder = new StringBuilder();
					for (int i = 1; i < args.length; i++) msgBuilder.append(args[i]);
					msg = msgBuilder.toString();
				}
				io.send(sender, io.translate("Command.Automessage.Add").replaceAll("%index%", String.valueOf(msgReader.addMessage(msg))));
			}
		}
		return true;
	}
}
