package org.efreak.bukkitmanager.commands.plugin;

import java.io.File;
import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.util.FileHelper;

public class PluginLoadCmd extends Command {

    public PluginLoadCmd() {
        super("load", "Plugin.Load", "bm.plugin.load", Arrays.asList("(path)",
                "[file]"), CommandCategory.PLUGIN);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) io.sendFewArgs(sender,
                "/bm plugin load (path) [file]");
        else if (args.length > 3) io.sendManyArgs(sender,
                "/bm plugin load (path) [file]");
        else {
            if (has(sender, "bm.plugin.load")) {
                if (args.length == 2) {
                    File dir = new File(FileHelper.getBukkitDir(), args[1]);
                    if (dir.exists()) {
                        if (dir.isDirectory()) {
                            try {
                                Plugin[] plugins = PluginManager
                                        .loadPlugins(dir);
                                PluginManager.enablePlugins(plugins);
                                io.send(sender,
                                        io.translate(
                                                "Command.Plugin.Load.Dir.Success")
                                                .replaceAll("%dir%",
                                                        dir.getAbsolutePath())
                                                .replaceAll("%plugins%",
                                                        plugins.toString()));
                            } catch (UnknownDependencyException e) {
                                io.send(sender,
                                        "Plugin.Load.Error.UnknownDependency");
                                if (config.getDebug()) e.printStackTrace();
                            } catch (InvalidPluginException e) {
                                io.send(sender,
                                        "Plugin.Load.Error.InvalidPlugin");
                                if (config.getDebug()) e.printStackTrace();
                            } catch (InvalidDescriptionException e) {
                                io.send(sender,
                                        "Plugin.Load.Error.InvalidDescription");
                                if (config.getDebug()) e.printStackTrace();
                            }
                        } else io.sendError(
                                sender,
                                io.translate("Command.Plugin.Load.IsNoDir")
                                        .replaceAll("%dir%",
                                                dir.getAbsolutePath()));
                    } else io
                            .sendError(
                                    sender,
                                    io.translate(
                                            "Command.Plugin.Load.DirDoesntExists")
                                            .replaceAll("%dir%",
                                                    dir.getAbsolutePath()));
                } else {
                    File dir = new File(FileHelper.getBukkitDir(), args[1]);
                    if (dir.exists()) {
                        if (dir.isDirectory()) {
                            File jarFile = new File(dir, args[2]);
                            if (jarFile.isFile()) {
                                try {
                                    Plugin plugin = PluginManager
                                            .loadPlugin(jarFile);
                                    PluginManager.enablePlugin(plugin);
                                    io.send(sender,
                                            io.translate(
                                                    "Command.Plugin.Load.File.Success")
                                                    .replaceAll("%file%",
                                                            jarFile.getName())
                                                    .replaceAll(
                                                            "%plugin%",
                                                            plugin.getDescription()
                                                                    .getFullName()));
                                } catch (UnknownDependencyException e) {
                                    io.send(sender,
                                            "Plugin.Load.Error.UnknownDependency");
                                    if (config.getDebug()) e.printStackTrace();
                                } catch (InvalidPluginException e) {
                                    io.send(sender,
                                            "Plugin.Load.Error.InvalidPlugin");
                                    if (config.getDebug()) e.printStackTrace();
                                } catch (InvalidDescriptionException e) {
                                    io.send(sender,
                                            "Plugin.Load.Error.InvalidDescription");
                                    if (config.getDebug()) e.printStackTrace();
                                }
                            } else io
                                    .sendError(
                                            sender,
                                            io.translate(
                                                    "Command.Plugin.Load.IsNoFile")
                                                    .replaceAll("%file%",
                                                            jarFile.getName()));
                        } else io.sendError(
                                sender,
                                io.translate("Command.Plugin.Load.IsNoDir")
                                        .replaceAll("%dir%",
                                                dir.getAbsolutePath()));
                    } else io
                            .sendError(
                                    sender,
                                    io.translate(
                                            "Command.Plugin.Load.DirDoesntExists")
                                            .replaceAll("%dir%",
                                                    dir.getAbsolutePath()));

                }
            }
        }
        return true;
    }
}
