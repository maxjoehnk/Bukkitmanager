package org.efreak.bukkitmanager.commands.autosave;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.util.SaveHelper;

public class AutosaveSaveCmd extends Command {

    private static SaveHelper saveHelper;

    public AutosaveSaveCmd() {
        super("save", "Autosave.Save", "bm.autosave.save",
                new ArrayList<String>(), CommandCategory.AUTOSAVE);
        saveHelper = new SaveHelper();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/bm autosave save");
        else if (args.length > 1) io.sendManyArgs(sender, "/bm autosave save");
        else {
            if (has(sender, "bm.autosave.backup")) saveHelper.performSave();
        }
        return true;
    }
}
