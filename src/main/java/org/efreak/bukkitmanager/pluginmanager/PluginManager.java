package org.efreak.bukkitmanager.pluginmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.pluginmanager.updater.FilePage;
import org.efreak.bukkitmanager.pluginmanager.updater.PluginPage;
import org.efreak.bukkitmanager.util.FileHelper;

public class PluginManager {//implements org.bukkit.plugin.PluginManager {
	
	private static HashMap<Plugin, PluginPage> pluginPages;
	private static HashMap<Plugin, FilePage> filePages;
	private static Configuration config;
	private static IOManager io;
	private static boolean pluginsFetched = false;
	private static boolean updatesAvailable = false;
	
	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public void init() {
		pluginPages = new HashMap<Plugin, PluginPage>();
		filePages = new HashMap<Plugin, FilePage>();
		if (config.getBoolean("PluginUpdater.Enabled")) {
			new Thread() {
				public void run() {
					setName("Bukkitmanager Pluginupdater");
					io.sendConsole(io.translate("PluginUpdater.FetchingPlugins"));
					Plugin[] plugins = getPlugins();
					@SuppressWarnings("unchecked")
					List<String> whitelist = (List<String>) config.getList("PluginUpdater.Whitelist.List");
					if (whitelist == null) whitelist = new ArrayList<String>();
					@SuppressWarnings("unchecked")
					List<String> blacklist = (List<String>) config.getList("PluginUpdater.Blacklist.List");
					if (blacklist == null) blacklist = new ArrayList<String>();
					for (int i = 0; i < plugins.length; i++) {
						if (config.getBoolean("PluginUpdater.Blacklist.Enabled") && blacklist.contains(plugins[i].getName())) continue;
						if (config.getBoolean("PluginUpdater.Whitelist.Enabled") && !whitelist.contains(plugins[i].getName())) continue;
						PluginPage pluginPage = new PluginPage(plugins[i].getName());
						if (pluginPage.exists()) {
					    	pluginPages.put(plugins[i], pluginPage);
					    	filePages.put(plugins[i], pluginPage.getNewestFile());
						}else if (config.getDebug()) io.sendConsoleWarning("Could not load BukkitDev Page of Plugin " + plugins[i].getName());
					}
					pluginsFetched = true;
					io.sendConsole(io.translate("Plugin.Done"));
					if (config.getBoolean("PluginUpdater.CheckOnStart")) {
						io.sendConsole(io.translate("PluginUpdater.CheckingUpdates"));
						for (int i = 0; i < plugins.length; i++) {
							if (!PluginManager.checkPlugin(plugins[i])) updatesAvailable = true;
						}
						io.sendConsole(io.translate("PluginUpdater.UpdatesAvailable"));
						io.sendConsole(io.translate("Plugin.Done"));
					}
				}
			}.start();
		}
	}
	
	public static void clearPlugins() {
		Bukkitmanager.getInstance().getServer().getPluginManager().clearPlugins();
	}

	public static void disablePlugin(Plugin arg0) {
		Bukkitmanager.getInstance().getServer().getPluginManager().disablePlugin(arg0);
	}

	public static void disablePlugins() {
		Bukkitmanager.getInstance().getServer().getPluginManager().disablePlugins();
	}
	
	public static void disablePlugins(Plugin[] plugins) {
		for (int i = 0; i < plugins.length; i++) disablePlugin(plugins[i]);
	}

	public static void enablePlugin(Plugin arg0) {
		Bukkitmanager.getInstance().getServer().getPluginManager().enablePlugin(arg0);
	}

	public static void enablePlugins() {
		Plugin[] plugins = getPlugins();
		for (int i = 0; i < plugins.length; i++) enablePlugin(plugins[i]);
	}
	
	public static void enablePlugins(Plugin[] plugins) {
		for (int i = 0; i < plugins.length; i++) enablePlugin(plugins[i]);
	}
	
	public static void restartPlugin(Plugin plugin) {
		disablePlugin(plugin);
		enablePlugin(plugin);
	}
	
	public static void restartPlugins() {
		Plugin[] plugins = Bukkitmanager.getInstance().getServer().getPluginManager().getPlugins();
		for (int i = 0; i < plugins.length; i++) restartPlugin(plugins[i]);
	}
	
	public static void restartPlugins(Plugin[] plugins) {
		for (int i = 0; i < plugins.length; i++) restartPlugin(plugins[i]);
	}

	public static Plugin getPlugin(String plugin) {
		return Bukkitmanager.getInstance().getServer().getPluginManager().getPlugin(plugin);
	}

	public static Plugin[] getPlugins() {
		return Bukkitmanager.getInstance().getServer().getPluginManager().getPlugins();
	}

	public static boolean isPluginEnabled(String name) {
		return Bukkitmanager.getInstance().getServer().getPluginManager().isPluginEnabled(name);
	}

	public static boolean isPluginEnabled(Plugin plugin) {
		return Bukkitmanager.getInstance().getServer().getPluginManager().isPluginEnabled(plugin);
	}

	public static Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
		return Bukkitmanager.getInstance().getServer().getPluginManager().loadPlugin(file);
	}

	public static Plugin[] loadPlugins(File dir) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
		return Bukkitmanager.getInstance().getServer().getPluginManager().loadPlugins(dir);
	}
	
	public static boolean unloadPlugin(Plugin plugin) {
		if (plugin.isEnabled()) disablePlugin(plugin);
		try {
			SimplePluginManager spm = (SimplePluginManager) Bukkit.getPluginManager();
			Field field = SimplePluginManager.class.getDeclaredField("lookupNames");
			field.setAccessible(true);
			Map<String, Plugin> lookupNames = (Map<String, Plugin>) field.get(spm);
			field = spm.getClass().getDeclaredField("plugins");
			field.setAccessible(true);
			List<Plugin> plugins = (List<Plugin>) field.get(spm);
			lookupNames.remove(plugin.getName());
			plugins.remove(plugin);
		}catch(Exception e) {
			if (config.getDebug()) e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void unloadPlugins(Plugin[] plugins) {
		for (int i = 0; i < plugins.length; i++) unloadPlugin(plugins[i]);
	}
	
	public static void unloadPlugins() {
		Plugin[] plugins = Bukkitmanager.getInstance().getServer().getPluginManager().getPlugins();
		for (int i = 0; i < plugins.length; i++) unloadPlugin(plugins[i]);
	}

	public static void updatePluginDB() {
		
	}
	
	public static void updatePlugin(Plugin plugin) {
		FilePage filePage = filePages.get(plugin);
		if (filePage == null) return;
		filePage.download();
	}

	public static void updatePlugins() {
		Plugin[] plugins = getPlugins();
		for (int i = 0; i < plugins.length; i++) {
			if (!PluginManager.checkPlugin(plugins[i])) PluginManager.updatePlugin(plugins[i]);
		}
	}
	
	public static boolean checkPlugin(Plugin plugin) {
		if (!pluginsFetched) return true;
		if (pluginPages.containsKey(plugin)) {
			FilePage filePage = filePages.get(plugin);
			if (filePage == null) return true;
			if (filePage.getName().contains(plugin.getDescription().getVersion())) return true;
			else return false;
		}else return true;
	}

	public static File getUpdateFolder() {
		return FileHelper.getUpdateDir();
	}

	public static void reloadPlugin(Plugin plugin) throws UnknownDependencyException, InvalidPluginException, InvalidDescriptionException, FileNotFoundException {
		File pluginFile = null;
		try {
			Field field = JavaPlugin.class.getDeclaredField("file");
			field.setAccessible(true);
			pluginFile = (File) field.get(plugin);
		}catch(Exception e) {
			if (config.getDebug()) e.printStackTrace();
		}
		if (pluginFile == null) pluginFile = new File(FileHelper.getPluginDir(), plugin.getName() + ".jar");
		unloadPlugin(plugin);
		if (pluginFile != null && pluginFile.exists()) loadPlugin(pluginFile);
		else throw new FileNotFoundException();
	}
	
	public static void reloadPlugins(Plugin[] plugins) throws UnknownDependencyException, InvalidPluginException, InvalidDescriptionException, FileNotFoundException {
		for (int i = 0; i < plugins.length; i++) reloadPlugin(plugins[i]);
	}
	
	public static void reloadPlugins() throws UnknownDependencyException, InvalidPluginException, InvalidDescriptionException, FileNotFoundException {
		Plugin[] plugins = Bukkitmanager.getInstance().getServer().getPluginManager().getPlugins();
		for (int i = 0; i < plugins.length; i++) reloadPlugin(plugins[i]);
	}

	public static int getPluginUpdateCount() {
		if (!updatesAvailable) return 0;
		Plugin[] plugins = PluginManager.getPlugins();
		int updateCount = 0;
		for (int i = 0; i < plugins.length; i++) {
			if (!PluginManager.checkPlugin(plugins[i])) updateCount++;
		}
		return updateCount;
	}
	
	public static String getPluginList() {
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = PluginManager.getPlugins();
		for (Plugin plugin : plugins) {
			if (pluginList.length() > 0) {
				pluginList.append(ChatColor.WHITE);
				pluginList.append(", ");
			}
			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getFullName());
		}
		return pluginList.toString();
	}

}
