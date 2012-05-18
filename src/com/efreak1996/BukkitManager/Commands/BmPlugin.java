package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmIOManager;

public class BmPlugin {
	
	public static BmIOManager io;
	public static BmPluginConfig configCmd;
	public static BmPluginDisable disableCmd;
	public static BmPluginEnable enableCmd;
	public static BmPluginInfo infoCmd;
	public static BmPluginInstall installCmd;
	public static BmPluginList listCmd;
	public static BmPluginRestart restartCmd;
	public static BmPluginUpdate updateCmd;
	public static BmPluginLoad loadCmd;

	public void initialize() {
		io = new BmIOManager();
		configCmd = new BmPluginConfig();
		configCmd.initialize();
		disableCmd = new BmPluginDisable();
		disableCmd.initialize();
		enableCmd = new BmPluginEnable();
		enableCmd.initialize();
		infoCmd = new BmPluginInfo();
		infoCmd.initialize();
		installCmd = new BmPluginInstall();
		installCmd.initialize();
		listCmd = new BmPluginList();
		listCmd.initialize();
		restartCmd = new BmPluginRestart();
		restartCmd.initialize();
		updateCmd = new BmPluginUpdate();
		updateCmd.initialize();
		loadCmd = new BmPluginLoad();
		loadCmd.initialize();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin [Args]");
		else {
			if (args[1].equalsIgnoreCase("list")) listCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("info")) infoCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("enable")) enableCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("disable")) disableCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("restart")) restartCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("install")) installCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("update")) updateCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("config")) configCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("load")) loadCmd.cmd(sender, args, true);
		} 
	}
}
