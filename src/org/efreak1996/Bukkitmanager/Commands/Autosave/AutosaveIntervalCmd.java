package org.efreak1996.Bukkitmanager.Commands.Autosave;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.ThreadManager;
import org.efreak1996.Bukkitmanager.ThreadType;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class AutosaveIntervalCmd extends Command {

	private static ThreadManager func;
	
	public AutosaveIntervalCmd() {
		super("interval", "Autosave.Interval", Arrays.asList("[interval]"), CommandCategory.AUTOSAVE);
		func = new ThreadManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm autosave interval [interval]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm autosave interval [interval]");
		else {
			if (args.length == (1 + length)) {
				if (has(sender, "bm.autosave.interval.get")) io.send(sender, io.translate("Command.Autosave.Interval.Get").replaceAll("%interval%", config.getString("Autosave.Interval")));
			}else if (args.length == (2 + length)) {
				if (has(sender, "bm.autosave.interval.set")) {
					io.send(sender, io.translate("Command.Autosave.Interval.Set").replaceAll("%interval%", config.getString("Autosave.Interval")));
					config.set("Autosave.Interval", args[1 + length]);
					io.sendTranslation(sender, "Command.Autosave.Restart");
					func.stopThread(ThreadType.AUTOSAVE);
					func.startThread(ThreadType.AUTOSAVE);
					io.sendTranslation(sender, "Plugin.Done");
				}
			}else io.sendManyArgs(sender, "/bm autosave interval [interval]");
		}
		return true;
	}
}
