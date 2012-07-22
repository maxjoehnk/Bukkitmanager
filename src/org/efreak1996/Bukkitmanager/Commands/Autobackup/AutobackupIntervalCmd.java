package org.efreak1996.Bukkitmanager.Commands.Autobackup;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutobackupIntervalCmd extends Command {

	private static ThreadManager func;
	
	public AutobackupIntervalCmd() {
		super("interval", "Autobackup.Interval", "bm.autobackup.interval", Arrays.asList("[interval]"), CommandCategory.AUTOBACKUP);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autobackup interval [interval]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm autobackup interval [interval]");
		else {
			if (args.length == (1 + length)) {
				if (has(sender, "bm.autobackup.interval.get")) io.send(sender, io.translate("Command.Autobackup.Interval.Get").replaceAll("%interval%", config.getString("Autobackup.Interval")));
			}else if (args.length == (2 + length)) {
				if (has(sender, "bm.autobackup.interval.set")) {
					io.send(sender, io.translate("Command.Autobackup.Interval.Set").replaceAll("%interval%", config.getString("Autobackup.Interval")));
					config.set("Autobackup.Interval", args[1 + length]);
					io.sendTranslation(sender, "Command.Autobackup.Restart");
					func.stopThread(ThreadType.AUTOBACKUP);
					func.startThread(ThreadType.AUTOBACKUP);
					io.sendTranslation(sender, "Plugin.Done");
				}
			}else io.sendManyArgs(sender, "/bm autobackup interval [interval]");
		}
		return true;
	}
}
