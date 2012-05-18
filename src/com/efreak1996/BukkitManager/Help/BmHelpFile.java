package com.efreak1996.BukkitManager.Help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmPlugin;

public class BmHelpFile {

	private static BmConfiguration config;
	private File helpFile;
	private YamlConfiguration help;
	
	public void initialize() {
		config = new BmConfiguration();
		helpFile = new File(BmPlugin.getPlugin().getDataFolder(), "help.yml");
		help = new YamlConfiguration();
		try {
			if (!helpFile.exists()) helpFile.createNewFile();
			help.load(helpFile);
			addContent();
			help.save(helpFile);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	private void addContent() {
		update("Help", "Helps you:D");
		update("Autobackup.Backup", "Performs a Backup");
		update("Autobackup.Start", "Starts the Autobackupthread");
		update("Autobackup.Stop", "Stops the Autobackupthread");
		update("Autobackup.Restart", "Restarts the Autobackupthread");
		update("Autobackup.Interval", "Gets/Sets the Autobackup Interval");
		
		update("Automessage.Add", "Lists all Messages");
		update("Automessage.Remove", "Removes a Message");
		update("Automessage.Get", "Gets a Message");
		update("Automessage.List", "Lists all Messages");
		update("Automessage.Send", "Sends a Message");
		update("Automessage.Start", "Starts the Automessagethread");
		update("Automessage.Stop", "Stops the Automessagethread");
		update("Automessage.Restart", "Restarts the Automessagethread");
		update("Automessage.Interval", "Gets/Sets the Automessage Interval");	
		
		update("Autosave.Save", "Performs a Save");
		update("Autosave.Start", "Starts the Autosavethread");
		update("Autosave.Stop", "Stops the Autosavethread");
		update("Autosave.Restart", "Restarts the Autosavethread");
		update("Autosave.Interval", "Gets/Sets the Autosave Interval");
		
		update("Bukkit.Config", "Modify the server.properties");
		update("Bukkit.Info", "Shows info about Bukkit");
		
		update("Language.Get", "Gets the Language");
		update("Language.Set", "Sets the Language");
		
		update("Player.Chat", "Send a Message with another Player");
		update("Player.Cmd", "Perform a Command with another Player");
		update("Player.Displayname.Get", "Get the Players Displayname");
		update("Player.Displayname.Set", "Set the Players Displayname");
		update("Player.Displayname.Reset", "Reset the Players Displayname");
		update("Player.Exp.Get", "Gets the Experience of a Player");
		update("Player.Exp.Set", "Sets the Experience of a Player");
		update("Player.Exp.Add", "Adds Experience to the Players Exp");
		update("Player.Firstseen", "Gets the first Online Time of the Player");
		update("Player.Food.Get", "Get the Foodlevel of a Player");
		update("Player.Food.Set", "Set the Foodlevel of a Player");
		update("Player.Food.Add", "Adds Food to the Players Foodlevel");
		update("Player.Food.Remove", "Removes Food of the Players Foodlevel");
		update("Player.Gamemode", "Changes the Gamemode");
		update("Player.Health.Get", "Get the Health of a Player");
		update("Player.Health.Set", "Set the Health of a Player");
		update("Player.Health.Add", "Adds Health to the Players Health");
		update("Player.Health.Remove", "Removes Health of the Players Health");
		update("Player.Hide", "Hides a Player");
		update("Player.Lastseen", "Gets the last Online Time of the Player");
		update("Player.Level.Get", "Get the Level of a Player");
		update("Player.Level.Set", "Set the Level of a Player");
		update("Player.Listname.Get", "Get the Players Listname");
		update("Player.Listname.Set", "Set the Players Listname");
		update("Player.Listname.Reset", "Reset the Players Listname");
		update("Player.Load", "Loads the Player.dat");
		update("Player.Save", "Saves the Player.dat");
		update("Player.Show", "Shows a Player");
		update("Player.Time.Get", "Get the Players Time");
		update("Player.Time.Set", "Set the Players Time");
		update("Player.Time.Reset", "Reset the Players Time");

		update("Plugin.Config", "Modify Plugins Configs");
		update("Plugin.Disable", "Disable Plugins");
		update("Plugin.Enable", "Enable Plugins");
		update("Plugin.Info", "Shows info about a Plugin");
		update("Plugin.List", "Lists all Plugins");
		update("Plugin.Load", "Loads Plugins without a reload");
		update("Plugin.Restart", "Restarts a Plugin");
	}
	
	public String getHelp(String key) {
		return help.getString(key);
	}
	
	public boolean update(String path, Object value) {
		if (!help.contains(path)) {
			help.set(path, value);
			return false;
		}else return true;
	}
}
