package org.efreak.bukkitmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;


/**
 * 
 * Loads and manages the config File
 *
 */

public class Configuration{
	 
	private static IOManager io;
	private static final Plugin plugin;
	private static FileConfiguration config;
	private static File configFile;
	private static String dbType = "SQLite";
	private static boolean devMode;
	
	static {
		plugin = Bukkitmanager.getInstance();
	}
	
	/**
	 * 
	 * Loads and creates if needed the config
	 *
	 */
	
	public void init() {
		io = Bukkitmanager.getIOManager();
		configFile = new File(plugin.getDataFolder(), "config.yml");
		config = plugin.getConfig();
		if (!configFile.exists()){
			io.sendConsole("Creating config.yml...", true);
			try {
				configFile.createNewFile();
				updateConfig();
		        io.sendConsole("config.yml succesfully created!", true);
				config.load(configFile);
			} catch (IOException e) {
				if (getDebug()) e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				if (getDebug()) e.printStackTrace();
			}
		}else {
	        try {
	        	Bukkitmanager.firstRun = false;
				config.load(configFile);
				updateConfig();
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
	 * Updates an already existing config
	 * 
	 * @throws IOException
	 * 
	 */
	public void updateConfig() throws IOException {
		if (contains("General.DevMode")) devMode = getBoolean("General.DevMode");
		update("General.Permissions.Use-Permissions", true);
		update("General.Permissions.Use-Vault", true);
		update("General.Permissions.Force-SuperPerms", false);
		update("General.Permissions.Log", true);
		update("General.Auto-Updater", true);
		update("General.Debug", false);
		update("General.Logger", false);
		update("General.Player-Synchronisation", true);
		update("General.CPULoad", false);
		update("General.Scripting", true);
		update("General.Statistics", true);
		update("IO.Show-Prefix", true);
		update("IO.Prefix", "&4[Bukkitmanager]");
		update("IO.Error", "&c[Error]");
		update("IO.Warning", "&e[Warning]");
		update("IO.Debug", "&3[Debug]");
		update("IO.Language", "en");		
		update("IO.ColoredLogs", true);
		update("IO.HelpHeader", "BUKKITMANAGER HELP(%page%/%pages%)");
		update("IO.HelpFormat", "&e%cmd%: &f%desc%");
		update("PluginUpdater.Enabled", true);
		update("PluginUpdater.CheckOnStart", true);
		update("PluginUpdater.Whitelist.Enabled", false);
		update("PluginUpdater.Blacklist.Enabled", false);
		update("Automessage.Enabled", true);
		update("Automessage.File", "automessages.txt");
		update("Automessage.Interval", 120);
		update("Automessage.Random", false);
		update("Automessage.Prefix", "[AutoMessage]");
		update("Autosave.Enabled", true);
		update("Autosave.Interval", 900);
		update("Autosave.Warntimes", Arrays.asList(60));
		update("Autosave.Notification", true);
		update("Autosave.NoOffline", true);
		if (update("Autosave.Taskmode", "async") && !getString("Autosave.Taskmode").equalsIgnoreCase("sync") && !getString("Autosave.Taskmode").equalsIgnoreCase("async")) set("Autosave.Taskmode", "async");
		update("Autobackup.Enabled", true);
		update("Autobackup.RenameJar", true);
		update("Autobackup.Interval", 3600);
		update("Autobackup.Warntimes", Arrays.asList(60));
		update("Autobackup.Notification", true);
		update("Autobackup.NoOffline", true);
		update("Autobackup.KeepBackups", -1);
		update("Autobackup.BackupDir", "backups");
		update("Autobackup.TempBackupDir", "backups/temp");
		update("Autobackup.PostExecution.Enabled", false);
		update("Autobackup.PostExecution.File", "backup.sh");
		if (update("Autobackup.Taskmode", "async") && !getString("Autobackup.Taskmode").equalsIgnoreCase("sync") && !getString("Autobackup.Taskmode").equalsIgnoreCase("async")) set("Autobackup.Taskmode", "async");
		updateWorlds();
		update("Autobackup.Backup.Plugins", true);
		update("Autobackup.Backup.craftbukkit", true);
		update("Database.System", "SQLite");
		update("CustomMessages.Enabled", true);
		update("CustomMessages.Join", Arrays.asList("&e%player% joined the game."));
		update("CustomMessages.Leave", Arrays.asList("&e%player% has left the game."));
		update("CustomMessages.Kick", Arrays.asList("&e%player% has left the game."));
		update("CustomMessages.UnknownCommand", Arrays.asList("Unknown Command"));
		update("Fakepluginlist.Enabled", true);
		update("Fakepluginlist.Fakes", Arrays.asList("fakeplugin123", "AntiGrief"));
		update("Fakepluginlist.Hidden", Arrays.asList("*", "--BukkitManager"));
		/*update("Scoreboards.Enabled", true);
		update("Scoreboards.Updates", true);
		update("Scoreboards.OnlinePlayer", false);*/
		update("Worlds.Autoload", true);
		update("Notifications.Enabled", true);
		update("Notifications.PluginUpdater.Updates", true);
		update("Notifications.PluginUpdater.Updated", true);
		update("Notifications.Autobackup.Started", true);
		update("Notifications.Autobackup.Finished", true);
		update("Notifications.RemoteServer", false);
		if (!PluginManager.isPluginEnabled("Notifications") && getBoolean("Notifications.Enabled")) io.sendConsoleWarning("Notifications enabled but Plugin couldn't be found");
		if (devMode) {
			update("RemoteServer.Enabled", false);
			update("RemoteServer.Port", 4444);
			update("RemoteServer.Logging.Enabled", true);
			update("RemoteServer.Logging.Console", false);
			update("RemoteServer.User.Local", true);
			update("RemoteServer.User.Remote", true);
			update("RemoteServer.User.Admin.Username", "admin");
			update("RemoteServer.User.Admin.Password", "123456");
		}
		update("Chatfilter.Enabled", true);
		update("Chatfilter.File", "chatfilter.txt");
		update("Chatfilter.Replacement", "*");
		config.save(configFile);
	}
	
	private void updateWorlds() {
		List<World> worlds = plugin.getServer().getWorlds();
		for (int i = 0; i < worlds.size(); i++) update("Autobackup.Backup.Worlds." + worlds.get(i).getName(), true);
	}
		
	public boolean getDev() {return devMode;}
	
	/**
	 * 
	 * Return whether Bukkitmanager is in Debug Mode or not
	 * 
	 * @return The Debug Mode
	 * 
	 */
	public boolean getDebug() {return config.getBoolean("General.Debug", false);}
	
	 /**
	 * 
	 * @return The currently used Database System
	 * @see org.efreak.bukkitmanager.Database
	 */
	
	public String getDatabaseType() {return dbType;}
	
	public String getString(String path) {return config.getString(path);}	
	public String getString(String path, String def) {return config.getString(path, def);}
	
	public boolean getBoolean(String path) {return config.getBoolean(path);}
	public boolean getBoolean(String path, Boolean def) {return config.getBoolean(path, def);}
	
	public int getInt(String path) {return config.getInt(path);}
	public int getInt(String path, int def) {return config.getInt(path, def);}

	public long getLong(String path) {return config.getLong(path);}
	public long getLong(String path, int def) {return config.getLong(path, def);}
	
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
	
	public void addAlias(String cmd) {
		update("General.Aliases." + cmd.substring(0, 1).toUpperCase() + cmd.substring(1), false);
	}
	
	public boolean getAlias(String cmd) {
		return getBoolean("General.Aliases." + cmd.substring(0, 1).toUpperCase() + cmd.substring(1));
	}
	
	public void addToList(String path, Object value) {
		update(path, Arrays.asList(getList(path), value));
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
}
