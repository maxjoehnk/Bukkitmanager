package org.efreak.bukkitmanager.commands.automessage;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageStartCmd extends Command {

    public AutomessageStartCmd() {
        super("start", "Automessage.Start", "bm.automessage.start",
                new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/bm automessage start");
        else if (args.length > 1) io.sendManyArgs(sender,
                "/bm automessage start");
        else {
            if (has(sender, "bm.automessage.start")) {
                ThreadManager.startThread(ThreadType.AUTOMESSAGE);
                io.sendTranslation(sender, "Command.Automessage.Start");
            }
        }
        return true;
    }
}
