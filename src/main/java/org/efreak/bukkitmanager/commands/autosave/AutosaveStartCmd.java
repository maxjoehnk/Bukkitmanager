package org.efreak.bukkitmanager.commands.autosave;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutosaveStartCmd extends Command {

	private static ThreadManager func;
	
	public AutosaveStartCmd() {
		super("start", "Autosave.Start", "bm.autsave.start", new ArrayList<String>(), CommandCategory.AUTOSAVE);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autosave start");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm autosave start");
		else {
			if (has(sender, "bm.autosave.start")) {
				func.startThread(ThreadType.AUTOSAVE);
				io.sendTranslation(sender, "Command.Autosave.Start");
			}
		}
		return true;
	}
}