package org.efreak1996.Bukkitmanager.Commands.Automessage;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutomessageStopCmd extends Command {

	private static ThreadManager func;
	
	public AutomessageStopCmd() {
		super("stop", "Automessage.Stop", new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm automessage stop");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm automessage stop");
		else {
			if (has(sender, "bm.automessage.stop")) {
				func.stopThread(ThreadType.AUTOMESSAGE);
				io.sendTranslation(sender, "Command.Automessage.Stop");
			}
		}
		return true;
	}
}
