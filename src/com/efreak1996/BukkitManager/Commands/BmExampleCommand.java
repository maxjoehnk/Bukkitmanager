package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmDatabase;
import com.efreak1996.BukkitManager.Util.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.BmPlugin;

public class BmExampleCommand {

	private static BmIOManager io;
	private static BmConfiguration config;
	private static BmDatabase db;
	private static BmPermissions permHandler;
	private static Plugin plugin;
	
	public void initialize() {
		io = new BmIOManager();
		config = new BmConfiguration();
		db = new BmDatabase();
		permHandler = new BmPermissions();
		plugin = BmPlugin.getPlugin();
	}
	public void shutdown() {}

	/*public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < ) io.sendFewArgs(sender, "/bm ");
			else if (args.length > ) io.sendManyArgs(sender, "/bm ");
			else {
			
			}
		}else {
			if (args.length < ) io.sendFewArgs(sender, "/");
			else if (args.length > ) io.sendManyArgs(sender, "/");
			else {
			
			}
		}
	}*/
}
