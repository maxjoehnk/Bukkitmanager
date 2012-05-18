package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmIOManager;
import com.efreak1996.BukkitManager.BmPermissions;

public class BmBukkit {
	
	public static BmIOManager io;
	public static BmBukkitConfig Config;
	public static BmBukkitInfo Info;
	public static BmPermissions permHandler;

	public void initialize() {
		io = new BmIOManager();
		Config = new BmBukkitConfig();
		Info = new BmBukkitInfo();
		permHandler = new BmPermissions();
		Config.initialize();
		Info.initialize();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args) {
		if (!(args.length == 1)) {
			if (args[1].equalsIgnoreCase("info")) Info.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("config")) Config.cmd(sender, args, true);
			else io.sendError(sender, "Invalid Arguments!");
		}else io.sendFewArgs(sender, "/bm bukkit (info|config) [Args]");
	}
}
