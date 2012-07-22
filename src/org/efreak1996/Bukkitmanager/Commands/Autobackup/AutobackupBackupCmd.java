package org.efreak1996.Bukkitmanager.Commands.Autobackup;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutobackupThread;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutobackupBackupCmd extends Command {

	private static AutobackupThread backupThread;
	
	public AutobackupBackupCmd() {
		super("backup", "Autobackup.Backup", "bm.autobackup.backup", new ArrayList<String>(), CommandCategory.AUTOBACKUP);
		backupThread = new AutobackupThread();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autobackup backup");
		else if (args.length > (1 + length)) io.sendManyArgs(sender, "/bm autobackup backup");
		else {
			if (has(sender, "bm.autobackup.backup")) backupThread.performBackup();
		}
		return true;
	}
}
