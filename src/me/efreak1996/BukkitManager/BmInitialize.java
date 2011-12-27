package me.efreak1996.BukkitManager;

import java.io.File;
import java.util.logging.Logger;

import me.efreak1996.BukkitManager.Commands.*;
import me.efreak1996.BukkitManager.Listener.*;
import me.efreak1996.BukkitManager.Listener.Spout.*;
import me.efreak1996.BukkitManager.Spout.BmGuiGeneral;

import me.taylorkelly.help.Help;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

public class BmInitialize {

	public static void Initialize(){
		out.sendConsole(ChatColor.WHITE + "enabling...");
		Bukkit.getServer().getUpdateFolderFile().mkdirs();
		PluginDescriptionFile pdfFile = plugin.getDescription();
		CreateDirs();
		config.initalize();
		Listener();
		Permissions();
		Help();
		BmDatabase.initialize();
		Commands();
		out.sendConsole(ChatColor.WHITE + "Version " + pdfFile.getVersion() + " enabled");
	}
	
	public static void CreateDirs() {
		if (!(plugin.getDataFolder().exists())){
			out.sendConsole(ChatColor.WHITE + "Creating MainDir...");
			plugin.getDataFolder().mkdirs();
			out.sendConsole(ChatColor.WHITE + "MainDir created succesfully!");
		}
		if (!(new File(plugin.getDataFolder() + File.separator + "externalsources").exists())){
			new File (plugin.getDataFolder() + File.separator + "externalsources").mkdir();
		}
	}

	public static void Help()
	{
        if (Bukkit.getServer().getPluginManager().getPlugin("Help") != null) {
            Help helpPlugin = ((Help) plugin.getServer().getPluginManager().getPlugin("Help"));
            helpPlugin.registerCommand("help bukkitmanager", "Shows the Help help", plugin, true);
            helpPlugin.registerCommand("bm help", "Shows the internal Help", plugin, "bm.help");
            helpPlugin.registerCommand("bm update", "!Updates the sources", plugin, "bm.update.source");
            helpPlugin.registerCommand("bm updatebukkit [rc|stable]", "!Updates Bukkit", plugin, "bm.update.bukkit");
            helpPlugin.registerCommand("bm updateplugin [plugin|all]", "!Updates your Plugins", plugin, "bm.update.plugin");
            helpPlugin.registerCommand("bm plugin list", "Shows a Plugin list", plugin, "bm.plugin.list");
            helpPlugin.registerCommand("bm plugin info [plugin]", "Shows all available Infos of a plugin", plugin, "bm.plugin.info");
            helpPlugin.registerCommand("bm plugin enable (plugin)", "Enable Plugins", plugin, "bm.plugin.enable");
            helpPlugin.registerCommand("bm plugin disable (plugin)", "Disable Plugins", plugin, "bm.plugin.disable");
            helpPlugin.registerCommand("bm config (entry) [value]", "Change the Config entrys", plugin, "bm.config");
            out.sendConsole("Help support enabled.");
        } else {
        	out.sendConsole("Help isn't detected. No /help support.");
        }
	}
	public static void Permissions()
	{
		if (config.getBoolean("General.Use-Permissions", true)) {
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit") != null) {            
			out.sendConsole("PermissionsBukkit support enabled.");
        } else
        {
			log.warning("[BukkitManager] No Permissions System found!");	
			out.sendConsole("Using OP-Rights for Commands!");
        }
		} else {
			out.sendConsole("Using OP-Rights for Commands!");
		}
	}

	public static void Commands()
	{
		out.sendConsole("Loading Commands...");
		Bukkit.getServer().getPluginCommand("bm").setExecutor(new BmCommandExecutor());
		BmConfig.initialize();
		BmHelp.initialize();
		BmPluginUpdate.initialize();
		out.sendConsole("Commands succesfully loaded.");
	}
	public static boolean Spout()
	{
		if (config.getBoolean("Spout.Enabled", true)) {
		if (plugin.getServer().getPluginManager().getPlugin("Spout") != null) {            
			out.sendConsole("Spout support enabled.");
			out.sendConsole("Initializing Gui...");
			BmGuiGeneral.initialize(plugin);
			out.sendConsole("Gui initialized!");
			return true;
        } else
        {
			log.warning("[BukkitManager] Spout not found!");
			return false;
        }
		}
		return false;
	}
	@SuppressWarnings("unused")
	public static void Listener()
	{
		out.sendConsole("Registering Events");
		(new BmBlockListener(plugin)).registerEvents();
		(new BmEntityListener(plugin)).registerEvents();
		(new BmPlayerListener(plugin)).registerEvents();
		(new BmServerListener(plugin)).registerEvents();
		(new BmWeatherListener(plugin)).registerEvents();
		(new BmWorldListener(plugin)).registerEvents();
		if (false/*Spout()*/) {
			(new BmSpoutListener()).registerEvents();
		}
		out.sendConsole("Events registered");
	}
	
	public static final Logger log = Logger.getLogger("Minecraft");
	public static Bukkitmanager plugin = (Bukkitmanager) Bukkit.getServer().getPluginManager().getPlugin("Bukkitmanager");
	public static BmOutput out = new BmOutput();
	public static BmConfiguration config = new BmConfiguration();
}
