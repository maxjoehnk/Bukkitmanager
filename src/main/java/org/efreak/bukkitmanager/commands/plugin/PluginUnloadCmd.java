package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginUnloadCmd extends Command {
	
	public PluginUnloadCmd() {
		super("unload", "Plugin.Unload", "bm.plugin.unload", Arrays.asList("[plugin|all]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin unload [plugin|all]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm plugin unload [plugin|all]");
		else {
			if (has(sender, "bm.plugin.unload")) {
				if (args.length == 1) {
					PluginManager.unloadPlugin(Bukkitmanager.getInstance());
					io.send(sender, io.translate("Command.Plugin.Unload.Success").replaceAll("%plugin%", "Bukkitmanager"));
				}else {
					if (args[1].equalsIgnoreCase("all")) {
						PluginManager.unloadPlugins();
						io.send(sender, io.translate("Command.Plugin.Reload.Success.All"));
					}else if (PluginManager.getPlugin(args[1]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
					}else {
						PluginManager.unloadPlugin(PluginManager.getPlugin(args[1]));
						io.send(sender, io.translate("Command.Plugin.Reload.Success").replaceAll("%plugin%", args[1]));
					}
				}
			}
		}
		return true;
	}
}
