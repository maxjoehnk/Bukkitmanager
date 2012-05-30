package com.efreak1996.BukkitManager.PluginManager.Updater;

import java.net.URL;

import com.efreak1996.BukkitManager.BmConfiguration;

public class PluginPage {

	public static BmConfiguration config;
	Category category;
	String pluginName;
	DevelopmentState state;
	protected URL mainUrl;
	
	public PluginPage() {
		config = new BmConfiguration();
	}
}
