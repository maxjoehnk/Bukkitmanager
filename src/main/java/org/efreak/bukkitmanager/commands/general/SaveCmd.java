package org.efreak.bukkitmanager.commands.general;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.util.SaveHelper;

public class SaveCmd extends Command {

	private static SaveHelper saveHelper;
	
	public SaveCmd() {
		super("save", "Autosave.Save", "bm.autosave.save", new ArrayList<String>(), CommandCategory.GENERAL);
		saveHelper = new SaveHelper();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm save");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm save");
		else {
			if (has(sender, "bm.autosave.save")) saveHelper.performSave();
		}
		return true;
	}
}
