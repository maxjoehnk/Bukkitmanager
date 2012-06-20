package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.Database;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPluginUpdate {

	private static IOManager io;
	private static Configuration config;
	private static Database db;
	private static Permissions permHandler;
	
	public void initialize() {
		io = new IOManager();
		db = new Database();
		config = new Configuration();
		permHandler = new Permissions();
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
