package org.efreak1996.Bukkitmanager.Commands.Autosave;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutosaveThread;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutosaveSaveCmd extends Command {

	private static AutosaveThread saveThread;
	
	public AutosaveSaveCmd() {
		super("save", "Autosave.Save", new ArrayList<String>(), CommandCategory.AUTOSAVE);
		saveThread = new AutosaveThread();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autosave save");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm autosave save");
		else {
			if (has(sender, "bm.autosave.backup")) saveThread.performSave();
		}
		return true;
	}
}
