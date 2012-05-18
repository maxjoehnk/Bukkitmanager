package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmAutobackupThread;
import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmFunctions;
import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.BmThreadType;

public class BmAutobackup {

	private static BmPermissions permHandler;
	private static BmConfiguration config;
	private static BmFunctions func;
	private static BmAutobackupThread backupThread;
	private static BmIOManager io;
	
	public void initialize() {
		config = new BmConfiguration();
		io = new BmIOManager();
		func = new BmFunctions();
		backupThread = new BmAutobackupThread();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autobackup (backup|start|stop|restart|interval) [interval]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm autobackup (backup|start|stop|restart|interval) [interval]");
		else {
			if (args[1].equalsIgnoreCase("backup")) {
				if (permHandler.has(sender, "bm.autobackup.backup")) backupThread.performBackup();
			}else if (args[1].equalsIgnoreCase("stop")) {
				if (permHandler.has(sender, "bm.autobackup.stop")) {
					io.sendTranslation(sender, "Command.Autobackup.Stop");
					func.stopThread(BmThreadType.AUTOBACKUP);
				}
			}else if (args[1].equalsIgnoreCase("start")) {
				if (permHandler.has(sender, "bm.autobackup.start")) {
					io.sendTranslation(sender, "Command.Autobackup.Start");
					func.startThread(BmThreadType.AUTOBACKUP);
				}
			}else if (args[1].equalsIgnoreCase("restart")) {
				if (permHandler.has(sender, "bm.autobackup.restart")) {
					io.sendTranslation(sender, "Command.Autobackup.Restart");
					func.stopThread(BmThreadType.AUTOBACKUP);
					func.startThread(BmThreadType.AUTOBACKUP);
				}
			}else if (args[1].equalsIgnoreCase("interval")) {
				if (args.length == 2) {
					if (permHandler.has(sender, "bm.autobackup.interval.get")) io.send(sender, io.translate("Command.Autobackup.Interval.Get").replaceAll("%interval%", config.getString("Autobackup.Interval")));
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.autobackup.interval.set")) {
						io.send(sender, io.translate("Command.Autobackup.Interval.Set").replaceAll("%interval%", config.getString("Autobackup.Interval")));
						config.set("Autobackup.Interval", args[2]);
						io.sendTranslation(sender, "Command.Autobackup.Restart");
						func.stopThread(BmThreadType.AUTOBACKUP);
						func.startThread(BmThreadType.AUTOBACKUP);
						io.sendTranslation(sender, "Plugin.Done");
					}
				}else io.sendManyArgs(sender, "/bm autobackup interval [interval]", false);
			}
		}
	}
}
