package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;

import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;

public abstract class BmCommand {
	
	private static IOManager io;
	private static Permissions permHandler;
	
	public void initialize() {
		io = new IOManager();
		permHandler = new Permissions();
	}
	public abstract void shutdown();
	public abstract void cmd(CommandSender sender, String[] args);
}
