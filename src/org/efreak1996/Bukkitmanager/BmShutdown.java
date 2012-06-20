package org.efreak1996.Bukkitmanager;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.Logger.BmLoggingManager;
import org.efreak1996.Bukkitmanager.Swing.BmSwing;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;
import org.efreak1996.Bukkitmanager.Util.BmThreadManager;

//import org.efreak1996.Bukkitmanager.Webinterface.BmWebinterface;

/**
 * 
 * The Stuff which should be done at servershutdown
 * 
 * @author efreak1996
 * 
 * @see Bukkitmanager#onDisable()
 *
 */

public class BmShutdown {
	
	private static BmDatabase db;
	private static Plugin plugin;
    private static BmIOManager io;
	private static BmThreadManager func;
	private static BmConfiguration config;
	private static BmLoggingManager logManager;

	public BmShutdown() {
    	db = new BmDatabase();
    	logManager = new BmLoggingManager();
    	logManager.shutdown();
    	plugin = BmPlugin.getPlugin();
    	io = new BmIOManager();
    	func = new BmThreadManager();
    	config = new BmConfiguration();
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
			new BmSwing().shutdown();
			//io.sendConsoleDev("Disabling Webinterface Server...");
			//new BmWebinterface().shutdown();
			io.sendConsoleDev("Development Features disabled!");
		}
    	io.sendConsole(io.translate("Plugin.Done"));
    }
}
