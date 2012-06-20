package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmBukkitCommandExecutor implements CommandExecutor {
	
	public static BmIOManager io;
	public static BmBukkitConfig Config;
	public static BmBukkitInfo Info;

	public BmBukkitCommandExecutor() {
		io = new BmIOManager();
		Config = new BmBukkitConfig();
		Info = new BmBukkitInfo();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(args.length == 0)) {
			if (args[0].equalsIgnoreCase("info")) Info.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("config")) Config.cmd(sender, args, false);
			else io.sendError(sender, "Invalid Arguments!");
		}else io.sendFewArgs(sender, "/bukkit (info|config) [Args]");
		return true;
	}

}
