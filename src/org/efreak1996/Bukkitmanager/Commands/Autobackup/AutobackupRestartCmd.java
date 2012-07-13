package org.efreak1996.Bukkitmanager.Commands.Autobackup;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutobackupRestartCmd extends Command {

	private static ThreadManager func;
	
	public AutobackupRestartCmd() {
		super("restart", "Autobackup.Restart", new ArrayList<String>(), CommandCategory.AUTOBACKUP);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autobackup restart");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm autobackup restart");
		else {
			if (has(sender, "bm.autobackup.restart")) {
				io.sendTranslation(sender, "Command.Autobackup.Restart");
				func.stopThread(ThreadType.AUTOBACKUP);
				func.startThread(ThreadType.AUTOBACKUP);
			}
		}
		return true;
	}
}
