package org.efreak1996.Bukkitmanager.PluginManager.Updater;

import java.net.URL;

import org.efreak1996.Bukkitmanager.Configuration;


public class PluginPage {

	public static Configuration config;
	Category category;
	String pluginName;
	DevelopmentState state;
	protected URL mainUrl;
	
	public PluginPage() {
		config = new Configuration();
	}
}
