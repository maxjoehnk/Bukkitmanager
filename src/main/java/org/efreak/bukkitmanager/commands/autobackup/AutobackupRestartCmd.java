package org.efreak.bukkitmanager.commands.autobackup;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class AutobackupRestartCmd extends Command {

    public AutobackupRestartCmd() {
        super("restart", "Autobackup.Restart", "bm.autobackup.restart",
                new ArrayList<String>(), CommandCategory.AUTOBACKUP);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/bm autobackup restart");
        else if (args.length > 1) io.sendManyArgs(sender,
                "/bm autobackup restart");
        else {
            if (has(sender, "bm.autobackup.restart")) {
                io.sendTranslation(sender, "Command.Autobackup.Restart");
                ThreadManager.stopThread(ThreadType.AUTOBACKUP);
                ThreadManager.startThread(ThreadType.AUTOBACKUP);
            }
        }
        return true;
    }
}
