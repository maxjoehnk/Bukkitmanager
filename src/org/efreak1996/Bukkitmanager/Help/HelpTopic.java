package org.efreak1996.Bukkitmanager.Help;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.Permissions;


public class HelpTopic {

	private String cmd = null;
	private String args = null;
	private String desc = null;
	private String perms;
	private static Configuration config;
	
	/**
	 * 
	 * Creates a new HelpTopic
	 * 
	 * @param arg1cmd The Command
	 * @param arg2Args The Arguments of the Command
	 * @param arg3Perms The needed Permission
	 */
	public HelpTopic(String arg1cmd, String arg2Args, String arg3Desc, String arg4Perms) {
		cmd = arg1cmd;
		args = arg2Args;
		desc = arg3Desc;
		perms = arg4Perms;
		config = new Configuration();
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
		return Permissions.has(sender, perms, false);
	}
	
}
