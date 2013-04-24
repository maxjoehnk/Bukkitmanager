package org.efreak.bukkitmanager.commands.automessage;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutomessageStopCmd extends Command {

    public AutomessageStopCmd() {
        super("stop", "Automessage.Stop", "bm.automessage.stop",
                new ArrayList<String>(), CommandCategory.AUTOMESSAGE);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/bm automessage stop");
        else if (args.length > 1) io.sendManyArgs(sender,
                "/bm automessage stop");
        else {
            if (has(sender, "bm.automessage.stop")) {
                ThreadManager.stopThread(ThreadType.AUTOMESSAGE);
                io.sendTranslation(sender, "Command.Automessage.Stop");
            }
        }
        return true;
    }
}
