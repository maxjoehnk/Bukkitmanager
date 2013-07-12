package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.util.SaveHelper;

public class AutosaveCmd extends CommandHandler {

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
	
	@Command(label = "autosave", alias = true, hideHelp = true, usage = "/autosave <save|start|stop|restart|interval> [args]")
	public boolean autosaveCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@Command(label = "save", alias = true, helpNode = "Autosave.Save", hideHelp = true, permission = "bm.autosave.save", usage = "/save")
	@SubCommand(label = "save", helpNode = "Autosave.Save", permission = "bm.autosave.save", usage = "autosave save")
	public boolean saveCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm save");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm save");
		else SaveHelper.performSave();
		return true;
	}
	
	@SubCommand(label = "start", helpNode = "Autosave.Start", permission = "bm.autosave.start", usage = "autosave start")
	public boolean startCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autosave start");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm autosave start");
		else {
			ThreadManager.startThread(ThreadType.AUTOSAVE);
			io.sendTranslation(sender, "Command.Autosave.Start");
		}
		return true;
	}
	
	@SubCommand(label = "stop", helpNode = "Autosave.Stop", permission = "bm.autosave.stop", usage = "autosave stop")
	public boolean stopCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autosave stop");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm autosave stop");
		else {
			ThreadManager.stopThread(ThreadType.AUTOSAVE);
			io.sendTranslation(sender, "Command.Autosave.Stop");
		}
		return true;
	}
	
	@SubCommand(label = "restart", helpNode = "Autosave.Restart", permission = "bm.autosave.restart", usage = "autosave restart")
	public boolean restartCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autosave restart");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm autosave restart");
		else {
			ThreadManager.stopThread(ThreadType.AUTOSAVE);
			ThreadManager.startThread(ThreadType.AUTOSAVE);
			io.sendTranslation(sender, "Command.Autosave.Restart");
		}
		return true;
	}
	
	@SubCommand(label = "interval", helpNode = "Autosave.Interval", permission = "bm.autosave.interval", usage = "autosave interval [interval]")
	public boolean intervalCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm autosave interval [interval]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm autosave interval [interval]");
		else {
			if (args.length == 1) {
				if (Permissions.has(sender, "bm.autosave.interval.get", "/autosave interval")) io.send(sender, io.translate("Command.Autosave.Interval.Get").replaceAll("%interval%", config.getString("Autosave.Interval")));
			}else if (args.length == 2) {
				if (Permissions.has(sender, "bm.autosave.interval.set", "/autosave interval " + args[1])) {
					io.send(sender, io.translate("Command.Autosave.Interval.Set").replaceAll("%interval_new", args[1]).replaceAll("%interval_old%", config.getString("Autosave.Interval")));
					config.set("Autosave.Interval", args[1]);
					config.save();
					io.sendTranslation(sender, "Command.Autosave.Restart");
					ThreadManager.stopThread(ThreadType.AUTOSAVE);
					ThreadManager.startThread(ThreadType.AUTOSAVE);
					io.sendTranslation(sender, "Plugin.Done");
				}
			}
		}
		return true;
	}
	
}
