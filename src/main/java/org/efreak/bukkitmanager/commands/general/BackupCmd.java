package org.efreak.bukkitmanager.commands.general;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.util.BackupHelper;

public class BackupCmd extends Command {

	private static BackupHelper backupHelper;
	
	public BackupCmd() {
		super("backup", "Autobackup.Backup", "bm.autobackup.backup", new ArrayList<String>(), CommandCategory.GENERAL);
		backupHelper = new BackupHelper();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm backup");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm backup");
		else {
			if (has(sender, "bm.autobackup.backup")) backupHelper.performBackup();
		}
		return true;
	}
}
