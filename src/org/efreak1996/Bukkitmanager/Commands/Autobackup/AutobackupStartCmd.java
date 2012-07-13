package org.efreak1996.Bukkitmanager.Commands.Autobackup;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutobackupStartCmd extends Command {

	private static ThreadManager func;
	
	public AutobackupStartCmd() {
		super("start", "Autobackup.Start", new ArrayList<String>(), CommandCategory.AUTOBACKUP);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autobackup start");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm autobackup start");
		else {
			if (has(sender, "bm.autobackup.start")) {
				func.startThread(ThreadType.AUTOBACKUP);
				io.sendTranslation(sender, "Command.Autobackup.Start");
			}
		}
		return true;
	}
}
