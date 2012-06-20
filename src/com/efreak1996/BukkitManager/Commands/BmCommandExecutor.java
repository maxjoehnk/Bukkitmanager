package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmCommandExecutor implements CommandExecutor {
	
	public static Player p;
	public static ConsoleCommandSender c;
	private static BmIOManager io;
	private static Plugin plugin;
	private static BmConfiguration config;
	private static BmPermissions permHandler;
	private static BmAutomessage automessageCmd;
	private static BmAutosave autosaveCmd;
	private static BmAutobackup autobackupCmd;
	private static BmPlugin pluginCmd;
	private static BmBukkit bukkitCmd;
	private static BmHelp helpCmd;
	private static BmPlayer playerCmd;
	private static BmLanguage langCmd;
	private static BmPassword passwordCmd;
	private static BmBukkitCommand bukkitCommand;
	private static BmPlayerCommand playerCommand;
	private static BmPluginCommand pluginCommand;
	private static BmLanguageCommand langCommand;
	
	public BmCommandExecutor() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
		config = new BmConfiguration();
		plugin = com.efreak1996.BukkitManager.BmPlugin.getPlugin();
		io.sendConsole(io.translate("Plugin.LoadingCommands"));
		bukkitCmd = new BmBukkit();
		bukkitCmd.initialize();
		pluginCmd = new BmPlugin();
		pluginCmd.initialize();
		automessageCmd = new BmAutomessage();
		automessageCmd.initialize();
		autosaveCmd = new BmAutosave();
		autosaveCmd.initialize();
		autobackupCmd = new BmAutobackup();
		autobackupCmd.initialize();
		helpCmd = new BmHelp();
		helpCmd.initialize();
		playerCmd = new BmPlayer();
		playerCmd.initialize();
		langCmd = new BmLanguage();
		langCmd.initialize();
		if (config.getDev()) {
			io.sendConsoleDev("Loading Development Commands...");
			passwordCmd = new BmPassword();
			passwordCmd.initialize();
			io.sendConsoleDev("Development Commands Loaded!");
		}
		io.sendConsole(io.translate("Plugin.LoadingAliases"));
		if (config.getBoolean("General.Aliases.Bukkit")) {
			bukkitCommand = new BmBukkitCommand("bukkit", plugin);
			((CraftServer) plugin.getServer()).getCommandMap().register("bukkit", bukkitCommand);
		}
		if (config.getBoolean("General.Aliases.Plugin")) {
			pluginCommand = new BmPluginCommand("plugin", plugin);
			((CraftServer) plugin.getServer()).getCommandMap().register("plugin", pluginCommand);
		}
		if (config.getBoolean("General.Aliases.Player")) {
			playerCommand = new BmPlayerCommand("player", plugin);
			((CraftServer) plugin.getServer()).getCommandMap().register("player", playerCommand);
		}
		if (config.getBoolean("General.Aliases.Language")) {
			langCommand = new BmLanguageCommand("lang", plugin);
			((CraftServer) plugin.getServer()).getCommandMap().register("lang", langCommand);
		}
		io.sendConsole(io.translate("Plugin.AliasesLoaded"));
		io.sendConsole(io.translate("Plugin.CommandsLoaded"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		p = null;
		c = null;
		if (sender instanceof Player) p = (Player) sender;
		if (sender instanceof ConsoleCommandSender) c = (ConsoleCommandSender) sender;
		if (p == c) return false;
		if (args.length == 0) return false;
		else if (args[0].equalsIgnoreCase("bukkit")) bukkitCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("plugin")) pluginCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("player")) playerCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("lang")) langCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("language")) langCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("automessage")) automessageCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("autosave")) autosaveCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("autobackup")) autobackupCmd.cmd(sender, args);
		else if (args[0].equalsIgnoreCase("password")) {
			if (config.getDev()) passwordCmd.cmd(sender, args);
			else return false;
		}else if (args[0].equalsIgnoreCase("help")) helpCmd.cmd(sender, args);
		else return false;
		return true;
	}	
}
