package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.IOManager;


public class BmPluginCommandExecutor implements CommandExecutor {
	
	public static IOManager io;
	public static BmPluginConfig configCmd;
	public static BmPluginDisable disableCmd;
	public static BmPluginEnable enableCmd;
	public static BmPluginInfo infoCmd;
	public static BmPluginInstall installCmd;
	public static BmPluginList listCmd;
	public static BmPluginRestart restartCmd;
	public static BmPluginUpdate updateCmd;
	public static BmPluginLoad loadCmd;

	public BmPluginCommandExecutor() {
		io = new IOManager();
		configCmd = new BmPluginConfig();
		disableCmd = new BmPluginDisable();
		enableCmd = new BmPluginEnable();
		infoCmd = new BmPluginInfo();
		installCmd = new BmPluginInstall();
		listCmd = new BmPluginList();
		restartCmd = new BmPluginRestart();
		updateCmd = new BmPluginUpdate();
		loadCmd = new BmPluginLoad();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(args.length == 0)) {
			if (args[0].equalsIgnoreCase("list")) listCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("info")) infoCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("enable")) enableCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("disable")) disableCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("restart")) restartCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("install")) installCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("update")) updateCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("config")) configCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("load")) loadCmd.cmd(sender, args, false);
		}else io.sendFewArgs(sender, "/plugin (Args)");
		return true;
	}

}
