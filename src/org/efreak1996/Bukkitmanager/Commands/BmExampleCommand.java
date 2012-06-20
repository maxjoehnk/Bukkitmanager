package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmDatabase;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.BmPlugin;

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
