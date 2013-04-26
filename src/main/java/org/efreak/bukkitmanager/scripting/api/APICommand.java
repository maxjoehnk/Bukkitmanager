package org.efreak.bukkitmanager.scripting.api;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.scripting.ScriptManager;

public class APICommand {

	private String function;
	private String perm;
	
	public APICommand(String function, String perm) {
		this.function = function;
		this.perm = perm;
	}
	
	public APICommand(String function) {
		this.function = function;
		perm = null;
	}
	
	public boolean execute(CommandSender sender, String[] args) {
		if (perm != null) {
			if (Permissions.has(sender, perm)) ScriptManager.runFunction(function, args);
		}else ScriptManager.runFunction(function, args);
		return true;
	}
	
}
