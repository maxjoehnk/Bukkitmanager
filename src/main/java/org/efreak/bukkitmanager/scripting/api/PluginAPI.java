package org.efreak.bukkitmanager.scripting.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.scripting.APIObject;

public class PluginAPI implements APIObject {

	HashMap<String, APIPlugin> plugins;
	private static Configuration config;
	
	static {
		config = Bukkitmanager.getConfiguration();
	}
	
	public APIPlugin getPlugin(String name) {
		return plugins.get(name);
	}

	public List<APIPlugin> getPluginList() {
		return new ArrayList<APIPlugin>(plugins.values());
	}
	
	@Override
	public boolean loadAPI() {
		plugins = new HashMap<String, APIPlugin>();
		Plugin[] pluginArray = PluginManager.getPlugins();
		for (Plugin plugin : pluginArray) plugins.put(plugin.getName(), new APIPlugin(plugin));
		return true;
	}
	
	public APIPlugin loadPlugin(String filePath) {
		try {
			Plugin plugin = PluginManager.loadPlugin(new File(filePath));
			return plugins.put(plugin.getName(), new APIPlugin(plugin));
		} catch (UnknownDependencyException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (InvalidPluginException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (InvalidDescriptionException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return null;
	}
}
