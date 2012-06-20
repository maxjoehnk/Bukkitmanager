package org.efreak1996.Bukkitmanager.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.AutomessageReader;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;
import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;


public class BmAutomessage {

	private static Permissions permHandler;
	private static Configuration config;
	private static ThreadManager func;
	private static IOManager io;
	private static AutomessageReader msgReader;
	
	public void initialize() {
		permHandler = new Permissions();
		msgReader = new AutomessageReader();
		config = new Configuration();
		func = new ThreadManager();
		io = new IOManager();
	}
	
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args) {
		if (args.length <= 1) io.sendFewArgs(sender, "/bm automessage [Args]");
		else {
			if (args[1].equalsIgnoreCase("add")) {
				if (permHandler.has(sender, "bm.automessage.add")) io.send(sender, io.translate("Command.Automessage.Add").replaceAll("%index%", String.valueOf(msgReader.addMessage(args[2]))));
			}else if (args[1].equalsIgnoreCase("remove")) {
				if (permHandler.has(sender, "bm.automessage.remove")) if (msgReader.remMessage(new Integer(args[2])) != null) io.send(sender, io.translate("Command.Automessage.Remove").replaceAll("%index%", args[2]));
			}else if (args[1].equalsIgnoreCase("send")) {
				if (permHandler.has(sender, "bm.automessage.send")) msgReader.sendMessage(new Integer(args[2]), true);
			}else if (args[1].equalsIgnoreCase("get")) {
				if (permHandler.has(sender, "bm.automessage.get")) {
					io.send(sender, io.translate("Command.Automessage.Get").replaceAll("%index", args[2]));
					io.send(sender, msgReader.getMessage(new Integer(args[2])));
				}
			}else if (args[1].equalsIgnoreCase("list")) {
				if (permHandler.has(sender, "bm.automessage.list")) {
					List<String> list = msgReader.listMessages();
					for (int i = 0; i < list.size(); i++) 
						io.send(sender, list.get(i), false);
				}
			}else if (args[1].equalsIgnoreCase("stop")) {
				if (permHandler.has(sender, "bm.automessage.stop")) {
					io.sendTranslation(sender, "Command.Automessage.Stop");
					func.stopThread(ThreadType.AUTOMESSAGE);
				}
			}else if (args[1].equalsIgnoreCase("start")) {
				if (permHandler.has(sender, "bm.automessage.start")) {
					io.sendTranslation(sender, "Command.Automessage.Start");
					func.startThread(ThreadType.AUTOMESSAGE);
				}
			}else if (args[1].equalsIgnoreCase("restart")) {
				if (permHandler.has(sender, "bm.automessage.restart")) {
					io.sendTranslation(sender, "Command.Automessage.Restart");
					func.stopThread(ThreadType.AUTOMESSAGE);
					func.startThread(ThreadType.AUTOMESSAGE);
				}
			}else if (args[1].equalsIgnoreCase("interval")) {
				if (args.length == 2) {
					if (permHandler.has(sender, "bm.automessage.interval.get")) io.send(sender, io.translate("Command.Automessage.Interval.Get").replaceAll("%interval%", config.getString("Automessage.Interval")));
				}else if (args.length == 3) {
					if (permHandler.has(sender, "bm.automessage.interval.set")) {
						io.send(sender, io.translate("Command.Automessage.Interval.Set").replaceAll("%interval%", config.getString("Automessage.Interval")));
						config.set("Automessage.Interval", args[2]);
						io.sendTranslation(sender, "Command.Automessage.Restart");
						func.stopThread(ThreadType.AUTOMESSAGE);
						func.startThread(ThreadType.AUTOMESSAGE);
						io.sendTranslation(sender, "Plugin.Done");
					}
				}else io.sendManyArgs(sender, "/bm Automessage interval [interval]");
			}
		}
	}
}
