package org.efreak1996.Bukkitmanager.Commands.General;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.AutobackupThread;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class BackupCmd extends Command {

	private static AutobackupThread backupThread;
	
	public BackupCmd() {
		super("backup", "Autobackup.Backup", "bm.autobackup.backup", new ArrayList<String>(), CommandCategory.GENERAL);
		backupThread = new AutobackupThread();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (0 + length)) io.sendFewArgs(sender, "/bm backup");
		else if (args.length > (0 + length)) io.sendManyArgs(sender, "/bm backup");
		else {
			if (has(sender, "bm.autobackup.backup")) backupThread.performBackup();
		}
		return true;
	}
}
