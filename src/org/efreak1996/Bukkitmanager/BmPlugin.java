package org.efreak1996.Bukkitmanager;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.efreak1996.Bukkitmanager.Commands.BmCommandExecutor;
import org.efreak1996.Bukkitmanager.Listener.BmBukkitListener;
import org.efreak1996.Bukkitmanager.Logger.BmLoggingManager;
import org.efreak1996.Bukkitmanager.PluginManager.BmPluginManager;
import org.efreak1996.Bukkitmanager.Swing.BmSwing;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;
import org.efreak1996.Bukkitmanager.Util.BmThreadManager;


/**
 * The mainclass of Bukkitmanager
 * Initializes everything, shutdowns everything, handles almost everything
 * 
 * @author efreak1996
 * 
 */

public class BmPlugin extends JavaPlugin {
	
	private static Plugin plugin;
	private static BmConfiguration config;
	private static BmIOManager io;
	private static BmLoggingManager logManager;
	private static BmAutomessageThread msgThread;
	private static BmAutosaveThread saveThread;
	private static BmAutobackupThread backupThread;
	private static BmPermissions permHandler;
	private static BmThreadManager func;
	private static BmDatabase db;
	private static BmSwing swing;
	private static File rootFolder;
	private static BmCustomMessageManager msgManager;
	//private static BmLibraryManager libManager;

	/**
	 * 
	 * Runs while shutdown the server
	 * 
	 */
	
	@Override
	public void onDisable() {
    	db.shutdown();
    	func.stopThread(BmThreadType.AUTOSAVE);
    	func.stopThread(BmThreadType.AUTOBACKUP);
    	func.stopThread(BmThreadType.AUTOMESSAGE);
		try {
			FileUtils.cleanDirectory(new File(plugin.getDataFolder().getParentFile().getAbsoluteFile().getParentFile() + File.separator + "backups" + File.separator + "temp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (config.getDev()) {
			io.sendConsoleDev("Disabling Development Features!");
			io.sendConsoleDev("Disabling Swing GUI and Swing Remote Server...");
			swing.shutdown();
			io.sendConsoleDev("Development Features disabled!");
		}
    	io.sendConsole(io.translate("Plugin.Done"));	}
	
	/**
	 * 
	 * Runs after loading the Plugin
	 * 
	 */
	
	@Override
	public void onLoad() {
		com.jidesoft.utils.Lm.verifyLicense("Max Joehnk", "Bukkitmanager", "YrreBQlj.lyFAbpwcAUamoLF3Moy0zN");
		plugin = this;
	}

	/**
	 * 
	 * Runs after loading of all plugins and worlds
	 * 
	 */
	
	@Override
	public void onEnable() {
		CreateDirs();
		config = new BmConfiguration();
		config.initalize();
		io = new BmIOManager();
		io.initialize();
		permHandler = new BmPermissions();
		permHandler.initialize();
		func = new BmThreadManager();
		func.initialize();
		db = new BmDatabase();
		db.initialize();
		logManager = new BmLoggingManager();
		logManager.initialize();
		if (config.getDev()) {
			io.sendConsoleDev("Development Mode enabled!");
			io.sendConsoleDev("New Features, which aren't complete or unstable will be enabled!");
			swing = new BmSwing();
			swing.initialize();
		}		
		plugin.getServer().getPluginCommand("bm").setExecutor(new BmCommandExecutor());
		plugin.getServer().getPluginManager().registerEvents(new BmBukkitListener(), plugin);
		Threads();
		if (config.getBoolean("General.Statistics.Enabled")) new BmStats().start();
		msgManager = new BmCustomMessageManager();
		msgManager.initialize();
		new BmFakepluginsManager();
		io.sendConsole(io.translate("Plugin.Done"));
	}
	
	/**
	 * 
	 * Creates the Folderstructure of Bukkitmanager
	 * 
	 */
	
	private void CreateDirs() {
		rootFolder = plugin.getDataFolder().getAbsoluteFile().getParentFile().getParentFile();
		if (new File(plugin.getDataFolder().getAbsoluteFile().getParentFile(), "BukkitManager").exists()) {
			plugin.getLogger().info("Moving Foldercontent to new Folder...");
			try {
				FileUtils.copyDirectory(new File(plugin.getDataFolder().getAbsoluteFile().getParentFile(), "BukkitManager"), plugin.getDataFolder());
				plugin.getLogger().info("Done! Please delete the folder: " + new File(plugin.getDataFolder().getAbsoluteFile().getParentFile(), "BukkitManager").toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!(plugin.getDataFolder().exists()))
				plugin.getDataFolder().mkdirs();
		File langDir = new File(plugin.getDataFolder() + File.separator + "lang");
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
		msgThread = new BmAutomessageThread();
		msgThread.initialize();
		saveThread = new BmAutosaveThread();
		saveThread.initialize();
		backupThread = new BmAutobackupThread();
		backupThread.initialize();
		if (config.getBoolean("Autosave.Enabled")) func.startThread(BmThreadType.AUTOSAVE);
		if (config.getBoolean("Automessage.Enabled")) func.startThread(BmThreadType.AUTOMESSAGE);
		if (config.getBoolean("Autobackup.Enabled")) func.startThread(BmThreadType.AUTOBACKUP);
	}
	/**
	 * 
	 * Returns the Plugin itself
	 * 
	 * @return The Plugin
	 * 
	 */
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	/**
	 * 
	 * Returns a modified Bukkit Pluginmanager for updating and installing new Plugins
	 * 
	 * @return the modified Pluginmanager
	 * 
	 */
	
	public static BmPluginManager getPluginManager() {
		return new BmPluginManager();
	}
}
