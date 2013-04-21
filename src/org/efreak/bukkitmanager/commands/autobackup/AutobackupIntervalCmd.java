package org.efreak.bukkitmanager.commands.autobackup;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutobackupIntervalCmd extends Command {
	
	public AutobackupIntervalCmd() {
		super("interval", "Autobackup.Interval", "bm.autobackup.interval", Arrays.asList("[interval]"), CommandCategory.AUTOBACKUP);
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
					io.send(sender, io.translate("Command.Autobackup.Interval.Set").replaceAll("%interval_new", args[1 + length]).replaceAll("%interval_old%", config.getString("Autobackup.Interval")));
					config.set("Autobackup.Interval", args[1 + length]);
					config.save();
					io.sendTranslation(sender, "Command.Autobackup.Restart");
					ThreadManager.stopThread(ThreadType.AUTOBACKUP);
					ThreadManager.startThread(ThreadType.AUTOBACKUP);
					io.sendTranslation(sender, "Plugin.Done");
				}
			}else io.sendManyArgs(sender, "/bm autobackup interval [interval]");
		}
		return true;
	}
}
