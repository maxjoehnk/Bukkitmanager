package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmBukkit {
	
	public static IOManager io;
	public static BmBukkitConfig Config;
	public static BmBukkitInfo Info;
	public static Permissions permHandler;

	public void initialize() {
		io = new IOManager();
		Config = new BmBukkitConfig();
		Info = new BmBukkitInfo();
		permHandler = new Permissions();
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
