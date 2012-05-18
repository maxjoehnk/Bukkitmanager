package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmIOManager;

public class BmPlayerCommandExecutor implements CommandExecutor {
	
	private static BmPlayerHide hideCmd;
	private static BmPlayerShow showCmd;
	private static BmPlayerLevel levelCmd;
	private static BmPlayerExp expCmd;
	private static BmPlayerTime timeCmd;
	private static BmPlayerSave saveCmd;
	private static BmPlayerLoad loadCmd;
	private static BmPlayerChat chatCmd;
	private static BmPlayerCmd cmdCmd;
	private static BmPlayerListname listnameCmd;
	private static BmPlayerDisplayname displaynameCmd;
	private static BmPlayerFirstseen firstseenCmd;
	private static BmPlayerLastseen lastseenCmd;
	private static BmPlayerGamemode gamemodeCmd;
	private static BmPlayerHealth healthCmd;
	private static BmPlayerFood foodCmd;
	private static BmIOManager io;

	public BmPlayerCommandExecutor() {
		io = new BmIOManager();
		hideCmd = new BmPlayerHide();
		showCmd = new BmPlayerShow();
		levelCmd = new BmPlayerLevel();
		expCmd = new BmPlayerExp();
		timeCmd = new BmPlayerTime();
		saveCmd = new BmPlayerSave();
		loadCmd = new BmPlayerLoad();
		chatCmd = new BmPlayerChat();
		cmdCmd = new BmPlayerCmd();
		listnameCmd = new BmPlayerListname();
		displaynameCmd = new BmPlayerDisplayname();
		firstseenCmd = new BmPlayerFirstseen();
		lastseenCmd = new BmPlayerLastseen();
		gamemodeCmd = new BmPlayerGamemode();
		healthCmd = new BmPlayerHealth();
		foodCmd = new BmPlayerFood();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(args.length == 0)) {
			if (args[0].equalsIgnoreCase("hide")) hideCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("show")) showCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("level")) levelCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("exp")) expCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("time")) timeCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("save")) saveCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("load")) loadCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("chat")) chatCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("cmd")) cmdCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("health")) healthCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("food")) foodCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("listname")) listnameCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("displayname")) displaynameCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("firstseen")) firstseenCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("lastseen")) lastseenCmd.cmd(sender, args, false);
			else if (args[0].equalsIgnoreCase("gamemode")) gamemodeCmd.cmd(sender, args, false);
			else io.sendError(sender, "Invalid Arguments!");
		}else io.sendFewArgs(sender, "/player (Args)");
		return true;
	}

}
