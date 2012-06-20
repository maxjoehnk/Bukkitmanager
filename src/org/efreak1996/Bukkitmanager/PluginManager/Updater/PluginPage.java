package org.efreak1996.Bukkitmanager.PluginManager.Updater;

import java.net.URL;

import org.efreak1996.Bukkitmanager.BmConfiguration;


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
