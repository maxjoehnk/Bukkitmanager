package org.efreak.bukkitmanager.logger;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

public abstract class BmLogger extends Logger implements Listener {

    public static IOManager io;
    public static Configuration config;
    public static Plugin plugin;
    public String eventType;
    public LoggingHandler handler;
    public boolean fileLogging;
    public boolean dbLogging;

    static {
        io = Bukkitmanager.getIOManager();
        config = Bukkitmanager.getConfiguration();
        plugin = Bukkitmanager.getInstance();
    }

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
        handler = arg1handler;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.setUseParentHandlers(true);
        this.addHandler(arg1handler);
        // this.addHandler(plugin.getServer().getLogger().getHandlers()[0]);
    }

    public final void shutdown() {
        handler.shutdown();
    }
}
