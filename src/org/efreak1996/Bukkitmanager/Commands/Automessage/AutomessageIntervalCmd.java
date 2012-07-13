package org.efreak1996.Bukkitmanager.Commands.Automessage;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutomessageIntervalCmd extends Command {

	private static ThreadManager func;
	
	public AutomessageIntervalCmd() {
		super("interval", "Automessage.Interval", Arrays.asList("[interval]"), CommandCategory.AUTOMESSAGE);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm automessage interval [interval]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm automessage interval [interval]");
		else {
			if (args.length == (1 + length)) {
				if (has(sender, "bm.automessage.interval.get")) io.send(sender, io.translate("Command.Automessage.Interval.Get").replaceAll("%interval%", config.getString("Automessage.Interval")));
			}else if (args.length == (2 + length)) {
				if (has(sender, "bm.automessage.interval.set")) {
					io.send(sender, io.translate("Command.Automessage.Interval.Set").replaceAll("%interval%", config.getString("Automessage.Interval")));
					config.set("Automessage.Interval", args[1 + length]);
					io.sendTranslation(sender, "Command.Automessage.Restart");
					func.stopThread(ThreadType.AUTOMESSAGE);
					func.startThread(ThreadType.AUTOMESSAGE);
					io.sendTranslation(sender, "Plugin.Done");
				}
			}else io.sendManyArgs(sender, "/bm automessage interval [interval]");
		}
		return true;
	}
}
