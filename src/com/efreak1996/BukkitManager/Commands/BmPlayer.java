package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmPlayer {
	
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

	public void initialize() {
		io = new BmIOManager();
		hideCmd = new BmPlayerHide();
		hideCmd.initialize();
		showCmd = new BmPlayerShow();
		showCmd.initialize();
		levelCmd = new BmPlayerLevel();
		levelCmd.initialize();
		expCmd = new BmPlayerExp();
		expCmd.initialize();
		timeCmd = new BmPlayerTime();
		timeCmd.initialize();
		saveCmd = new BmPlayerSave();
		saveCmd.initialize();
		loadCmd = new BmPlayerLoad();
		loadCmd.initialize();
		chatCmd = new BmPlayerChat();
		chatCmd.initialize();
		cmdCmd = new BmPlayerCmd();
		cmdCmd.initialize();
		listnameCmd = new BmPlayerListname();
		listnameCmd.initialize();
		displaynameCmd = new BmPlayerDisplayname();
		displaynameCmd.initialize();
		firstseenCmd = new BmPlayerFirstseen();
		firstseenCmd.initialize();
		lastseenCmd = new BmPlayerLastseen();
		lastseenCmd.initialize();
		gamemodeCmd = new BmPlayerGamemode();
		gamemodeCmd.initialize();
		healthCmd = new BmPlayerHealth();
		healthCmd.initialize();
		foodCmd = new BmPlayerFood();
		foodCmd.initialize();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args) {
		if (!(args.length == 1)) {
			if (args[1].equalsIgnoreCase("hide")) hideCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("show")) showCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("level")) levelCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("exp")) expCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("time")) timeCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("save")) saveCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("load")) loadCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("chat")) chatCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("cmd")) cmdCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("health")) healthCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("food")) foodCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("listname")) listnameCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("displayname")) displaynameCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("firstseen")) firstseenCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("lastseen")) lastseenCmd.cmd(sender, args, true);
			else if (args[1].equalsIgnoreCase("gamemode")) gamemodeCmd.cmd(sender, args, true);
			else io.sendError(sender, "Invalid Arguments!");
		}else io.sendFewArgs(sender, "/bm player [Args]");
	}
}
