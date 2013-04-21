package org.efreak.bukkitmanager.commands.autobackup;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutobackupStopCmd extends Command {
	
	public AutobackupStopCmd() {
		super("stop", "Autobackup.Stop", "bm.autobackup.stop", new ArrayList<String>(), CommandCategory.AUTOBACKUP);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autobackup stop");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm autobackup stop");
		else {
			if (has(sender, "bm.autobackup.stop")) {
				ThreadManager.stopThread(ThreadType.AUTOBACKUP);
				io.sendTranslation(sender, "Command.Autobackup.Stop");
			}
		}
		return true;
	}
}
