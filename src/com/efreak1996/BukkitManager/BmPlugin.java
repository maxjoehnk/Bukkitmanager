package com.efreak1996.BukkitManager;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Our Mainclass for the onLoad, onEnable and onDisable Functions
 * 
 * @author efreak1996
 *
 * @version Alpha 1.3
 * 
 */

public class BmPlugin extends JavaPlugin {
	
	/**
	 * 
	 * Runs while shutdown the server
	 * 
	 * @see BmShutdown
	 * 
	 */
	
	@Override
	public void onDisable() {
        new BmShutdown();
	}
	
	/**
	 * 
	 * Runs after loading the Plugin
	 * 
	 */
	
	@Override
	public void onLoad() {
	}

	/**
	 * 
	 * Runs after loading of all plugins and worlds
	 * 
	 * @see BmInitialize
	 * 
	 */
	
	@Override
	public void onEnable() {
		new BmInitialize();
	}
}
