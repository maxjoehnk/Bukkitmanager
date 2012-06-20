package org.efreak1996.Bukkitmanager.Help;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmPermissions;


public class BmHelpTopic {

	private String cmd;
	private String args;
	private String desc;
	private String perms;
	private static BmPermissions permHandler;
	private static BmConfiguration config;
	
	/**
	 * 
	 * Creates a new HelpTopic
	 * 
	 * @param arg1cmd The Command
	 * @param arg2Args The Arguments of the Command
	 * @param arg3Perms The needed Permission
	 */
	public BmHelpTopic(String arg1cmd, String arg2Args, String arg3Desc, String arg4Perms) {
		cmd = arg1cmd;
		args = arg2Args;
		desc = arg3Desc;
		perms = arg4Perms;
		permHandler = new BmPermissions();
		config = new BmConfiguration();
	}
	
	public String format() {
		String formatted = "";
		formatted = config.getString("IO.HelpFormat").replaceAll("%cmd%", cmd).replaceAll("%args%", args).replaceAll("%desc%", desc);
		return formatted;
	}
	
	public boolean hasPerm(CommandSender sender) {
		return permHandler.has(sender, perms, false);
	}
	
}
