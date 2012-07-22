package org.efreak1996.Bukkitmanager.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;
import org.efreak1996.Bukkitmanager.Help.HelpManager;

public abstract class Command {
	
	protected String label;
	protected String helpNode;
	protected String helpPerm;
	protected List<String> args;
	protected CommandCategory category;
	protected static IOManager io;
	protected static Configuration config;
	
	public Command(String arg1Label, String arg2HelpNode, String arg3HelpPermission, List<String> arg4Args, CommandCategory arg5Category) {
		label = arg1Label;
		helpNode = arg2HelpNode;
		helpPerm = arg3HelpPermission;
		args = arg4Args;
		category = arg5Category;
		io = new IOManager();
		config = new Configuration();
		HelpManager.registerCommand(this);
	}
	
	public abstract boolean execute(CommandSender sender, String[] args, Integer length);
	
	protected boolean has(CommandSender sender, String perm) {
		return Permissions.has(sender, perm);
	}
	
	public CommandCategory getCategory() {
		return category;
	}
	
	public String getLabel() {
		return label;
	}
	
	public List<String> getArgs() {
		return args;
	}
	
	public String getHelpNode() {
		return helpNode;
	}
	
	public String getHelpPermission() {
		return helpPerm;
	}
}
