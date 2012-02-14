package com.efreak1996.BukkitManager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * 
 * Loads and manages the config File
 * 
 * @author efreak1996
 * 
 * @version Alpha 1.3
 *
 */

public class BmConfiguration{
	 
	/**
	 * 
	 * Loads and creates if needed the config
	 *
	 */
	
	public void initalize() {
		Config = plugin.getConfig();
		if (!(ConfigFile.exists())){
			out.sendConsole("Creating config.yml...");
			try {
				ConfigFile.createNewFile();
				CreateConfig();
				Config.save(ConfigFile);
		        out.sendConsole("config.yml succesfully created!");
		        Config.load(ConfigFile);
			} catch (IOException e) {
				if (Config.getBoolean("General.Debug")) e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				if (Config.getBoolean("General.Debug")) e.printStackTrace();
			}
		}else {
	        try {
				Config.load(ConfigFile);
				UpdateConfig();
				Config.load(ConfigFile);
			} catch (IOException e) {
				if (Config.getBoolean("General.Debug")) e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				if (Config.getBoolean("General.Debug")) e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * Creates a new config
	 * 
	 */
	
	public void CreateConfig() {
	    Config.set("General.Use-Permissions", true);
	    Config.set("General.Show-Prefix", true);
	    Config.set("General.Update-Sources-on-start", false);
	    Config.set("General.Debug", false);
	    Config.set("Automessage.Enabled", false);
	    Config.set("Automessage.Interval", 120);
	    //Config.set("Automessage.Random", true);
	    Config.set("Automessage.Prefix", "[AutoMessage]");
	    Config.set("Plugins.CheckUpdate.on-start", false);
	    Config.set("Plugins.CheckUpdate.on-adminlogin", false);
	    Config.set("Bukkit.CheckUpdate.on-start",false);
	    Config.set("Bukkit.CheckUpdate.on-adminlogin", false);
	    Config.set("Autosave.Interval", 900);
	    Config.set("Autosave.Enabled", true);
	    //Config.set("Autosave.Task-Mode", 0);
		/*warntimes.add(60);
	    warntimes.add(300);
	    Config.set("Autosave.Warntimes", warntimes);*/
	    Config.set("Autobackup.Interval", 3600);
	    Config.set("Autobackup.Enabled", true);
	    Config.set("Autobackup.Backup.Worlds", true);
	    Config.set("Autobackup.Backup.Plugins", true);
	    Config.set("Autobackup.Backup.craftbukkit.jar", true);
	    /*Config.set("Webinterface.Enabled", false);
	    Config.set("Webinterface.ip", null);
	    Config.set("Webinterface.Port", 7);*/
	}
	
	/**
	 * 
	 * Updates a already existing config
	 * 
	 * @throws IOException
	 */
	
	public void UpdateConfig() throws IOException {
		if (!Config.contains("General.Use-Permissions")) Config.set("General.Use-Permissions", true);
		if (!Config.contains("General.Show-Prefix")) Config.set("General.Show-Prefix", true);
		if (!Config.contains("General.Update-Sources-on-start")) Config.set("General.Update-Sources-on-start", false);
		if (!Config.contains("General.Debug")) Config.set("General.Debug", false);
	    if (!Config.contains("Automessage.Enabled")) Config.set("Automessage.Enabled", false);
		if (!Config.contains("Automessage.Interval")) Config.set("Automessage.Interval", 120);
		//if (!Config.contains("Automessage.Random")) Config.set("Automessage.Random", true);
		if (!Config.contains("Automessage.Prefix")) Config.set("Automessage.Prefix", "[AutoMessage]");
		if (!Config.contains("Autosave.Interval")) Config.set("Autosave.Interval", 900);
		if (!Config.contains("Autosave.Enabled")) Config.set("Autosave.Enabled", true);
		//if (!Config.contains("Autosave.Task-Mode")) Config.set("Autosave.Task-Mode", 0);		
		/*if (!Config.contains("Autosave.Warntimes")) {
			warntimes.add(60);
		    warntimes.add(300);
			Config.set("Autosave.Warntimes", warntimes);
		}*/
		if (!Config.contains("Autobackup.Interval")) Config.set("Autobackup.Interval", 3600);
		if (!Config.contains("Autobackup.Enabled")) Config.set("Autobackup.Enabled", true);
		if (!Config.contains("Plugins.CheckUpdate.on-start")) Config.set("Plugins.CheckUpdate.on-start", false);
		if (!Config.contains("Plugins.CheckUpdate.on-adminlogin")) Config.set("Plugins.CheckUpdate.on-adminlogin", false);
		if (!Config.contains("Bukkit.CheckUpdate.on-start")) Config.set("Bukkit.CheckUpdate.on-start", false);
		if (!Config.contains("Bukkit.CheckUpdate.on-adminlogin")) Config.set("Bukkit.CheckUpdate.on-adminlogin", false);
		Config.save(ConfigFile);
	}
	
	private BmOutput out = new BmOutput();
	public Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("BukkitManager");
	public static FileConfiguration Config;
	File ConfigFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
	//private List<Integer> warntimes;
}
