package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.AutobackupThread;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;
import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;


public class BmAutobackup {

	private static Permissions permHandler;
	private static Configuration config;
	private static ThreadManager func;
	private static AutobackupThread backupThread;
	private static IOManager io;
	
	public void initialize() {
		config = new Configuration();
		io = new IOManager();
		func = new ThreadManager();
		backupThread = new AutobackupThread();
		permHandler = new Permissions();
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
					func.stopThread(ThreadType.AUTOBACKUP);
				}
			}else if (args[1].equalsIgnoreCase("start")) {
				if (permHandler.has(sender, "bm.autobackup.start")) {
					io.sendTranslation(sender, "Command.Autobackup.Start");
					func.startThread(ThreadType.AUTOBACKUP);
				}
			}else if (args[1].equalsIgnoreCase("restart")) {
				if (permHandler.has(sender, "bm.autobackup.restart")) {
					io.sendTranslation(sender, "Command.Autobackup.Restart");
					func.stopThread(ThreadType.AUTOBACKUP);
					func.startThread(ThreadType.AUTOBACKUP);
				}
			}else if (args[1].equalsIgnoreCase("interval")) {
				if (args.length == 2) {
					if (permHandler.has(sender, "bm.autobackup.interval.get")) io.send(sender, io.translate("Command.Autobackup.Interval.Get").replaceAll("%interval%", config.getString("Autobackup.Interval")));
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.autobackup.interval.set")) {
						io.send(sender, io.translate("Command.Autobackup.Interval.Set").replaceAll("%interval%", config.getString("Autobackup.Interval")));
						config.set("Autobackup.Interval", args[2]);
						io.sendTranslation(sender, "Command.Autobackup.Restart");
						func.stopThread(ThreadType.AUTOBACKUP);
						func.startThread(ThreadType.AUTOBACKUP);
						io.sendTranslation(sender, "Plugin.Done");
					}
				}else io.sendManyArgs(sender, "/bm autobackup interval [interval]", false);
			}
		}
	}
}
