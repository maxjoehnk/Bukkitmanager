package org.efreak1996.Bukkitmanager.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.BmPermissions;


public abstract class Command {
	
	protected String label;
	protected String helpNode;
	protected List<Object> args;
	
	public Command(String arg1Label, String arg2HelpNode, List<Object> arg3Args) {
		label = arg1Label;
		helpNode = arg2HelpNode;
		args = arg3Args;
	}
	
	public abstract boolean execute(CommandSender sender);
	
	private boolean hasPerm(CommandSender sender, String perm) {
		return BmPermissions.has(sender, perm);
	}
	
}
