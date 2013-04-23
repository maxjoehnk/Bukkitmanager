package org.efreak.bukkitmanager.commands.autobackup;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.util.BackupHelper;

public class AutobackupBackupCmd extends Command {

	private static BackupHelper backupHelper;
	
	public AutobackupBackupCmd() {
		super("backup", "Autobackup.Backup", "bm.autobackup.backup", new ArrayList<String>(), CommandCategory.AUTOBACKUP);
		backupHelper = new BackupHelper();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autobackup backup");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm autobackup backup");
		else {
			if (has(sender, "bm.autobackup.backup")) backupHelper.performBackup();
		}
		return true;
	}
}
