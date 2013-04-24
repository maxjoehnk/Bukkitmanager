package org.efreak.bukkitmanager.commands.automessage;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageIntervalCmd extends Command {

    public AutomessageIntervalCmd() {
        super("interval", "Automessage.Interval", "bm.automessage.interval",
                Arrays.asList("[interval]"), CommandCategory.AUTOMESSAGE);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender,
                "/bm automessage interval [interval]");
        else if (args.length > 2) io.sendManyArgs(sender,
                "/bm automessage interval [interval]");
        else {
            if (args.length == 1) {
                if (has(sender, "bm.automessage.interval.get")) io
                        .send(sender,
                                io.translate("Command.Automessage.Interval.Get")
                                        .replaceAll(
                                                "%interval%",
                                                config.getString("Automessage.Interval")));
            } else if (args.length == 2) {
                if (has(sender, "bm.automessage.interval.set")) {
                    io.send(sender,
                            io.translate("Command.Automessage.Interval.Set")
                                    .replaceAll(
                                            "%interval%",
                                            config.getString("Automessage.Interval")));
                    config.set("Automessage.Interval", args[1]);
                    io.sendTranslation(sender, "Command.Automessage.Restart");
                    ThreadManager.stopThread(ThreadType.AUTOMESSAGE);
                    ThreadManager.startThread(ThreadType.AUTOMESSAGE);
                    io.sendTranslation(sender, "Plugin.Done");
                }
            } else io.sendManyArgs(sender,
                    "/bm automessage interval [interval]");
        }
        return true;
    }
}
