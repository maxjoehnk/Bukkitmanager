package org.efreak.bukkitmanager.scripting.api;

import java.util.HashMap;

import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.scripting.APIObject;

public class PluginAPI implements APIObject {

	HashMap<String, APIPlugin> plugins;
	
	public APIPlugin getPlugin(String name) {
		return plugins.get(name);
	}

	@Override
	public void loadAPI() {
		plugins = new HashMap<String, APIPlugin>();
		Plugin[] pluginArray = PluginManager.getPlugins();
		for (Plugin plugin : pluginArray) plugins.put(plugin.getName(), new APIPlugin(plugin));
	}
	
}
