package com.efreak1996.BukkitManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.Commands.BmCommandExecutor;
import com.efreak1996.BukkitManager.Listener.BmBukkitListener;
import com.efreak1996.BukkitManager.Logger.BmLoggingManager;
import com.efreak1996.BukkitManager.Swing.BmSwing;
import com.efreak1996.BukkitManager.Util.BmDownloader;

/**
 * 
 * Manages the start of Bukkitmanager 
 *
 * @author efreak1996
 *
 * @version Alpha 1.5
 * 
 */

public class BmInitialize {
	
	private static Plugin plugin;
	private static BmConfiguration config;
	private static BmIOManager io;
	private static BmLoggingManager logManager;
	private static BmAutomessageThread msgThread;
	private static BmAutosaveThread saveThread;
	private static BmAutobackupThread backupThread;
	private static BmPermissions permHandler;
	private static BmFunctions func;
	private static BmDatabase database;
	private static BmDownloader downloader;
	private static BmSwing swing;
	private static File rootFolder;
	
	/**
	 * 
	 * @see Bukkitmanager#onEnable()
	 * 
	 */
	
	public BmInitialize(Plugin plugin) {
		this.plugin = plugin;
		CreateDirs();
		config = new BmConfiguration();
		config.initalize();
		io = new BmIOManager();
		io.initialize();
		permHandler = new BmPermissions();
		permHandler.initialize();
		func = new BmFunctions();
		func.initialize();
		database = new BmDatabase();
		database.initialize();
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
		downloader = new BmDownloader();
		downloader.initialize();

		//new Bukkitmanager(plugin, io, config, database, permHandler);
		//Bukkitmanager.getAddonManager().loadAddons();
		io.sendConsole(io.translate("Plugin.Done"));
	}
	
	/**
	 * 
	 * Creates the Folderstructure of Bukkitmanager
	 * 
	 */
	
	public void CreateDirs() {
		rootFolder = plugin.getDataFolder().getAbsoluteFile().getParentFile().getParentFile();
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
	 * Checks whether Spout is installed or not and loads Spoutspecific stuff
	 * 
	 */
	
	public void Spout() {
		if (config.getBoolean("Spout.Enabled")) {
			if (plugin.getServer().getPluginManager().getPlugin("Spout") != null) {            
				io.sendConsole("Spout support enabled.");
				io.sendConsole("Initializing Spoutgui...");
				//BmGuiGeneral.initialize();
				io.sendConsole("Spoutgui initialized!");
			} else io.sendConsoleWarning("Spout not found!");
		}
	}
	
	public void Threads() {
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
}
