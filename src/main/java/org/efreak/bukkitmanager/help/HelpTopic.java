package org.efreak.bukkitmanager.help;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Permissions;

public class HelpTopic {

    private String cmd = null;
    private String args = null;
    private String desc = null;
    private String perms;
    private static Configuration config;

    static {
        config = Bukkitmanager.getConfiguration();
    }

    /**
     * 
     * Creates a new HelpTopic
     * 
     * @param arg1cmd
     *            The Command Label
     * @param arg2Args
     *            The Arguments of the Command
     * @param arg3Desc
     *            The Description of what the Command does
     * @param arg4Perms
     *            The needed Permission to view the Command in the Help List
     * 
     */
    public HelpTopic(String arg1cmd, String arg2Args, String arg3Desc,
            String arg4Perms) {
        cmd = arg1cmd;
        args = arg2Args;
        desc = arg3Desc;
        perms = arg4Perms;
    }

    public String format() {
        String formatted = "";
        formatted = config.getString("IO.HelpFormat");
        if (cmd != null) formatted = formatted.replaceAll("%cmd%", cmd);
        if (args != null) formatted = formatted.replaceAll("%args%", args);
        if (desc != null) formatted = formatted.replaceAll("%desc%", desc);
        return formatted;
    }

    public boolean hasPerm(CommandSender sender) {
        return Permissions.has(sender, perms);
    }

}
