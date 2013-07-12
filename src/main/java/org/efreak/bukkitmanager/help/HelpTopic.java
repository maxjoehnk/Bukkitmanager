package org.efreak.bukkitmanager.help;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Permissions;


public class HelpTopic {

	private String cmd = null;
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
	 * @param arg1cmd The Command Label
	 * @param arg2Args The Arguments of the Command
	 * @param arg3Desc The Description of what the Command does
	 * @param arg4Perms The needed Permission to view the Command in the Help List
	 * 
	 */
	public HelpTopic(String arg1cmd, String arg2Desc, String arg3Perms) {
		cmd = arg1cmd;
		desc = arg2Desc;
		perms = arg3Perms;
	}
	
	public String format() {
		String formatted = "";
		formatted = config.getString("IO.HelpFormat");
		formatted = formatted.replaceAll("%args%", "");
		if (cmd != null) formatted = formatted.replaceAll("%cmd%", cmd);
		if (desc != null) formatted = formatted.replaceAll("%desc%", desc);
		else formatted = formatted.replaceAll("%desc%", "No Help Found");
		return formatted;
	}
	
	public String getCommand() {
		return cmd;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public boolean hasPerm(CommandSender sender) {
		return Permissions.has(sender, perms);
	}
	
}
