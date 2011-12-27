package me.efreak1996.BukkitManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;

@SuppressWarnings("deprecation")
public class BmConfiguration {
	
	public void initalize() {
		if (!(new File(plugin.getDataFolder() + File.separator + "config.yml").exists())){
			Config = new Configuration(new File(plugin.getDataFolder() + File.separator + "config.yml"));
			out.sendConsole("Creating config.yml...");
			try {
				new File(plugin.getDataFolder() + File.separator + "config.yml").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			CreateConfig();
		}else {
			Config = new Configuration(new File(plugin.getDataFolder() + File.separator + "config.yml"));
			//UpdateConfig();
		}
	}

	public static void CreateConfig()
	{
	        	 //Config.setHeader("Bukkitmanager Config File v. 1.1");
	        	 Config.setProperty("General.Use-Permissions", true);
	        	 Config.setProperty("General.Show-Prefix", true);
	        	 Config.setProperty("Plugins.Update", true);
	        	 Config.setProperty("Plugins.Management", true);
	        	 Config.setProperty("Autosave.Interval", 900);
	        	 Config.setProperty("Autosave.Enabled", true);
	        	 Config.setProperty("Autobackup.Interval", 1800);
	        	 Config.setProperty("Autobackup.Enabled", true);
	             Config.setProperty("Spout.Enabled", true);
	             Config.setProperty("Spout.Guest.Enabled", true);
	             Config.setProperty("Spout.Member.Enabled", true);
	             Config.setProperty("Spout.VIP.Enabled", true);
	             Config.setProperty("Spout.Supporter.Enabled", true);
	             Config.setProperty("Spout.Admin.Enabled", true);
	             Config.save();
	}
	
	public static void UpdateConfig() {
		if (Config.getHeader() != "Bukkitmanager Config File v. 1.1") {
			out.sendConsole("Older Config found, updating...");
			try {
	           if (new FileInputStream(new File(plugin.getDataFolder() + File.separator + "config.yml")).read() == -1) {
		        	 Config.setHeader("Bukkitmanager Config File v. 1.0");
		        	 Config.setProperty("General.Use-Permissions", true);
		        	 Config.setProperty("Update.Enbaled", true);
		        	 Config.setProperty("Update.Bukkit", true);
		        	 Config.setProperty("Autosave.interval", 900);
		        	 Config.setProperty("Autosave.enabled", true);
		        	 Config.setProperty("Autobackup.interval", 1800);
		        	 Config.setProperty("Autobackup.enabled", true);
		             Config.setProperty("Spout.Enabled", true);
		             Config.setProperty("Spout.Guest.Enabled", true);
		             Config.setProperty("Spout.Member.Enabled", true);
		             Config.setProperty("Spout.VIP.Enabled", true);
		             Config.setProperty("Spout.Supporter.Enabled", true);
		             Config.setProperty("Spout.Admin.Enabled", true);
	             if (Config.getProperty("Multiworld") != null) {
	            	 Config.removeProperty("Multiworld");
	             }
	             Config.save();
	           }
	         } catch (FileNotFoundException e) {
	           e.printStackTrace();
	         } catch (IOException e) {
	           e.printStackTrace();
	         }
			out.sendConsole("Successfully updated!");
		}
	}
	
	public static boolean checkConfig() {
		return false;
	}
	
	public void create() {
		try {
	        new File(plugin.getDataFolder() + File.separator + "config.yml").createNewFile();
	        Config = new Configuration(new File(plugin.getDataFolder() + File.separator + "config.yml"));
	        Config.load();
	        CreateConfig();
	        Config.save();
	        out.sendConsole("config.yml succesfully created!");
	       }
	      catch (IOException e) {
	        e.printStackTrace(); 
	        log.warning("[BukkitManager] Could'nt create config.yml. Disabling...");
			Bukkit.getServer().getPluginManager().disablePlugin(plugin);
	       }
		}
	
	public Object getEntry(String path) {
		Config.load();
		if (Config.getProperty(path) != null) {
			return Config.getProperty(path);
		}
		return null;		
	}
	
	public String getString(String path) {
		Config.load();
		if (Config.getString(path) != null) {
			return Config.getString(path);
		}
		return null;		
	}
	
	public Boolean getBoolean(String path, Boolean defaultvalue) {
		Config.load();
		return Config.getBoolean(path, defaultvalue);
	}
	
	public int getInteger(String path, int defaultvalue) {
		Config.load();
		return Config.getInt(path, defaultvalue);
	}
	
	public static BmOutput out = new BmOutput();
	static Configuration Config;
	public static Bukkitmanager plugin = (Bukkitmanager) Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager");
	public static final Logger log = Logger.getLogger("Minecraft");
}
