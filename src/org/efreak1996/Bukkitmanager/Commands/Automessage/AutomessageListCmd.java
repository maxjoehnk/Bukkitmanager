package org.efreak1996.Bukkitmanager.Commands.Automessage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutomessageReader;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutomessageListCmd extends Command {

	private static AutomessageReader msgReader;
	
	public AutomessageListCmd() {
		super("list", "Automessage.List", "bm.automessage.list", new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
		msgReader = new AutomessageReader();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm automessage list");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm automessage start");
		else {
			if (has(sender, "bm.automessage.list")) {
				List<String> list = msgReader.listMessages();
				for (int i = 0; i < list.size(); i++) 
					io.send(sender, list.get(i), false);
			}
		}
		return true;
	}
}
