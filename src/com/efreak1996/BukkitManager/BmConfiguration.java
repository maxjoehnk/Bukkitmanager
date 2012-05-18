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
		addAutosaveWarntimes();
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
		addAutobackupWarntimes();
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

	private void addAutosaveWarntimes() {
		List<Integer> warntimes = new ArrayList<Integer>();
		warntimes.add(60);
		update("Autosave.Warntimes", warntimes);
	}
	
	private void addAutobackupWarntimes() {
		List<Integer> warntimes = new ArrayList<Integer>();
		warntimes.add(60);
		update("Autobackup.Warntimes", warntimes);
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
	
	public List<Integer> getIntegerList(String path) {return config.getIntegerList(path);}

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
