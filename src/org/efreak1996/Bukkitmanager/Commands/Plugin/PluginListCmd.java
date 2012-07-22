package org.efreak1996.Bukkitmanager.Commands.Plugin;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class PluginListCmd extends Command {
	
	public PluginListCmd() {
		super("list", "Plugin.List", "bm.plugin.list", Arrays.asList("[#]"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm plugin list [#]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm plugin list [#]");
		else {
			if (args.length == (1 + length)) {
				if (has(sender, "bm.plugin.list.normal")) io.send(sender, "Plugins: " + getPluginList());
			}
			else if (args.length == (2 + length)) {
				if (has(sender, "bm.plugin.list.detail")) showDetailedList(sender, Integer.parseInt(args[1 + length]));
			}
		}
		return true;
	}
	
	private static void showDetailedList(CommandSender sender, int page) {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = Bukkitmanager.getPluginManager().getPlugins();
		pluginList.append(ChatColor.YELLOW + "---------------------------" + ChatColor.WHITE + " PLUGINS " + ChatColor.YELLOW + "---------------------------");
		for (Plugin plugin : plugins) {
			pluginList.append("\n");			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getFullName());
		}
		io.send(sender, pluginList.toString(), false);
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
			pluginList.append(plugin.getDescription().getFullName());
		}
		return pluginList.toString();
	}
}
