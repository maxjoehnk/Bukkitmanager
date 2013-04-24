package org.efreak.bukkitmanager.commands.autosave;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutosaveStopCmd extends Command {

    public AutosaveStopCmd() {
        super("stop", "Autosave.Stop", "bm.autosave.stop",
                new ArrayList<String>(), CommandCategory.AUTOSAVE);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/bm autosave stop");
        else if (args.length > 1) io.sendManyArgs(sender, "/bm autosave stop");
        else {
            if (has(sender, "bm.autosave.stop")) {
                ThreadManager.stopThread(ThreadType.AUTOSAVE);
                io.sendTranslation(sender, "Command.Autosave.Stop");
            }
        }
        return true;
    }
}
