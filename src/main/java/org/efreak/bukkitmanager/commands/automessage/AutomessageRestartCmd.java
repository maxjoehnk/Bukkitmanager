package org.efreak.bukkitmanager.commands.automessage;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageRestartCmd extends Command {
	
	public AutomessageRestartCmd() {
		super("restart", "Automessage.Restart", "bm.automessage.restart", new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm automessage restart");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm automessage restart");
		else {
			if (has(sender, "bm.automessage.restart")) {
				io.sendTranslation(sender, "Command.Automessage.Restart");
				ThreadManager.stopThread(ThreadType.AUTOMESSAGE);
				ThreadManager.startThread(ThreadType.AUTOMESSAGE);
			}
		}
		return true;
	}
}
