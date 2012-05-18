package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmDatabase;
import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;

public class BmPluginInstall {

	private static BmIOManager io;
	private static BmConfiguration config;
	private static BmDatabase db;
	private static BmPermissions permHandler;
	
	public void initialize() {
		io = new BmIOManager();
		config = new BmConfiguration();
		db = new BmDatabase();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm plugin install [plugin]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm plugin install [plugin]");
			else {
				
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/plugin install [plugin]");
			else if (args.length > 2) io.sendManyArgs(sender, "/plugin install [plugin]");
			else {
				
			}
		}
	}
}
