package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmDatabase;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmPluginUpdate {

	private static BmIOManager io;
	private static BmConfiguration config;
	private static BmDatabase db;
	private static BmPermissions permHandler;
	
	public void initialize() {
		io = new BmIOManager();
		db = new BmDatabase();
		config = new BmConfiguration();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}
		
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm plugin update [plugin|all]");
			else if (args.length > 3) io.sendManyArgs(sender, "/bm plugin update [plugin|all]");
			else {
				
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/plugin update [plugin|all]");
			else if (args.length > 2) io.sendManyArgs(sender, "/plugin update [plugin|all]");
			else {
				
			}
		}
	}
}
