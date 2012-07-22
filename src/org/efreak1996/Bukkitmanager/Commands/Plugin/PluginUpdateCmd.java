package org.efreak1996.Bukkitmanager.Commands.Plugin;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PluginUpdateCmd extends Command{
	
	public PluginUpdateCmd() {
		super("update", "Plugin.Update", "bm.plugin.update", Arrays.asList("[plugin|all]"), CommandCategory.PLUGIN);
	}
		
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm plugin update [plugin|all]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm plugin update [plugin|all]");
		else {
			
		}
		return true;
	}
}
