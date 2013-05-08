package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginRestartCmd extends Command {
	
	public PluginRestartCmd() {
		super("restart", "Plugin.Restart", "bm.plugin.restart", Arrays.asList("[plugin|all]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin restart [plugin|all]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm plugin restart [plugin|all]");
		else {
			if (has(sender, "bm.plugin.restart")) {
				if (args.length == 1) {
					PluginManager.restartPlugin(Bukkitmanager.getInstance());
					io.send(sender, io.translate("Command.Plugin.Restart.Success").replaceAll("%plugin%", "Bukkitmanager"));
				}else {
					if (args[1].equalsIgnoreCase("all")) {
						PluginManager.restartPlugins();
						io.send(sender, io.translate("Command.Plugin.Restart.Success.All"));
					}else if (PluginManager.getPluginIgnoreCase(args[1]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
					}else {
						if (args[1] == "Spout") io.sendWarning(sender, io.translate("Command.Plugin.Restart.Spout"));
						else {
							PluginManager.restartPlugin(PluginManager.getPluginIgnoreCase(args[1]));
							io.send(sender, io.translate("Command.Plugin.Restart.Success").replaceAll("%plugin%", args[1]));
						}
					}
				}
			}
		}
		return true;
	}
}
