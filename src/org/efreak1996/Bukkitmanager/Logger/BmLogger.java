package org.efreak1996.Bukkitmanager.Logger;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.IOManager;

public abstract class BmLogger extends Logger implements Listener {

	public static IOManager io;
	public static Configuration config;
	public static Plugin plugin;
	public String eventType;
	public LoggingHandler handler;
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
	
	public final void initialize(LoggingHandler arg1handler) {
		io = new IOManager();
		config = new Configuration();
		handler = arg1handler;
		plugin = Bukkitmanager.getInstance();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.setUseParentHandlers(true);
		this.addHandler(arg1handler);
		//this.addHandler(plugin.getServer().getLogger().getHandlers()[0]);
	}
	
	public final void shutdown() {
		handler.shutdown();
	}
}
