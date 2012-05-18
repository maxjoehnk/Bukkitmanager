package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;

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
