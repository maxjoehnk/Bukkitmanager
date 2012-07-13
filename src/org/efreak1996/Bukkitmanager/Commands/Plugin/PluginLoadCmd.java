package org.efreak1996.Bukkitmanager.Commands.Plugin;

import java.io.File;
import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;
import org.efreak1996.Bukkitmanager.PluginManager.PluginManager;

public class PluginLoadCmd extends Command {

	private static PluginManager pm;
	
	public PluginLoadCmd() {
		super("load", "Plugin.Load", Arrays.asList("(path)", "[file]"), CommandCategory.PLUGIN);
		pm = Bukkitmanager.getPluginManager();
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm plugin load (path) [file]");
		else if (args.length > (3 + length)) io.sendManyArgs(sender, "/bm plugin load (path) [file]");
		else {
			if (has(sender, "bm.plugin.load")) {
				if (args.length == (2 + length)) {
					File dir = new File(Bukkitmanager.getRootFolder(), args[1 + length]);
					if (dir.exists()) {
						if (dir.isDirectory()) {
							try {
								Plugin[] plugins = pm.loadPlugins(dir);
								pm.enablePlugins(plugins);
								io.send(sender, io.translate("Command.Plugin.Load.Dir.Success").replaceAll("%dir%", dir.getAbsolutePath()).replaceAll("%plugins%", plugins.toString()));
							}catch (Exception e) {
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
					}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));
				}else {
					File dir = new File(Bukkitmanager.getRootFolder(), args[1 + length]);
					if (dir.exists()) {
						if (dir.isDirectory()) {
							File jarFile = new File(dir, args[2 + length]);
							if (jarFile.isFile()) {
								try {
									Plugin plugin = pm.loadPlugin(jarFile);
									pm.enablePlugin(plugin);
									io.send(sender, io.translate("Command.Plugin.Load.File.Success").replaceAll("%file%", jarFile.getName()).replaceAll("%plugin%", plugin.getDescription().getFullName()));								
								} catch (Exception e) {
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoFile").replaceAll("%file%", jarFile.getName()));
						}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
					}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));

				}
			}
		}
		return true;
	}
}
