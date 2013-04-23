package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginReloadCmd extends Command {
	
	public PluginReloadCmd() {
		super("reload", "Plugin.Reload", "bm.plugin.reload", Arrays.asList("[plugin|all]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin reload [plugin|all]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm plugin reload [plugin|all]");
		else {
			if (has(sender, "bm.plugin.reload")) {
				if (args.length == 1) {
					try {
						PluginManager.reloadPlugin(Bukkitmanager.getInstance());
						io.send(sender, io.translate("Command.Plugin.Reload.Success").replaceAll("%plugin%", "Bukkitmanager"));
					}catch (UnknownDependencyException e) {
						io.send(sender, "Plugin.Load.Error.UnknownDependency");
						if (config.getDebug()) e.printStackTrace();
					}catch (InvalidPluginException e) {
						io.send(sender, "Plugin.Load.Error.InvalidPlugin");
						if (config.getDebug()) e.printStackTrace();
					}catch (InvalidDescriptionException e) {
						io.send(sender, "Plugin.Load.Error.InvalidDescription");
						if (config.getDebug()) e.printStackTrace();
					}
				}else {
					if (args[1].equalsIgnoreCase("all")) {
						try {
							PluginManager.reloadPlugins();
							io.send(sender, io.translate("Command.Plugin.Reload.Success.All"));
						}catch (UnknownDependencyException e) {
							io.send(sender, "Plugin.Load.Error.UnknownDependency");
							if (config.getDebug()) e.printStackTrace();
						}catch (InvalidPluginException e) {
							io.send(sender, "Plugin.Load.Error.InvalidPlugin");
							if (config.getDebug()) e.printStackTrace();
						}catch (InvalidDescriptionException e) {
							io.send(sender, "Plugin.Load.Error.InvalidDescription");
							if (config.getDebug()) e.printStackTrace();
						}
					}else if (PluginManager.getPlugin(args[1]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
					}else {
						try {
							PluginManager.reloadPlugin(PluginManager.getPlugin(args[1]));
							io.send(sender, io.translate("Command.Plugin.Reload.Success").replaceAll("%plugin%", args[1]));
						}catch (UnknownDependencyException e) {
							io.send(sender, "Plugin.Load.Error.UnknownDependency");
							if (config.getDebug()) e.printStackTrace();
						}catch (InvalidPluginException e) {
							io.send(sender, "Plugin.Load.Error.InvalidPlugin");
							if (config.getDebug()) e.printStackTrace();
						}catch (InvalidDescriptionException e) {
							io.send(sender, "Plugin.Load.Error.InvalidDescription");
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}
			}
		}
		return true;
	}
}
