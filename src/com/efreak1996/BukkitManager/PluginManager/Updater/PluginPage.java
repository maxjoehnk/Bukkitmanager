package com.efreak1996.BukkitManager.PluginManager.Updater;

import java.net.MalformedURLException;
import java.net.URL;

import com.efreak1996.BukkitManager.BmConfiguration;

public class PluginPage {

	public static BmConfiguration config;
	Category category;
	String pluginName;
	DevelopmentState state;
	URL mainUrl;
	
	public PluginPage() {
		try {
			mainUrl = new URL("http://dev.bukkit.org/server-mods/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
}
