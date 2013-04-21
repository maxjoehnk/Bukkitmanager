package org.efreak.bukkitmanager.commands.automessage;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageRestartCmd extends Command {

	private static ThreadManager func;
	
	public AutomessageRestartCmd() {
		super("restart", "Automessage.Restart", "bm.automessage.restart", new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm automessage restart");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm automessage restart");
		else {
			if (has(sender, "bm.automessage.restart")) {
				io.sendTranslation(sender, "Command.Automessage.Restart");
				func.stopThread(ThreadType.AUTOMESSAGE);
				func.startThread(ThreadType.AUTOMESSAGE);
			}
		}
		return true;
	}
}
