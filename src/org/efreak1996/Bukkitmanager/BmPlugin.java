package org.efreak1996.Bukkitmanager;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.efreak1996.Bukkitmanager.PluginManager.BmPluginManager;


/**
 * Our Mainclass for the onLoad, onEnable and onDisable Functions
 * 
 * @author efreak1996
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
		com.jidesoft.utils.Lm.verifyLicense("Max Joehnk", "Bukkitmanager", "YrreBQlj.lyFAbpwcAUamoLF3Moy0zN");
		plugin = this;
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
		new BmInitialize(this);
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
	
	private static Plugin plugin;
}
