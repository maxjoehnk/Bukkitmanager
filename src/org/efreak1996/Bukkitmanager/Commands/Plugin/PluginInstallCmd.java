package org.efreak1996.Bukkitmanager.Commands.Plugin;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PluginInstallCmd extends Command {

	public PluginInstallCmd() {
		super("install", "Plugin.Install", Arrays.asList("[plugin]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm plugin install [plugin]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm plugin install [plugin]");
		else {
			
		}
		return true;
	}

}
