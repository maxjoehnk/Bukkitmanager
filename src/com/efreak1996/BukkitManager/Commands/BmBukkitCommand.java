package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

public final class BmBukkitCommand extends Command implements PluginIdentifiableCommand {
    private final Plugin owningPlugin;
    private CommandExecutor executor;

    protected BmBukkitCommand(String name, Plugin owner) {
        super(name);
        executor = new BmBukkitCommandExecutor();
        owningPlugin = owner;
        usageMessage = "/bukkit info\n/bukkit config (entry) [value]";
        description = "All /bm bukkit Commands without the /bm Prefix";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        boolean success = false;

        if (!owningPlugin.isEnabled()) return false;

        try {
            success = executor.onCommand(sender, this, commandLabel, args);
        } catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + owningPlugin.getDescription().getFullName(), ex);
        }

        if (!success && usageMessage.length() > 0) {
            for (String line : usageMessage.replace("<command>", commandLabel).split("\n")) {
                sender.sendMessage(line);
            }
        }

        return success;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public Plugin getPlugin() {
        return owningPlugin;
    }
}
