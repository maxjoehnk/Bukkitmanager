package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.AutomessageReader;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.util.BackupHelper;

public class AutomessageCmd extends CommandHandler {

	private static AutomessageReader msgReader;

	static {
		msgReader = new AutomessageReader();
	}
	
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
	
	@Command(label = "automessage", helpNode = "Automessage", hideHelp = true, usage = "/automessage <start|stop|restart|interval>")
	public boolean autobackupCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@SubCommand(label = "start", helpNode = "Automessage.Start", permission = "bm.automessage.start", usage = "automessage start")
	public boolean startCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm automessage start");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm automessage start");
		else {
			ThreadManager.startThread(ThreadType.AUTOMESSAGE);
			io.sendTranslation(sender, "Command.Automessage.Start");
		}
		return true;
	}
	
	@SubCommand(label = "stop", helpNode = "Automessage.Stop", permission = "bm.automessage.stop", usage = "automessage stop")
	public boolean stopCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm automessage stop");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm automessage stop");
		else {
			ThreadManager.stopThread(ThreadType.AUTOMESSAGE);
			io.sendTranslation(sender, "Command.Automessage.Stop");
		}
		return true;
	}
	
	@SubCommand(label = "restart", helpNode = "Automessage.Restart", permission = "bm.automessage.restart", usage = "automessage restart")
	public boolean restartCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm automessage restart");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm automessage restart");
		else {
			ThreadManager.stopThread(ThreadType.AUTOMESSAGE);
			ThreadManager.startThread(ThreadType.AUTOMESSAGE);
			io.sendTranslation(sender, "Command.Automessage.Restart");
		}
		return true;
	}
	
	@SubCommand(label = "interval", helpNode = "Automessage.Interval", permission = "bm.automessage.interval", usage = "automessage interval [interval]")
	public boolean intervalCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm automessage interval [interval]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm automessage interval [interval]");
		else {
			if (args.length == 0) {
				if (Permissions.has(sender, "bm.automessage.interval.get", "/automessage interval")) io.send(sender, io.translate("Command.Automessage.Interval.Get").replaceAll("%interval%", config.getString("Automessage.Interval")));
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.automessage.interval.set", "/automessage interval " + args[0])) {
					io.send(sender, io.translate("Command.Automessage.Interval.Set").replaceAll("%interval_new", args[0]).replaceAll("%interval_old%", config.getString("Automessage.Interval")));
					config.set("Automessage.Interval", args[0]);
					config.save();
					io.sendTranslation(sender, "Command.Automessage.Restart");
					ThreadManager.stopThread(ThreadType.AUTOMESSAGE);
					ThreadManager.startThread(ThreadType.AUTOMESSAGE);
					io.sendTranslation(sender, "Plugin.Done");
				}
			}
		}
		return true;
	}
	
	@SubCommand(label = "send", helpNode = "Automessage.Send", permission = "bm.automesage.send", usage = "automessage send (&oindex)")
	public boolean sendCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm automessage send (index)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm automessage send (index)");
		else msgReader.sendMessage(new Integer(args[0]), true);
		return true;
	}
	
	@SubCommand(label = "add", helpNode = "Automessage.Add", permission = "bm.automesage.add", usage = "automessage add (&omsg)")
	public boolean addCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm automessage add (msg)");
		else {
			String msg = args[0];
			if (args.length > 1) {
				StringBuilder msgBuilder = new StringBuilder();
				for (int i = 0; i < args.length; i++) msgBuilder.append(args[i]);
				msg = msgBuilder.toString();
			}
			io.send(sender, io.translate("Command.Automessage.Add").replaceAll("%index%", String.valueOf(msgReader.addMessage(msg))));
		}
		return true;
	}
	
	@SubCommand(label = "get", helpNode = "Automessage.Get", permission = "bm.automesage.get", usage = "automessage get (&oindex)")
	public boolean getCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm automessage get (index)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm automessage get (index)");
		else {
			io.send(sender, io.translate("Command.Automessage.Get").replaceAll("%index", args[0]));
			io.send(sender, msgReader.getMessage(new Integer(args[0])));
		}
		return true;
	}
	
	@SubCommand(label = "list", helpNode = "Automessage.List", permission = "bm.automesage.list", usage = "automessage list")
	public boolean listCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm automessage list");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm automessage list");
		else {
			List<String> list = msgReader.listMessages();
			for (int i = 0; i < list.size(); i++) io.send(sender, list.get(i), false);
		}
		return true;
	}
	
	@SubCommand(label = "remove", helpNode = "Automessage.Remove", permission = "bm.automesage.remove", usage = "automessage remove (&oindex)")
	public boolean removeCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm automessage remove (index)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm automessage remove (index)");
		else {
			if (msgReader.remMessage(new Integer(args[1])) != null) io.send(sender, io.translate("Command.Automessage.Remove").replaceAll("%index%", args[1]));
			else io.sendError(sender, io.translate("Command.Automessage.Remove.NotFound"));
		}
		return true;
	}
}
