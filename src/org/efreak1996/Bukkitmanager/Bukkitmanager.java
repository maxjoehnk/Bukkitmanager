package org.efreak1996.Bukkitmanager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.efreak1996.Bukkitmanager.Addon.Addon;
import org.efreak1996.Bukkitmanager.Addon.AddonCommand;
import org.efreak1996.Bukkitmanager.Addon.AddonCommandExecutor;
import org.efreak1996.Bukkitmanager.Commands.BmCommandExecutor;
import org.efreak1996.Bukkitmanager.Listener.BukkitListener;
import org.efreak1996.Bukkitmanager.Logger.LoggingManager;
import org.efreak1996.Bukkitmanager.PluginManager.PluginManager;
import org.efreak1996.Bukkitmanager.Swing.Swing;

/**
 * 
 * The mainclass of Bukkitmanager
 * Initializes everything, shutdowns everything, handles almost everything
 * 
 * @author efreak1996
 * 
 */

public class Bukkitmanager extends JavaPlugin {
	
	//Manages all the Input and Output of the Plugin
	private static IOManager io;
	//The Config of the Plugin
	private static Configuration config;
	//The Properties File of the standalone Version
	private static Properties properties;
	//The Database of the Plugin
	private static Database db;
	//The PermissionsHandler of the Plugin
	private static Permissions permHandler;
	//
	private static Plugin instance;
	private static LoggingManager logManager;
	private static AutomessageThread msgThread;
	private static AutosaveThread saveThread;
	private static AutobackupThread backupThread;
	private static ThreadManager func;
	private static Swing swing;
	private static File rootFolder;
	private static CustomMessageManager msgManager;
	private static List<Plugin> plugins;
	private static List<Addon> addons;
	public static boolean firstRun = true;
	//private static LibraryManager libManager;

	/**
	 * 
	 * Controls Bukkitmanager, if it is started without Bukkit
	 * 
	 */
	
	public static void main(String[] args) {
		List<String> argList = Arrays.asList(args);
		if (args.length == 0); //Open Gui
		else if (argList.contains("--external") || argList.contains("-e")) {
			
		}
	}
	
	/**
	 * 
	 * Runs while shutdown the server
	 * 
	 */
	
	@Override
	public void onDisable() {
    	db.shutdown();
    	func.stopThread(ThreadType.AUTOSAVE);
    	func.stopThread(ThreadType.AUTOBACKUP);
    	func.stopThread(ThreadType.AUTOMESSAGE);
    	getServer().getScheduler().cancelTasks(this);
		try {
			FileUtils.cleanDirectory(new File(getDataFolder().getParentFile().getAbsoluteFile().getParentFile() + File.separator + "backups" + File.separator + "temp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (config.getDev()) {
			io.sendConsoleDev("Disabling Development Features!");
			io.sendConsoleDev("Disabling Swing GUI and Swing Remote Server...");
			swing.shutdown();
			io.sendConsoleDev("Development Features disabled!");
		}
    	io.sendConsole(io.translate("Plugin.Done"));
    }
	
	/**
	 * 
	 * Runs after loading the Plugin
	 * 
	 */
	
	@Override
	public void onLoad() {
		com.jidesoft.utils.Lm.verifyLicense("Max Joehnk", "Bukkitmanager", "YrreBQlj.lyFAbpwcAUamoLF3Moy0zN");
		instance = this;
	}

	/**
	 * 
	 * Runs after loading of all plugins and worlds
	 * 
	 */
	
	@Override
	public void onEnable() {
		CreateDirs();
		config = new Configuration();
		config.initalize();
		io = new IOManager();
		io.initialize();
		if (firstRun) io.sendConsole(io.translate("Plugin.FirstRun"));
		permHandler = new Permissions();
		permHandler.initialize();
		func = new ThreadManager();
		func.initialize();
		db = new Database();
		db.initialize();
		logManager = new LoggingManager();
		logManager.initialize();
		if (config.getDev()) {
			io.sendConsoleDev("Development Mode enabled!");
			io.sendConsoleDev("New Features, which aren't complete or unstable will be enabled!");
			swing = new Swing();
			swing.initialize();
		}		
		getServer().getPluginCommand("bm").setExecutor(new BmCommandExecutor());
		getServer().getPluginManager().registerEvents(new BukkitListener(), this);
		Threads();
		if (config.getBoolean("General.Statistics.Enabled")) new Statistics().start();
		msgManager = new CustomMessageManager();
		msgManager.initialize();
		new FakepluginsManager();
		io.sendConsole(io.translate("Plugin.Done"));
	}
	
	/**
	 * 
	 * Creates the Folderstructure of Bukkitmanager
	 * 
	 */
	
	private void CreateDirs() {
		rootFolder = getDataFolder().getAbsoluteFile().getParentFile().getParentFile();
		if (new File(getDataFolder().getAbsoluteFile().getParentFile(), "BukkitManager").exists()) {
			new File(getDataFolder().getAbsoluteFile().getParentFile(), "BukkitManager").renameTo(getDataFolder().getAbsoluteFile());
		}
		if (!(getDataFolder().exists()))
				getDataFolder().mkdirs();
		File langDir = new File(getDataFolder() + File.separator + "lang");
		if (!(langDir.exists()))
			langDir.mkdirs();
		if (!(new File(rootFolder, "backups" + File.separator + "temp").exists()))
			new File(rootFolder, "backups" + File.separator + "temp").mkdirs();
		try {
			FileUtils.cleanDirectory(new File(rootFolder, "backups" + File.separator + "temp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!(new File(rootFolder, "update").exists()))
			new File(rootFolder, "update").mkdirs();
	}
	
	/**
	 * 
	 * Initialize all Threads
	 * 
	 */
	
	private void Threads() {
		msgThread = new AutomessageThread();
		msgThread.initialize();
		saveThread = new AutosaveThread();
		saveThread.initialize();
		backupThread = new AutobackupThread();
		backupThread.initialize();
		if (config.getBoolean("Autosave.Enabled")) func.startThread(ThreadType.AUTOSAVE);
		if (config.getBoolean("Automessage.Enabled")) func.startThread(ThreadType.AUTOMESSAGE);
		if (config.getBoolean("Autobackup.Enabled")) func.startThread(ThreadType.AUTOBACKUP);
	}
	
	/**
	 * 
	 * Returns an Instance of the Plugin
	 * 
	 * @return The Instance
	 * 
	 */
	
	public static Plugin getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * Returns a modified Bukkit Pluginmanager for updating and installing new Plugins
	 * 
	 * @return the modified Pluginmanager
	 * 
	 */
	
	public static PluginManager getPluginManager() {
		return new PluginManager();
	}
	
	/**
	 * 
	 * Returns the IOManager of Bukkitmanager
	 * 
	 * @return The IOManager
	 * 
	 */
	
	public static IOManager getIOManager() {
		return io;
	}
	
	/**
	 * 
	 * Returns the Database of Bukkitmanager
	 * 
	 * @return The Database
	 * 
	 */
	
	public static Database getDb() {
		return db;
	}
	
	/**
	 * 
	 * Returns the Permissions Handler of Bukkitmanager
	 * 
	 * @return The Permissions Handler
	 * 
	 */
	
	public static Permissions getPermHandler() {
		return permHandler;
	}
	
	/**
	 * 
	 * Returns the Config of Bukkitmanager
	 * 
	 * @return The Config
	 * 
	 */
	
	public static Configuration getConfiguration() {
		return config;
	}
	
	/**
	 * 
	 * Registers a new Command from an Addon
	 * 
	 * @return The AddonCommand
	 * 
	 */
	
	public static AddonCommand registerCommand(Addon addon, String name, AddonCommandExecutor executor) {
		//AddonCommand cmd = new AddonCommand(name, addon, executor);
		return null;
	}
	
	/**
	 * 
	 * Returns the Rootfolder of the Server
	 * 
	 * @return The Rootfolder
	 * 
	 */
	
	public static File getRootFolder() {
		return rootFolder;
	}

	public static void reload() {
		
	}
}
