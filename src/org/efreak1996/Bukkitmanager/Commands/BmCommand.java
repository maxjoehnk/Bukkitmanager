package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.Util.BmIOManager;
import org.efreak1996.Bukkitmanager.BmPermissions;

public abstract class BmCommand {
	
	private static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	public abstract void shutdown();
	public abstract void cmd(CommandSender sender, String[] args);
}
