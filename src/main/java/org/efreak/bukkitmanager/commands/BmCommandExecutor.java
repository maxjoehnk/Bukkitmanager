package org.efreak.bukkitmanager.commands;

import java.util.HashMap;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.IOManager;
//import org.efreak.bukkitmanager.Commands.Addon.*;
import org.efreak.bukkitmanager.commands.autobackup.*;
import org.efreak.bukkitmanager.commands.automessage.*;
import org.efreak.bukkitmanager.commands.autosave.*;
import org.efreak.bukkitmanager.commands.bukkit.*;
import org.efreak.bukkitmanager.commands.general.*;
import org.efreak.bukkitmanager.commands.player.*;
import org.efreak.bukkitmanager.commands.plugin.*;
import org.efreak.bukkitmanager.commands.server.*;

public class BmCommandExecutor implements CommandExecutor {
	
	private static IOManager io;
	private static HashMap<String, Command> commands;
	private static HashMap<String, Alias> aliases;
	
	public BmCommandExecutor() {
		io = Bukkitmanager.getIOManager();
		commands = new HashMap<String, Command>();
		aliases = new HashMap<String, Alias>();
		io.sendConsole(io.translate("Plugin.LoadingCommands"));
		//Generalcommands
		registerCommand(new PasswordCmd());
		registerCommand(new LanguageCmd());
		registerCommand(new BackupCmd());
		registerCommand(new SaveCmd());
		registerCommand(new HelpCmd());
		//registerCommand(new FilebrowserCmd());
		if (Bukkitmanager.firstRun) registerCommand(new InstallCmd());
		//Addoncommands
		//new AddonCommand();
		//Autobackupcommands
		registerAlias("autobackup", new AutobackupCommand());
		//registerCommand(new AutobackupCmd());
		registerCommand(new AutobackupBackupCmd());
		registerCommand(new AutobackupIntervalCmd());
		registerCommand(new AutobackupRestartCmd());
		registerCommand(new AutobackupStartCmd());
		registerCommand(new AutobackupStopCmd());
		//Autosavecommands
		registerAlias("autosave", new AutosaveCommand());
		registerCommand(new AutosaveSaveCmd());
		registerCommand(new AutosaveIntervalCmd());
		registerCommand(new AutosaveRestartCmd());
		registerCommand(new AutosaveStartCmd());
		registerCommand(new AutosaveStopCmd());
		//Automessagecommands
		registerAlias("automessage", new AutomessageCommand());
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
		registerAlias("bukkit", new BukkitCommand());
		registerCommand(new BukkitConfigCmd());
		registerCommand(new BukkitInfoCmd());
		//registerCommand(new BukkitRestartCmd());
		//Playercommands
		registerAlias("player", new PlayerCommand());
		registerCommand(new PlayerChatCmd());
		registerCommand(new PlayerCmdCmd());
		registerCommand(new PlayerDisplaynameCmd());
		registerCommand(new PlayerExpCmd());
		registerCommand(new PlayerFirstseenCmd());
		registerCommand(new PlayerFoodCmd());
		registerCommand(new PlayerGamemodeCmd());
		registerCommand(new PlayerHealthCmd());
		registerCommand(new PlayerHideCmd());
		registerCommand(new PlayerLastseenCmd());
		registerCommand(new PlayerLevelCmd());
		registerCommand(new PlayerListnameCmd());
		registerCommand(new PlayerLoadCmd());
		registerCommand(new PlayerSaveCmd());
		registerCommand(new PlayerShowCmd());
		registerCommand(new PlayerTimeCmd());
		//Plugincommands
		registerAlias("plugin", new PluginCommand());
		registerCommand(new PluginConfigCmd());
		registerCommand(new PluginDisableCmd());
		registerCommand(new PluginEnableCmd());
		registerCommand(new PluginInfoCmd());
		registerCommand(new PluginInstallCmd());
		registerCommand(new PluginListCmd());
		registerCommand(new PluginLoadCmd());
		//registerCommand(new PluginReloadCmd());
		registerCommand(new PluginRestartCmd());
		//registerCommand(new PluginUnloadCmd());
		registerCommand(new PluginUpdateCmd());
		//Servercomands
		registerAlias("server", new ServerCommand());
		registerCommand(new ServerInfoCmd());
		registerCommand(new ServerLoadCmd());
		registerCommand(new ServerNetworkCmd());
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
		else {
			if (commands.containsKey("general." + args[0])) {
				Command command = commands.get("general." + args[0]);
				Bukkitmanager.incrementCommandTracker(command);
				return command.execute(sender, args, 1);				
			}else if (args.length > 1) {
				if (commands.containsKey(args[0] + '.' + args[1])) {
					Command command = commands.get(args[0] + "." + args[1]);
					Bukkitmanager.incrementCommandTracker(command);
					return command.execute(sender, args, 1);					
				}else return false;
			}else return false;
		}
	}
	
	public static void registerCommand(Command command) {
		//if (command instanceof MainCommand) commands.put(command.getCategory().toString().toLowerCase(), command);
		commands.put(command.getCategory().toString().toLowerCase() + "." + command.getLabel(), command);
		Alias alias = aliases.get(command.getCategory().toString().toLowerCase());
		if (alias != null) alias.registerCommand(command);
		Bukkitmanager.addCommandTracker(command);
	}
	
	public static void registerAlias(String category, Alias alias) {
		aliases.put(category, alias);
	}
}
