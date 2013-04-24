package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginEnableCmd extends Command {

    public PluginEnableCmd() {
        super("enable", "Plugin.Enable", "bm.plugin.enable", Arrays
                .asList("(plugin|all)"), CommandCategory.PLUGIN);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) io.sendFewArgs(sender,
                "/bm plugin enable (plugin|all)");
        else if (args.length > 2) io.sendManyArgs(sender,
                "/bm plugin enable (plugin|all)");
        else {
            if (has(sender, "bm.plugin.enable")) {
                if (args[1].equalsIgnoreCase("all")) {
                    PluginManager.enablePlugins();
                    io.send(sender,
                            io.translate("Command.Plugin.Enable.Success.All"));
                } else {
                    if (PluginManager.getPlugin(args[1]) == null) {
                        io.sendError(sender,
                                io.translate("Command.Plugin.DoesntExists"));
                        io.send(sender,
                                io.translate("Command.Plugin.Available")
                                        .replaceAll("%pluginlist%",
                                                PluginManager.getPluginList()));
                    } else {
                        if (PluginManager.getPlugin(args[1]).isEnabled()) io
                                .sendError(
                                        sender,
                                        io.translate("Command.Plugin.Enable.Already"));
                        else {
                            PluginManager.enablePlugin(PluginManager
                                    .getPlugin(args[1]));
                            io.send(sender,
                                    io.translate(
                                            "Command.Plugin.Enable.Success")
                                            .replaceAll("%plugin%", args[1]));
                        }
                    }
                }
            }
        }
        return true;
    }
}
