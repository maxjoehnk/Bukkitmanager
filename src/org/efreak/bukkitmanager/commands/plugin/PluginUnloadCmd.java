package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginUnloadCmd extends Command {
	
	public PluginUnloadCmd() {
		super("reload", "Plugin.Reload", "bm.plugin.unload", Arrays.asList("[plugin|all]"), CommandCategory.PLUGIN);
	}

	private String getPluginList() {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = PluginManager.getPlugins();
		
		for (Plugin plugin : plugins) {
			if (pluginList.length() > 0) {
				pluginList.append(ChatColor.WHITE);
				pluginList.append(", ");
			}
			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getName());
		}
		return pluginList.toString();
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm plugin unload [plugin|all]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm plugin unload [plugin|all]");
		else {
			if (has(sender, "bm.plugin.unload")) {
				if (args.length == (1 + length)) {
					PluginManager.unloadPlugin(Bukkitmanager.getInstance());
					io.send(sender, io.translate("Command.Plugin.Unload.Success").replaceAll("%plugin%", "Bukkitmanager"));
				}else {
					if (args[1 + length].equalsIgnoreCase("all")) {
						PluginManager.unloadPlugins();
						io.send(sender, io.translate("Command.Plugin.Reload.Success.All"));
					}else if (PluginManager.getPlugin(args[1 + length]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
					}else {
						PluginManager.unloadPlugin(PluginManager.getPlugin(args[1 + length]));
						io.send(sender, io.translate("Command.Plugin.Reload.Success").replaceAll("%plugin%", args[1 + length]));
					}
				}
			}
		}
		return true;
	}
}
