package org.efreak1996.Bukkitmanager.Commands.Automessage;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutomessageStartCmd extends Command {

	private static ThreadManager func;
	
	public AutomessageStartCmd() {
		super("start", "Automessage.Start", "bm.automessage.start", new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm automessage start");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm automessage start");
		else {
			if (has(sender, "bm.automessage.start")) {
				func.startThread(ThreadType.AUTOMESSAGE);
				io.sendTranslation(sender, "Command.Automessage.Start");
			}
		}
		return true;
	}
}
