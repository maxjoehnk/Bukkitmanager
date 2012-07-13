package org.efreak1996.Bukkitmanager.Commands;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Commands.Addon.*;
import org.efreak1996.Bukkitmanager.Commands.Autobackup.*;
import org.efreak1996.Bukkitmanager.Commands.Automessage.*;
import org.efreak1996.Bukkitmanager.Commands.Autosave.*;
import org.efreak1996.Bukkitmanager.Commands.Bukkit.*;
import org.efreak1996.Bukkitmanager.Commands.General.*;
import org.efreak1996.Bukkitmanager.Commands.Player.*;
import org.efreak1996.Bukkitmanager.Commands.Plugin.*;

public class BmCommandExecutor implements CommandExecutor {
	
	private static IOManager io;
	private static HashMap<String, Command> commands;
	
	public BmCommandExecutor() {
		io = new IOManager();
		commands = new HashMap<String, Command>();
		io.sendConsole(io.translate("Plugin.LoadingCommands"));
		//Generalcommands
		registerCommand(new LanguageCmd());
		registerCommand(new BackupCmd());
		registerCommand(new SaveCmd());
		if (Bukkitmanager.firstRun) registerCommand(new InstallCmd());
		//Addoncommands
		//new AddonCommand();
		//Autobackupcommands
		new AutobackupCommand();
		registerCommand(new AutobackupBackupCmd());
		registerCommand(new AutobackupIntervalCmd());
		registerCommand(new AutobackupRestartCmd());
		registerCommand(new AutobackupStartCmd());
		registerCommand(new AutobackupStopCmd());
		//Autosavecommands
		new AutosaveCommand();
		registerCommand(new AutosaveSaveCmd());
		registerCommand(new AutosaveIntervalCmd());
		registerCommand(new AutosaveRestartCmd());
		registerCommand(new AutosaveStartCmd());
		registerCommand(new AutosaveStopCmd());
		//Automessagecommands
		new AutomessageCommand();
		registerCommand(new AutomessageAddCmd());
		registerCommand(new AutomessageGetCmd());
		registerCommand(new AutomessageIntervalCmd());
		registerCommand(new AutomessageListCmd());
		registerCommand(new AutomessageRemoveCmd());
		registerCommand(new AutomessageRestartCmd());
		registerCommand(new AutomessageSendCmd());
		registerCommand(new AutomessageStartCmd());
		registerCommand(new AutomessageStopCmd());
		//Bukkitcommands
		new BukkitCommand();
		registerCommand(new BukkitConfigCmd());
		registerCommand(new BukkitInfoCmd());
		//Playercommands
		new PlayerCommand();
		registerCommand(new PlayerChatCmd());
		registerCommand(new PlayerCmdCmd());
		registerCommand(new PlayerDisplaynameCmd());
		//Plugincommands
		new PluginCommand();
		registerCommand(new PluginConfigCmd());
		registerCommand(new PluginDisableCmd());
		registerCommand(new PluginEnableCmd());
		registerCommand(new PluginInfoCmd());
		//registerCommand(new PluginInstallCmd());
		registerCommand(new PluginListCmd());
		registerCommand(new PluginLoadCmd());
		registerCommand(new PluginRestartCmd());
		registerCommand(new PluginUpdateCmd());
		io.sendConsole(io.translate("Plugin.CommandsLoaded"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
		Player p = null;
		ConsoleCommandSender c = null;
		if (sender instanceof Player) p = (Player) sender;
		if (sender instanceof ConsoleCommandSender) c = (ConsoleCommandSender) sender;
		if (p == c) return false;
		if (args.length == 0) return false;
		//else if (args[0].equalsIgnoreCase("install")) return commands.get("install").execute(sender, args, 1);
		else if (!Arrays.asList(CommandCategory.values()).contains(args[0].toUpperCase()) && commands.containsKey(args[0])) return commands.get(args[0]).execute(sender, args, 1);
		else if (CommandCategory.valueOf(args[0].toUpperCase()).equals(commands.get(args[1]).getCategory()) && commands.containsKey(args[1])) return commands.get(args[1]).execute(sender, args, 1);
		else return false;
	}
	
	public static void registerCommand(Command command) {
		commands.put(command.getLabel(), command);
		if (!command.getCategory().equals(CommandCategory.GENERAL)) {
			switch (command.getCategory()) {
				case ADDON:
					AddonCommand.registerCommand(command);
				case AUTOBACKUP:
					AutobackupCommand.registerCommand(command);
				case AUTOSAVE:
					AutosaveCommand.registerCommand(command);
				case AUTOMESSAGE:
					AutomessageCommand.registerCommand(command);
				case BUKKIT:
					BukkitCommand.registerCommand(command);
				case PLAYER:
					PlayerCommand.registerCommand(command);
				case PLUGIN:
					PluginCommand.registerCommand(command);
				case GENERAL:
					break;
				default:
					break;
			}
		}
	}
}
