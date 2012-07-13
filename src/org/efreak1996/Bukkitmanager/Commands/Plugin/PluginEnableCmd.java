package org.efreak1996.Bukkitmanager.Commands.Plugin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PluginEnableCmd extends Command {
	
	public PluginEnableCmd() {
		super("enable", "Plugin.Enable", Arrays.asList("(plugin|all)"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm plugin enable (plugin|all)");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm plugin enable (plugin|all)");
		else {
			if (has(sender, "bm.plugin.enable")) {
				if (args[1 + length].equalsIgnoreCase("all")) {
					Bukkitmanager.getPluginManager().enablePlugins();
					io.send(sender, io.translate("Command.Plugin.Enable.Success.All"));
				}else if (Bukkitmanager.getPluginManager().getPlugin(args[1 + length]) == null) {
					io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
					io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", getPluginList()));
				}else {
					if ((Bukkitmanager.getPluginManager().getPlugin(args[1 + length])).isEnabled()) io.sendError(sender, io.translate("Command.Plugin.Enable.Already"));
					else {
						Bukkitmanager.getPluginManager().enablePlugin(Bukkitmanager.getPluginManager().getPlugin(args[1 + length]));
						io.send(sender, io.translate("Command.Plugin.Enable.Success").replaceAll("%plugin%", args[1 + length]));
					}
				}
			}
		}
		return true;
	}
	
	private static String getPluginList() {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = Bukkitmanager.getPluginManager().getPlugins();
		
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
}
