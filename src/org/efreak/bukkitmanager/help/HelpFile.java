package org.efreak.bukkitmanager.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;

public class HelpFile {

	private static Configuration config;
	private File helpFile;
	private YamlConfiguration help;
	
	public void initialize() {
		config = Bukkitmanager.getConfiguration();
		helpFile = new File(Bukkitmanager.getInstance().getDataFolder(), "help.yml");
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
		update("Language", "Gets/Sets the Language");
		update("Filebrowser", "A simple Filebrowser, based on UNIX Commands");
		update("Install", "Configure Bukkitmanager for the first time");
		update("Password", "Gets/Sets the Remote Password");
		
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
		update("Bukkit.Restart", "Restarts Bukkit");
				
		update("Player.Chat", "Send a Message with another Player");
		update("Player.Cmd", "Perform a Command with another Player");
		update("Player.Displayname.Get", "Get the Players Displayname");
		update("Player.Displayname.Set", "Set the Players Displayname");
		update("Player.Displayname.Reset", "Reset the Players Displayname");
		update("Player.Exp", "Modify the Experience of a Player");
		update("Player.Firstseen", "Gets the first Online Time of the Player");
		update("Player.Food", "Modify the Foodlevel of a Players");
		update("Player.Gamemode", "Changes the Gamemode");
		update("Player.Health", "Modify the Health of a Players");
		update("Player.Hide", "Hides a Player");
		update("Player.Lastseen", "Gets the last Online Time of the Player");
		update("Player.Level", "Get/Set the Level of a Player");
		update("Player.Listname", "Modify the Players Listname");
		update("Player.Load", "Loads the Player.dat");
		update("Player.Save", "Saves the Player.dat");
		update("Player.Show", "Shows a Player");
		update("Player.Time", "Modify the Players Time");

		update("Plugin.Config", "Modify Plugins Configs");
		update("Plugin.Disable", "Disable Plugins");
		update("Plugin.Enable", "Enable Plugins");
		update("Plugin.Info", "Shows info about a Plugin");
		update("Plugin.Install", "Installs a new Plugin");
		update("Plugin.List", "Lists all Plugins");
		update("Plugin.Load", "Loads Plugins without a reload");
		update("Plugin.Restart", "Restarts a Plugin");
		update("Plugin.Update", "Updates a Plugin");
		
		update("Server.Info", "Gives you general Server Information");
		update("Server.Network", "Gives you informations about the Network Adapter");
		update("Server.Load", "Displays the Serverload");		
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
