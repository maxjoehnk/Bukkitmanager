package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginListCmd extends Command {

    public PluginListCmd() {
        super("list", "Plugin.List", "bm.plugin.list", Arrays.asList("[#]"),
                CommandCategory.PLUGIN);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/bm plugin list [#]");
        else if (args.length > 2) io
                .sendManyArgs(sender, "/bm plugin list [#]");
        else {
            if (args.length == 1 && has(sender, "bm.plugin.list.normal")) io
                    .send(sender, "Plugins: " + PluginManager.getPluginList());
            else if (args.length == 2 && has(sender, "bm.plugin.list.detail")) showDetailedList(
                    sender, Integer.parseInt(args[1]));
        }
        return true;
    }

    private static void showDetailedList(CommandSender sender, int page) {
        StringBuilder pluginList = new StringBuilder();
        Plugin[] plugins = PluginManager.getPlugins();
        pluginList.append(ChatColor.YELLOW + "---------------------------"
                + ChatColor.WHITE + " PLUGINS " + ChatColor.YELLOW
                + "---------------------------");
        for (Plugin plugin : plugins) {
            pluginList.append("\n");
            pluginList.append(plugin.isEnabled() ? ChatColor.GREEN
                    : ChatColor.RED);
            pluginList.append(plugin.getDescription().getFullName());
        }
        io.send(sender, pluginList.toString(), false);
    }
}
