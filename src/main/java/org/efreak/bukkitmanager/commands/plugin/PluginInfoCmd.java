package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginInfoCmd extends Command {

    public PluginInfoCmd() {
        super("info", "Plugin.Info", "bm.plugin.info", Arrays
                .asList("[plugin]"), CommandCategory.PLUGIN);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/bm plugin info [plugin]");
        else if (args.length > 2) io.sendManyArgs(sender,
                "/bm plugin info [plugin]");
        else {
            if (has(sender, "bm.plugin.info")) {
                if (args.length == 2) {
                    if (PluginManager.getPlugin(args[1]) == null) {
                        io.sendError(sender, "This Plugin does not exists.");
                        return true;
                    }
                    PluginDescriptionFile pdfFile = PluginManager.getPlugin(
                            args[1]).getDescription();
                    io.send(sender, ChatColor.YELLOW + "--------------"
                            + ChatColor.WHITE + " Plugin Info "
                            + ChatColor.YELLOW + "--------------", false);
                    io.send(sender, ChatColor.RED + "Name:          "
                            + ChatColor.DARK_RED + pdfFile.getName(), false);
                    io.send(sender, ChatColor.RED + "Version:       "
                            + ChatColor.DARK_RED + pdfFile.getVersion(), false);
                    io.send(sender, ChatColor.RED + "Author(s):     "
                            + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
                    if (pdfFile.getDescription() != null) {
                        io.send(sender, ChatColor.RED + "Description:", false);
                        io.send(sender,
                                ChatColor.DARK_RED + pdfFile.getDescription(),
                                false);
                    }
                } else if (args.length == 1) {
                    PluginDescriptionFile pdfFile = Bukkitmanager.getInstance()
                            .getDescription();
                    io.send(sender, ChatColor.YELLOW + "--------------"
                            + ChatColor.WHITE + " Plugin Info "
                            + ChatColor.YELLOW + "--------------", false);
                    io.send(sender, ChatColor.RED + "Name:          "
                            + ChatColor.DARK_RED + pdfFile.getName(), false);
                    io.send(sender, ChatColor.RED + "Version:       "
                            + ChatColor.DARK_RED + pdfFile.getVersion(), false);
                    io.send(sender, ChatColor.RED + "Author(s):     "
                            + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
                    io.send(sender, ChatColor.RED + "Description:", false);
                    io.send(sender,
                            ChatColor.DARK_RED + pdfFile.getDescription(),
                            false);
                }
            }
        }
        return true;
    }
}