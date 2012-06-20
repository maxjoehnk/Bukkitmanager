package org.efreak1996.Bukkitmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

public class Configuration{
	 
	private static IOManager io;
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
		io = new IOManager();
		io.plugin = plugin;
		config = plugin.getConfig();
		if (!(configFile.exists())){
			io.sendConsole("Creating config.yml...", true);
			try {
				configFile.createNewFile();
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
		update("General.Statistics.Enabled", true);
		update("General.Statistics.GUID", UUID.randomUUID().toString());
		addDefault("General.Statistics.GUID", getString("General.Statistics.GUID"));
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
		update("Autosave.Warntimes", Arrays.asList(60));
		update("Autosave.Notification", true);
		update("Autosave.Enabled", true);
		update("Autosave.NoOffline", true);
		if (update("Autosave.Taskmode", "Async")) {
			if (config.getString("Autosave.Taskmode").equalsIgnoreCase("Sync")) saveMode = "SYNC";
			else if (config.getString("Autosave.Taskmode").equalsIgnoreCase("Async")) saveMode = "ASYNC";
			else {
				config.set("Autosave.Taskmode", "Async");
				saveMode = "ASYNC";
			}
		}		
		update("Autobackup.Interval", 3600);
		update("Autobackup.Warntimes", Arrays.asList(60));
		update("Autobackup.Notification", true);
		update("Autobackup.Enabled", true);
		update("Autobackup.NoOffline", true);
		if (update("Autobackup.Taskmode", "Async")) {
			if (config.getString("Autobackup.Taskmode").equalsIgnoreCase("Sync")) backupMode = "SYNC";
			else if (config.getString("Autobackup.Taskmode").equalsIgnoreCase("Async")) backupMode = "ASYNC";
			else {
				set("Autobackup.Taskmode", "Async");
				backupMode = "ASYNC";
			}
		}
		updateWorlds();
		update("Autobackup.Backup.Plugins", true);
		update("Autobackup.Backup.craftbukkit", true);
		update("Database.System", "SQLite");
		if (config.getString("Database.System").equalsIgnoreCase("SQLite")) {
			dbType = "SQLite";
			update("Database.File", "database.db");
			remove("Database.Host");
			remove("Database.Port");
			remove("Database.Username");
			remove("Database.Password");
			remove("Database.TablePrefix");
			remove("Database.Name");
		}else if (config.getString("Database.System").equalsIgnoreCase("MySQL")) {
			dbType = "MySQL";
			update("Database.Host", "localhost");
			update("Database.Port", 3306);
			update("Database.Name", "minecraft");
			update("Database.Username", "root");
			update("Database.Password", "");
			remove("Database.File");
		}else if (config.getString("Database.System").equalsIgnoreCase("H2")) {
			dbType = "H2";
			update("Database.Host", "localhost");
			update("Database.Name", "minecraft");
			update("Database.Username", "root");
			update("Database.Password", "");
			remove("Database.File");
			remove("Database.Port");
		}
		update("CustomMessages.Enabled", true);
		update("CustomMessages.Join", Arrays.asList("&e%player% joined the game."));
		update("CustomMessages.Leave", Arrays.asList("&e%player% has left the game."));
		update("CustomMessages.Kick", Arrays.asList("&e%player% has left the game."));
		update("Fakepluginlist.Enabled", true);
		update("Fakepluginlist.Fakes", Arrays.asList("fakeplugin123", "AntiGrief"));
		update("Fakepluginlist.Hidden", Arrays.asList("*", "--BukkitManager"));
		if (devMode) {
			//update("Webinterface.Enabled", false);
			//update("Webinterface.Port", 4444);
			//update("Webinterface.UseSwingPort", false);
			//update("Webinterface.ConnectionLogging", true);
			update("Swing.Enabled", false);
			//update("Swing.Start", "on-load");
			//update("Swing.Remote.Enabled", false);
			//update("Swing.Remote.Ports", Arrays.asList(4445,4447,4449,4451,4453,4455,4457,4459);
			//update("Swing.Remote.UseWebinterfacePort", false);
			//update("Swing.Remote.ConnectionLogging", true);
		}
		config.save(configFile);
	}
	
	private void updateWorlds() {
		List<World> worlds = plugin.getServer().getWorlds();
		for (int i = 0; i < worlds.size(); i++) update("Autobackup.Backup.Worlds." + worlds.get(i).getName(), true);
	}
	
	/**
	 * 
	 * Return whether Bukkimanager is in Dev Mode or not
	 * 
	 * @return The Dev Mode
	 * 
	 */
	
	public boolean getDev() {return devMode;}
	
	/**
	 * 
	 * Return whether Bukkimanager is in Debug Mode or not
	 * 
	 * @return The Debug Mode
	 * 
	 */
	
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
	
	public List<String> getStringList(String path) {return config.getStringList(path);}
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
	
	public void remove(String path) {config.set(path, null);}
	
	public boolean contains(String path) {return config.contains(path);}

	public String getGUID() {
		return getString("General.Statistics.GUID");
	}

	public void reload() {
		try {
			config.load(configFile);
		} catch (FileNotFoundException e) {
			if (getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (getDebug()) e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			if (getDebug()) e.printStackTrace();
		}
	}

	public void save() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			if (getDebug()) e.printStackTrace();
		}
	}

	public void addDefault(String path, Object value) {
		config.addDefault(path, value);
	}
}
