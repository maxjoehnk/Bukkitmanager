package org.efreak.bukkitmanager.commands.autosave;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutosaveIntervalCmd extends Command {

    public AutosaveIntervalCmd() {
        super("interval", "Autosave.Interval", "bm.autosave.interval", Arrays
                .asList("[interval]"), CommandCategory.AUTOSAVE);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender,
                "/bm autosave interval [interval]");
        else if (args.length > 2) io.sendManyArgs(sender,
                "/bm autosave interval [interval]");
        else {
            if (args.length == 1) {
                if (has(sender, "bm.autosave.interval.get")) io.send(
                        sender,
                        io.translate("Command.Autosave.Interval.Get")
                                .replaceAll("%interval%",
                                        config.getString("Autosave.Interval")));
            } else if (args.length == 2) {
                if (has(sender, "bm.autosave.interval.set")) {
                    io.send(sender,
                            io.translate("Command.Autosave.Interval.Set")
                                    .replaceAll(
                                            "%interval%",
                                            config.getString("Autosave.Interval")));
                    config.set("Autosave.Interval", args[1]);
                    io.sendTranslation(sender, "Command.Autosave.Restart");
                    ThreadManager.stopThread(ThreadType.AUTOSAVE);
                    ThreadManager.startThread(ThreadType.AUTOSAVE);
                    io.sendTranslation(sender, "Plugin.Done");
                }
            } else io.sendManyArgs(sender, "/bm autosave interval [interval]");
        }
        return true;
    }
}
