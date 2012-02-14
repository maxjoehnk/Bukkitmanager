package com.efreak1996.BukkitManager;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.efreak1996.BukkitManager.Commands.*;
import com.efreak1996.BukkitManager.Commands.BmPlugin;
import com.efreak1996.BukkitManager.Listener.BmBukkitListener;
/*import com.efreak1996.BukkitManager.Listener.Spout.*;
import com.efreak1996.BukkitManager.Listener.Spout.BmSpoutListener;
import com.efreak1996.BukkitManager.Spout.BmGuiGeneral;*/

/**
 * 
 * Manages the start of Bukkitmanager 
 *
 * @author efreak1996
 *
 * @version Alpha 1.3
 * 
 */

public class BmInitialize {

	/**
	 * 
	 * @see Bukkitmanager#onEnable()
	 * 
	 */
	
	public BmInitialize() {
		CreateDirs();
		config.initalize();
		//BmDatabase.initialize();
		//BmAutomessage.initialize();
		Commands();
		Bukkit.getPluginManager().registerEvents(new BmBukkitListener(), plugin);
		Permissions();
		Threads();
		out.sendConsole(ChatColor.GREEN + "Done!");
	}
	
	/**
	 * 
	 * Creates the Folderstructure of Bukkitmanager
	 * 
	 */
	
	public static void CreateDirs() {
		if (!(plugin.getDataFolder().exists()))
				plugin.getDataFolder().mkdirs();
		if (!(new File(plugin.getDataFolder() + File.separator + "externalsources").exists()))
				new File (plugin.getDataFolder() + File.separator + "externalsources").mkdir();
		//if (!(new File(plugin.getDataFolder() + File.separator + "players").exists()))
				//new File (plugin.getDataFolder() + File.separator + "players").mkdir();
		//if (!(new File(plugin.getDataFolder() + File.separator + "filter").exists()))
				//new File (plugin.getDataFolder() + File.separator + "filter").mkdir();
		if (!(new File(plugin.getDataFolder().getParentFile().getParentFile() + File.separator + "backups" + File.separator + "temp").exists()))
			new File(plugin.getDataFolder().getParentFile().getParentFile() + File.separator + "backups" + File.separator + "temp").mkdirs();
		Bukkit.getServer().getUpdateFolderFile().mkdirs();		
	}
	
	/**
	 * 
	 * Manages Permissions and checks whether to use Permissions or OP-Rights
	 * 
	 */
	
	public static void Permissions() {
		if (config.Config.getBoolean("General.Use-Permissions")) {
			if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit") != null)            
				out.sendConsole("PermissionsBukkit support enabled.");
			else if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null)             
				out.sendConsole("PermissionsEx support enabled.");
        	else {
				out.sendConsoleWarning("[BukkitManager] No Permissions System found!");	
				out.sendConsole("Using OP-Rights for Commands!");
        	}
		}else {
			out.sendConsole("Using OP-Rights for Commands!");
		}
	}

	/**
	 * 
	 * Loads all commands and the commandexecutor
	 * 
	 */
	
	public static void Commands() {
		out.sendConsole("Loading Commands...");
		Bukkit.getServer().getPluginCommand("bm").setExecutor(new BmCommandExecutor());
		BmAutomessage.initialize();
		BmHelp.initialize();
		BmBukkit.initialize();
		BmPlugin.initialize();
		out.sendConsole("Commands succesfully loaded.");
	}
	
	/**
	 * 
	 * Checks whether Spout is installed or not and loads Spoutspecific stuff
	 * 
	 */
	
	public static void Spout() {
		if (config.Config.getBoolean("Spout.Enabled")) {
			if (plugin.getServer().getPluginManager().getPlugin("Spout") != null) {            
				out.sendConsole("Spout support enabled.");
				out.sendConsole("Initializing Spoutgui...");
				//BmGuiGeneral.initialize();
				out.sendConsole("Spoutgui initialized!");
			} else out.sendConsoleWarning("Spout not found!");
		}
	}
	
	public static void Threads() {
		if (config.Config.getBoolean("Autosave.Enabled")) BmFunctions.startThread(BmThreadType.AUTOSAVE);
		if (config.Config.getBoolean("Automessage.Enabled")) BmFunctions.startThread(BmThreadType.AUTOMESSAGE);
	}
	
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitManager");
	private static PluginDescriptionFile pdfFile = plugin.getDescription();
	private static BmOutput out = new BmOutput();
	private static BmConfiguration config = new BmConfiguration();
	public static String version = "Alpha " + pdfFile.getVersion();
}
