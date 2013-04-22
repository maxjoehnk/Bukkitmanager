package org.efreak.bukkitmanager.commands.automessage;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageStopCmd extends Command {

	private static ThreadManager func;
	
	public AutomessageStopCmd() {
		super("stop", "Automessage.Stop", "bm.automessage.stop", new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
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