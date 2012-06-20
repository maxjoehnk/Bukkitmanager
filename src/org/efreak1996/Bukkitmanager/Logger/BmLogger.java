package org.efreak1996.Bukkitmanager.Logger;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmPlugin;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public abstract class BmLogger extends Logger implements Listener {

	public static BmIOManager io;
	public static BmConfiguration config;
	public static Plugin plugin;
	public String eventType;
	public BmHandler handler;
	public boolean fileLogging;
	public boolean dbLogging;

	public BmLogger(String arg1loggerName, String arg2eventType) {
		super(arg1loggerName, null);
		eventType = arg2eventType;
	}
	
	public void info(HashMap<String, Object> values) {
		if (dbLogging) handler.logDb(values);
		if (fileLogging) info(handler.logFile(values));
	}
	
	public abstract void setupLogger();
	
	public final void initialize(BmHandler arg1handler) {
		io = new BmIOManager();
		config = new BmConfiguration();
		handler = arg1handler;
		plugin = BmPlugin.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.setUseParentHandlers(true);
		this.addHandler(arg1handler);
		//this.addHandler(plugin.getServer().getLogger().getHandlers()[0]);
	}
	
	public final void shutdown() {
		handler.shutdown();
	}
}
