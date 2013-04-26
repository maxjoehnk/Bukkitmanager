package org.efreak.bukkitmanager.commands.autobackup;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutobackupStartCmd extends Command {
	
	public AutobackupStartCmd() {
		super("start", "Autobackup.Start", "bm.autobackup.start", new ArrayList<String>(), CommandCategory.AUTOBACKUP);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autobackup start");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm autobackup start");
		else {
			if (has(sender, "bm.autobackup.start")) {
				ThreadManager.startThread(ThreadType.AUTOBACKUP);
				io.sendTranslation(sender, "Command.Autobackup.Start");
			}
		}
		return true;
	}
}
