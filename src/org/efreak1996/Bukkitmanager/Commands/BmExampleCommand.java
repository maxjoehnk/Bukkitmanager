package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.Database;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;

public class BmExampleCommand {

	private static IOManager io;
	private static Configuration config;
	private static Database db;
	private static Permissions permHandler;
	private static Plugin plugin;
	
	public void initialize() {
		io = new IOManager();
		config = new Configuration();
		db = new Database();
		permHandler = new Permissions();
		plugin = Bukkitmanager.getInstance();
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
