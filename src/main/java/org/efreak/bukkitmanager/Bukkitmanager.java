package org.efreak.bukkitmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.bukkit.plugin.java.JavaPlugin;

import org.efreak.bukkitmanager.addons.BukkitmanagerAddon;
import org.efreak.bukkitmanager.commands.BmCommandExecutor;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.databases.*;
//import org.efreak.bukkitmanager.external.ArgumentParser;
import org.efreak.bukkitmanager.help.HelpManager;
import org.efreak.bukkitmanager.listener.BukkitListener;
import org.efreak.bukkitmanager.logger.LoggingManager;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.pluginmanager.updater.FilePage;
import org.efreak.bukkitmanager.pluginmanager.updater.PluginPage;
import org.efreak.bukkitmanager.remoteserver.RemoteCommandManager;
import org.efreak.bukkitmanager.remoteserver.RemoteServer;
import org.efreak.bukkitmanager.remoteserver.commands.*;
import org.efreak.bukkitmanager.scripting.ScriptManager;
import org.efreak.bukkitmanager.util.ChatFilter;
//import org.efreak.bukkitmanager.scoreboards.ScoreboardManager;
import org.efreak.bukkitmanager.util.FileHelper;

import com.turt2live.metrics.EMetrics;
import com.turt2live.metrics.graph.DonutGraph;
import com.turt2live.metrics.graph.PieGraph;
import com.turt2live.metrics.tracker.BasicTracker;

public class Bukkitmanager extends JavaPlugin {
	
	private static IOManager io;
	private static Configuration config;
	private static Database db;
	private static JavaPlugin instance;
	private static LoggingManager logManager;
	private static File pluginFile;
	private static EMetrics metrics;
	private static DonutGraph<CommandCategory> commandStatistics;
	private static PieGraph<BukkitmanagerAddon> addonStatistics;
	private static RemoteServer remoteServer;
	private static List<BukkitmanagerAddon> addons;
	public static boolean firstRun = true;

	@Override
	public void onDisable() {
    	db.shutdown();
    	ThreadManager.stopThread(ThreadType.AUTOSAVE);
    	ThreadManager.stopThread(ThreadType.AUTOBACKUP);
    	ThreadManager.stopThread(ThreadType.AUTOMESSAGE);
		getServer().getScheduler().cancelTasks(this);
		if (config.getDev()) remoteServer.shutdown();
		try {
			FileUtils.cleanDirectory(new File(getDataFolder().getParentFile().getAbsoluteFile().getParentFile() + File.separator + "backups" + File.separator + "temp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		io.sendConsole(io.translate("Plugin.Done"));
    }

	@Override
	public void onLoad() {
		instance = this;
		pluginFile = getFile();
		addons = new ArrayList<BukkitmanagerAddon>();
	}

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		config = new Configuration();
		io = new IOManager();
		config.init();
		FileHelper.setupFolderStructure();
		io.init();
		if (config.getDebug()) io.sendConsoleWarning(io.translate("Plugin.Debug"));
		if (firstRun) io.sendConsole(io.translate("Plugin.FirstRun"));
		try {
			metrics = new EMetrics(this);
			commandStatistics = new DonutGraph<CommandCategory>("Command Usage");
			addonStatistics = new PieGraph<BukkitmanagerAddon>("Addon Usage");
			for (BukkitmanagerAddon addon : addons) addonStatistics.addSlice(addon, addon.getAddonName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Permissions().init();
		if (config.getBoolean("Autobackup.Enabled")) ThreadManager.startThread(ThreadType.AUTOBACKUP);
		if (config.getBoolean("Autosave.Enabled")) ThreadManager.startThread(ThreadType.AUTOSAVE);
		if (config.getBoolean("Automessage.Enabled")) ThreadManager.startThread(ThreadType.AUTOMESSAGE);
		Database.registerDatabaseSystem("MySQL", new MySQL());
		Database.registerDatabaseSystem("SQLite", new SQLite());
		db = Database.getDatabaseBySystem(config.getString("Database.System"));
		if (db == null) {
			io.sendConsoleWarning("Unknown Database System. Falling back to SQLite");
			db = Database.getDatabaseBySystem("SQLite");
		}
		db.init();
		logManager = new LoggingManager();
		logManager.initialize();
		new HelpManager().init();
		getServer().getPluginCommand("bm").setExecutor(new BmCommandExecutor());
		getServer().getPluginManager().registerEvents(new BukkitListener(), this);
		new CustomMessageManager().init();
		new FakepluginsManager();
		new PluginManager().init();
		//new ScoreboardManager().init();
		new ScriptManager().init();
		new ChatFilter().init();
		if (config.getDev()) {
			RemoteCommandManager.registerHandler(PlayerCommands.class);
			RemoteCommandManager.registerHandler(ServerCommands.class);
			RemoteCommandManager.registerHandler(WorldCommands.class);
			RemoteCommandManager.registerHandler(PluginCommands.class);
			remoteServer = new RemoteServer();
			remoteServer.init();
		}
		if (config.getBoolean("General.Statistics")) {
			metrics.addGraph(commandStatistics);
			metrics.addGraph(addonStatistics);
			metrics.startMetrics();
		}
		io.sendConsole(io.translate("Plugin.Done"));
		new Thread() {
			public void run() {	
				setName("Bukkitmanager Autoupdater");
				if (config.getBoolean("General.Auto-Updater") &! (config.getBoolean("PluginUpdater.Enabled") && config.getBoolean("PluginUpdater.CheckOnStart"))) {
					io.sendConsole(io.translate("PluginUpdater.CheckingUpdates"));
					PluginPage pluginPage = new PluginPage(instance.getName());
					FilePage filePage = pluginPage.getNewestFile();
					if (!filePage.getName().equals("Bukkitmanager Beta " + getDescription().getVersion())) {
						io.sendConsole(io.translate("AutoUpdater.NewVersion").replaceAll("%name%", filePage.getName()));
						io.sendConsole(io.translate("AutoUpdater.Running").replaceAll("%name%", "Bukkitmanager Beta " + getDescription().getVersion()));
						filePage.download();
					}else io.sendConsole(io.translate("AutoUpdater.UpToDate"));
				}
			}
		}.start();
	}

	/**
	 * 
	 * @return The Plugin Instance
	 * 
	 */
	
	public static JavaPlugin getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @return The currently used IOManager
	 * @see org.efreak.bukkitmanager.IOManager
	 * 
	 */
	
	public static IOManager getIOManager() {
		return io;
	}
	
	/**
	 * 
	 * @return The currently used Database
	 * @see org.efreak.bukkitmanager.Database
	 * 
	 */
	
	public static Database getDb() {
		return db;
	}
	
	/**
	 * 
	 * @return The Configuration
	 * @see org.efreak.bukkitmanager.Configuration
	 * 
	 */
	
	public static Configuration getConfiguration() {
		return config;
	}
	
	public static void reload() {
		
	}
	
	public static File getPluginFile() {
		return pluginFile;
	}
	
	/**
	 * 
	 * Add a Stats Tracker
	 * 
	 * @param tracker
	 * 
	 */
	
	public static void addTracker(BasicTracker tracker) {
		metrics.addTracker(tracker);
	}
	
	/**
	 * 
	 * Set up the Tracker for a Command and add them to Metrics
	 * 
	 * @param command
	 * @see org.efreak.bukkitmanager.commands.Command
	 * 
	 */
	
	public static void addCommandTracker(Command command) {
		if (commandStatistics == null) commandStatistics = new DonutGraph<CommandCategory>("Command Usage");
		commandStatistics.addSlice(command.getCategory(), command.getCategory().toString().toLowerCase(), command.getLabel().toLowerCase());
		addTracker(command.getTracker());
	}
	
	/**
	 * 
	 * Increment all Tracker of a Command
	 * 
	 * @param command
	 * @see org.efreak.bukkitmanager.commands.Command
	 * 
	 */
	
	public static void incrementCommandTracker(Command command) {
		commandStatistics.increment(command.getCategory(), command.getLabel().toLowerCase());
		command.getTracker().increment();
	}
	
	/**
	 * 
	 * Add an Instance of an Addon
	 * 
	 * @param addon
	 * @see org.efreak.bukkitmanager.addons.BukkitmanagerAddon
	 * 
	 */

	public static void addAddon(BukkitmanagerAddon addon) {
		addons.add(addon);
	}
}
