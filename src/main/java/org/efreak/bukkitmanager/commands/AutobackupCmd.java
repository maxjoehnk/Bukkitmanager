package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.util.BackupHelper;

public class AutobackupCmd extends CommandHandler {

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		if (args.length == 1) {
			for (String subCommand : subCommands.keySet()) {
				if (subCommand.startsWith(args[0])) tabs.add(subCommand);
			}
		}else if (subCommands.containsKey(args[0])) {
			try {
			CommandHandler handler = (CommandHandler) subCommands.get(args[0]).getDeclaringClass().newInstance();
			return handler.onTabComplete(sender, cmd, label, args);
			}catch(Exception e) {
				io.sendConsoleWarning("Error on TabCompletion: " + e.getLocalizedMessage());
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return tabs;
	}
	
	@Command(label = "autobackup", helpNode = "Autobackup", hideHelp = true, usage = "/autobackup <backup|start|stop|restart|interval> [args]")
	public boolean autobackupCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@Command(label = "backup", helpNode = "Autobackup.Backup", hideHelp = true, permission = "bm.autobackup.backup", usage = "/backup")
	@SubCommand(label = "backup", helpNode = "Autobackup.Backup", permission = "bm.autobackup.backup", usage = "autobackup backup")
	public boolean backupCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm backup");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm backup");
		else BackupHelper.performBackup();
		return true;
	}
	
	@SubCommand(label = "start", helpNode = "Autobackup.Start", permission = "bm.autobackup.start", usage = "autobackup start")
	public boolean startCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm autobackup start");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm autobackup start");
		else {
			ThreadManager.startThread(ThreadType.AUTOBACKUP);
			io.sendTranslation(sender, "Command.Autobackup.Start");
		}
		return true;
	}
	
	@SubCommand(label = "stop", helpNode = "Autobackup.Stop", permission = "bm.autobackup.stop", usage = "autobackup stop")
	public boolean stopCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm autobackup stop");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm autobackup stop");
		else {
			ThreadManager.stopThread(ThreadType.AUTOBACKUP);
			io.sendTranslation(sender, "Command.Autobackup.Stop");
		}
		return true;
	}
	
	@SubCommand(label = "restart", helpNode = "Autobackup.Restart", permission = "bm.autobackup.restart", usage = "autobackup restart")
	public boolean restartCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm autobackup restart");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm autobackup restart");
		else {
			ThreadManager.stopThread(ThreadType.AUTOBACKUP);
			ThreadManager.startThread(ThreadType.AUTOBACKUP);
			io.sendTranslation(sender, "Command.Autobackup.Restart");
		}
		return true;
	}
	
	@SubCommand(label = "interval", helpNode = "Autobackup.Interval", permission = "bm.autobackup.interval", usage = "autobackup interval [interval]")
	public boolean intervalCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm autobackup interval [interval]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm autobackup interval [interval]");
		else {
			if (args.length == 0) {
				if (Permissions.has(sender, "bm.autobackup.interval.get", "/autobackup interval")) io.send(sender, io.translate("Command.Autobackup.Interval.Get").replaceAll("%interval%", config.getString("Autobackup.Interval")));
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.autobackup.interval.set", "/autobackup interval " + args[0])) {
					io.send(sender, io.translate("Command.Autobackup.Interval.Set").replaceAll("%interval_new", args[0]).replaceAll("%interval_old%", config.getString("Autobackup.Interval")));
					config.set("Autobackup.Interval", args[0]);
					config.save();
					io.sendTranslation(sender, "Command.Autobackup.Restart");
					ThreadManager.stopThread(ThreadType.AUTOBACKUP);
					ThreadManager.startThread(ThreadType.AUTOBACKUP);
					io.sendTranslation(sender, "Plugin.Done");
				}
			}
		}
		return true;
	}
	
}
