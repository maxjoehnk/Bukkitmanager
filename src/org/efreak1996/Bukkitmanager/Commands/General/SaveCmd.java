package org.efreak1996.Bukkitmanager.Commands.General;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutosaveThread;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class SaveCmd extends Command {

	private static AutosaveThread saveThread;
	
	public SaveCmd() {
		super("save", "Autosave.Save", new ArrayList<String>(), CommandCategory.GENERAL);
		saveThread = new AutosaveThread();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (0 + length)) io.sendFewArgs(sender, "/bm save");
		else if (args.length > (0 + length)) io.sendManyArgs(sender, "/bm save");
		else {
			if (has(sender, "bm.autosave.save")) saveThread.performSave();
		}
		return true;
	}
}
