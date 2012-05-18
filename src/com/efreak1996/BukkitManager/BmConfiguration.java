package com.efreak1996.BukkitManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * 
 * Loads and manages the config File
 * 
 * @author efreak1996
 *
 */

public class BmConfiguration{
	 
	private static BmIOManager io;
	private static Plugin plugin;
	private static FileConfiguration config;
	private static File configFile;
	private static boolean devMode;
	private static String dbType = "SQLite";
	private static String backupMode = "SYNC";
	private static String saveMode = "SYNC";
	
	/**
	 * 
	 * Loads and creates if needed the config
	 *
	 */
	
	public void initalize() {
		plugin = BmPlugin.getPlugin();
		configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
		io = new BmIOManager();
		io.plugin = plugin;
		config = plugin.getConfig();
		if (!(configFile.exists())){
			io.sendConsole("Creating config.yml...", true);
			try {
				configFile.createNewFile();
				//CreateConfig();
				//config.save(configFile);
				UpdateConfig();
		        io.sendConsole("config.yml succesfully created!", true);
				config.load(configFile);
			} catch (IOException e) {
				if (getDebug()) e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				if (getDebug()) e.printStackTrace();
			}
		}else {
	        try {
				config.load(configFile);
				UpdateConfig();
				config.load(configFile);
			} catch (IOException e) {
				if (getDebug()) e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				if (getDebug()) e.printStackTrace();
			}
		}
	}

	/*/**
	 * 
	 * Creates a new config
	 * 
	 */
	
	/*public void CreateConfig() {
	    config.set("General.Use-Permissions", true);
	    config.set("General.Use-Vault", true);
	    config.set("General.Force-SuperPerms", false);
	    config.set("General.Aliases.Bukkit", false);
	    config.set("General.Aliases.Plugin", false);
	    config.set("General.Aliases.Player", false);
	    config.set("General.Aliases.Language", false);
	    config.set("General.Debug", false);
	    config.set("IO.Show-Prefix", true);
	    config.set("IO.Prefix", "&4[BukkitManager]");
	    config.set("IO.Error", "&c[Error]");
	    config.set("IO.Warning", "&e[Warning]");
	    config.set("IO.Language", "en_US");
	    config.set("IO.ColoredLogs", true);
	    config.set("IO.HelpFormat", "&e%cmd% %args%: &f%desc%");
	    config.set("Database.System", "SQLite");
	    config.set("Database.File", "database.db");
	    config.set("Automessage.Enabled", true);
	    config.set("Automessage.Interval", 120);
	    config.set("Automessage.Random", false);
	    config.set("Automessage.Prefix", "[AutoMessage]");
	    config.set("Autosave.Interval", 900);
	    config.set("Autosave.Enabled", true);
	    config.set("Autosave.NoOffline", true);
	    config.set("Autosave.Taskmode", "Async");
	    config.set("Autobackup.Interval", 3600);
	    config.set("Autobackup.Enabled", true);
	    config.set("Autobackup.NoOffline", true);
	    config.set("Autobackup.Taskmode", "Async");
	    updateWorlds();
	    config.set("Autobackup.Backup.Plugins", true);
	    config.set("Autobackup.Backup.craftbukkit", true);
	    /*config.set("Webinterface.Enabled", true);
	    config.set("Webinterface.Port", 4444);
	    config.set("Webinterface.UseSwingPort", false);
	    config.set("Webinterface.ConnectionLogging", true);
	    config.set("Swing.Enabled", true);
	    config.set("Swing.Start", "on-load");
	    config.set("Swing.Remote.Enabled", true);
	    config.set("Swing.Remote.Port", 4445);
	    config.set("Swing.Remote.UseWebinterfacePort", false);
	    config.set("Swing.Remote.ConnectionLogging", true);*/
	/*}*/
	
	/**
	 * 
	 * Updates a already existing config
	 * 
	 * @throws IOException
	 * 
	 */
	
	public void UpdateConfig() throws IOException {
		if (config.contains("General.Dev-Mode")) devMode = config.getBoolean("General.Dev-Mode");
		update("General.Aliases.Bukkit", false);
		update("General.Aliases.Plugin", false);
		update("General.Aliases.Player", false);
		update("General.Aliases.Language", false);
		update("General.Use-Permissions", true);
		update("General.Use-Vault", true);
		update("General.Force-SuperPerms", false);
		update("General.Debug", false);
		update("IO.Show-Prefix", true);
		update("IO.Prefix", "&4[BukkitManager]");
		update("IO.Error", "&c[Error]");
		update("IO.Warning", "&e[Warning]");
		update("IO.Language", "en_US");		
		update("IO.ColoredLogs", true);
		update("IO.HelpFormat", "&e%cmd% %args%: &f%desc%");
		update("Automessage.Enabled", true);
		update("Automessage.Interval", 120);
		update("Automessage.Random", false);
		update("Automessage.Prefix", "[AutoMessage]");
		update("Autosave.Interval", 900);
		update("Autosave.Enabled", true);
		update("Autosave.NoOffline", true);
		if (update("Autosave.Taskmode", "Async")) {
			if (config.getString("Autosave.Taskmode").equalsIgnoreCase("Sync")) saveMode = "SYNC";
			else if (config.getString("Autosave.Taskmode").equalsIgnoreCase("Async")) saveMode = "ASYNC";
			else {
				config.set("Autosave.Taskmode", "Sync");
				saveMode = "SYNC";
			}
		}		
		update("Autobackup.Interval", 3600);
		update("Autobackup.Enabled", true);
		update("Autobackup.NoOffline", true);
		if (update("Autobackup.Taskmode", "Async")) {
			if (config.getString("Autobackup.Taskmode").equalsIgnoreCase("Sync")) backupMode = "SYNC";
			else if (config.getString("Autobackup.Taskmode").equalsIgnoreCase("Async")) backupMode = "ASYNC";
			else {
				set("Autobackup.Taskmode", "Sync");
				backupMode = "SYNC";
			}
		}
		updateWorlds();
		update("Autobackup.Backup.Plugins", true);
		update("Autobackup.Backup.craftbukkit", true);
		update("Database.System", "SQLite");
		if (config.getString("Database.System").equalsIgnoreCase("SQLite")) {
			dbType = "SQLite";
			update("Database.File", "database.db");
			set("Database.Host", null);
			set("Database.Port", null);
			set("Database.Username", null);
			set("Database.Password", null);
			set("Database.TablePrefix", null);
			set("Database.Name", null);
		}else if (config.getString("Database.System").equalsIgnoreCase("MySQL")) {
			dbType = "MySQL";
			update("Database.Host", "localhost");
			update("Database.Port", 3306);
			update("Database.Name", "minecraft");
			update("Database.Username", "root");
			update("Database.Password", "");
			set("Database.File" , null);
		}else if (config.getString("Database.System").equalsIgnoreCase("H2")) {
			dbType = "H2";
			update("Database.Host", "localhost");
			update("Database.Name", "minecraft");
			update("Database.Username", "root");
			update("Database.Password", "");
			set("Database.File" , null);
			set("Database.Port", null);
		}
		
		/*
		 * 		if (config.contains("General.Dev-Mode")) devMode = config.getBoolean("General.Dev-Mode");
		if (!config.contains("General.Aliases.Bukkit")) config.set("General.Aliases.Bukkit", false);
		if (!config.contains("General.Aliases.Plugin")) config.set("General.Aliases.Plugin", false);
		if (!config.contains("General.Aliases.Player")) config.set("General.Aliases.Player", false);
		if (!config.contains("General.Aliases.Language")) config.set("General.Aliases.Language", false);
		if (!config.contains("General.Use-Permissions")) config.set("General.Use-Permissions", true);
		if (!config.contains("General.Use-Vault")) config.set("General.Use-Vault", true);
		if (!config.contains("General.Force-SuperPerms")) config.set("General.Force-SuperPerms", false);
		if (!config.contains("General.Debug")) config.set("General.Debug", false);
		if (!config.contains("IO.Show-Prefix")) config.set("IO.Show-Prefix", true);
		if (!config.contains("IO.Prefix")) config.set("IO.Prefix", "&4[BukkitManager]");
		if (!config.contains("IO.Error")) config.set("IO.Error", "&c[Error]");
		if (!config.contains("IO.Warning")) config.set("IO.Warning", "&e[Warning]");
		if (!config.contains("IO.Language")) config.set("IO.Language", "en_US");		
		if (!config.contains("IO.ColoredLogs")) config.set("IO.ColoredLogs", true);
		if (!config.contains("IO.HelpFormat")) config.set("IO.HelpFormat", "&e%cmd% %args%: &f%desc%");
	    if (!config.contains("Automessage.Enabled")) config.set("Automessage.Enabled", true);
		if (!config.contains("Automessage.Interval")) config.set("Automessage.Interval", 120);
		if (!config.contains("Automessage.Random")) config.set("Automessage.Random", false);
		if (!config.contains("Automessage.Prefix")) config.set("Automessage.Prefix", "[AutoMessage]");
		if (!config.contains("Autosave.Interval")) config.set("Autosave.Interval", 900);
		if (!config.contains("Autosave.Enabled")) config.set("Autosave.Enabled", true);
		if (!config.contains("Autosave.NoOffline")) config.set("Autosave.NoOffline", true);
		if (!config.contains("Autosave.Taskmode")) config.set("Autosave.Taskmode", "Async");
		else {
			if (config.getString("Autosave.Taskmode").equalsIgnoreCase("Sync")) saveMode = "SYNC";
			else if (config.getString("Autosave.Taskmode").equalsIgnoreCase("Async")) saveMode = "ASYNC";
			else {
				config.set("Autosave.Taskmode", "Sync");
				saveMode = "SYNC";
			}
		}		
		if (!config.contains("Autobackup.Interval")) config.set("Autobackup.Interval", 3600);
		if (!config.contains("Autobackup.Enabled")) config.set("Autobackup.Enabled", true);
		if (!config.contains("Autobackup.NoOffline")) config.set("Autobackup.NoOffline", true);
		if (!config.contains("Autobackup.Taskmode")) config.set("Autobackup.Taskmode", "Async");
		else {
			if (config.getString("Autobackup.Taskmode").equalsIgnoreCase("Sync")) backupMode = "SYNC";
			else if (config.getString("Autobackup.Taskmode").equalsIgnoreCase("Async")) backupMode = "ASYNC";
			else {
				config.set("Autobackup.Taskmode", "Sync");
				backupMode = "SYNC";
			}
		}
		updateWorlds();
		if (!config.contains("Autobackup.Backup.Plugins")) config.set("Autobackup.Backup.Plugins", true);
		if (!config.contains("Autobackup.Backup.craftbukkit")) config.set("Autobackup.Backup.craftbukkit", true);
		if (!config.contains("Database.System")) config.set("Database.System", "SQLite");
		if (config.getString("Database.System").equalsIgnoreCase("SQLite")) {
			dbType = "SQLite";
			if (!config.contains("Database.File")) config.set("Database.File", "database.db");
			if (config.contains("Database.Host")) config.set("Database.Host", null);
			if (config.contains("Database.Port")) config.set("Database.Port", null);
			if (config.contains("Database.Username")) config.set("Database.Username", null);
			if (config.contains("Database.Password")) config.set("Database.Password", null);
			if (config.contains("Database.TablePrefix")) config.set("Database.TablePrefix", null);
			if (config.contains("Database.Name")) config.set("Database.Name", null);
		}else if (config.getString("Database.System").equalsIgnoreCase("MySQL")) {
			dbType = "MySQL";
			if (!config.contains("Database.Host")) config.set("Database.Host", "localhost");
			if (!config.contains("Database.Port")) config.set("Database.Port", 3306);
			if (!config.contains("Database.Name")) config.set("Database.Name", "minecraft");
			if (!config.contains("Database.Username")) config.set("Database.Username", "root");
			if (!config.contains("Database.Password")) config.set("Database.Password", "");
			if (config.contains("Database.File")) config.set("Database.File" , null);
		}else if (config.getString("Database.System").equalsIgnoreCase("H2")) {
			dbType = "H2";
			if (!config.contains("Database.Host")) config.set("Database.Host", "localhost");
			if (!config.contains("Database.Name")) config.set("Database.Name", "minecraft");
			if (!config.contains("Database.Username")) config.set("Database.Username", "root");
			if (!config.contains("Database.Password")) config.set("Database.Password", "");
			if (config.contains("Database.File")) config.set("Database.File" , null);
			if (config.contains("Database.Port")) config.set("Database.Port", null);
		}
		 * 
		 */
		
		if (devMode) {
			//update("Webinterface.Enabled", false);
			//update("Webinterface.Port", 4444);
			//update("Webinterface.UseSwingPort", false);
			//update("Webinterface.ConnectionLogging", true);
			update("Swing.Enabled", false);
			update("Swing.Start", "on-load");
			update("Swing.Remote.Enabled", false);
			List<Integer> portList = new ArrayList<Integer>();
			portList.add(4445);
			portList.add(4447);
			portList.add(4449);
			portList.add(4451);
			portList.add(4453);
			portList.add(4455);
			portList.add(4457);
			portList.add(4459);
			update("Swing.Remote.Ports", portList);
			//update("Swing.Remote.UseWebinterfacePort", false);
			update("Swing.Remote.ConnectionLogging", true);
		}
		config.save(configFile);
	}
	
	private void updateWorlds() {
		List<World> worlds = plugin.getServer().getWorlds();
		for (int i = 0; i < worlds.size(); i++) update("Autobackup.Backup.Worlds." + worlds.get(i).getName(), true);
	}

	/**
	 * 
	 * Return whether Bukkimanager is in Debug Mode or not
	 * 
	 * @return The Debug Mode
	 * 
	 */
	
	public boolean getDev() {return devMode;}
	public boolean getDebug() {return config.getBoolean("General.Debug", false);}
	
	public String getDatabaseType() {return dbType;}
	public String getBackupMode() {return backupMode;}
	public String getSaveMode() {return saveMode;}
	
	public String getString(String path) {return config.getString(path);}	
	public String getString(String path, String def) {return config.getString(path, def);}
	
	public boolean getBoolean(String path) {return config.getBoolean(path);}
	public boolean getBoolean(String path, Boolean def) {return config.getBoolean(path, def);}
	
	public int getInt(String path) {return config.getInt(path);}
	public int getInt(String path, int def) {return config.getInt(path, def);}
	
	public List<?> getList(String path) {return config.getList(path);}
	public List<?> getList(String path, List<?> def) {return config.getList(path, def);}
	
	public Object get(String path) {return config.get(path);}
	public Object get(String path, Object def) {return config.get(path, def);}
	
	public boolean update(String path, Object value) {
		if (!config.contains(path)) {
			config.set(path, value);
			return false;
		}else return true;
	}
	
	public void set(String path, Object value) {config.set(path, value);}
	
	public boolean contains(String path) {return config.contains(path);}
}
