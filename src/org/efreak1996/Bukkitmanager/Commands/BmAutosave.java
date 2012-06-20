package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.BmAutosaveThread;
import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.BmThreadType;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;
import org.efreak1996.Bukkitmanager.Util.BmThreadManager;


public class BmAutosave {
	
	private static BmPermissions permHandler;
	private static BmConfiguration config;
	private static BmThreadManager func;
	private static BmAutosaveThread saveThread;
	private static BmIOManager io;

	public void initialize() {
		saveThread = new BmAutosaveThread();
		permHandler = new BmPermissions();
		func = new BmThreadManager();
		config = new BmConfiguration();
		io = new BmIOManager();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autosave (save|start|stop|restart|interval) [interval]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm autosave (save|start|stop|restart|interval) [interval]");
		else {
			if (args[1].equalsIgnoreCase("save")) {
				if (permHandler.has(sender, "bm.autosave.save")) saveThread.performSave();
			}else if (args[1].equalsIgnoreCase("stop")) {
				if (permHandler.has(sender, "bm.autosave.stop")) {
					io.sendTranslation(sender, "Command.Autosave.Stop");
					func.stopThread(BmThreadType.AUTOSAVE);
				}
			}else if (args[1].equalsIgnoreCase("start")) {
				if (permHandler.has(sender, "bm.autosave.start")) {
					io.sendTranslation(sender, "Command.Autosave.Start");
					func.startThread(BmThreadType.AUTOSAVE);
				}
			}else if (args[1].equalsIgnoreCase("restart")) {
				if (permHandler.has(sender, "bm.autosave.restart")) {
					io.sendTranslation(sender, "Command.Autosave.Restart");
					func.stopThread(BmThreadType.AUTOSAVE);
					func.startThread(BmThreadType.AUTOSAVE);
				}
			}else if (args[1].equalsIgnoreCase("interval")) {
				if (args.length == 2) {
					if (permHandler.has(sender, "bm.autosave.interval.get")) io.send(sender, io.translate("Command.Autosave.Interval.Get").replaceAll("%interval%", config.getString("Autosave.Interval")));
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.autosave.interval.set")) {
						io.send(sender, io.translate("Command.Autosave.Interval.Set").replaceAll("%interval%", config.getString("Autosave.Interval")));
						config.set("Autosave.Interval", new Integer(args[2]));
						io.sendTranslation(sender, "Command.Autosave.Restart");
						func.stopThread(BmThreadType.AUTOSAVE);
						func.startThread(BmThreadType.AUTOSAVE);
						io.sendTranslation(sender, "Plugin.Done");
					}
				}else io.sendManyArgs(sender, "/bm autosave interval [interval]", false);
			}
		}
	}
}
