package me.efreak1996.BukkitManager.Listener;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class BmServerListener extends ServerListener {
	
    public BmServerListener(Plugin plugin) {
        this.plugin = plugin;
    }
    
    public void onPluginEnable(PluginEnableEvent event) {
    	/*log.info("PluginEnableEvent");
    	if (BmDatabase.getPlugin(event.getPlugin()) != null) {
    		Object[] infos = BmDatabase.getPlugin(event.getPlugin());
    		if (infos[1] == "false") {
    			Bukkit.getServer().getPluginManager().disablePlugin(event.getPlugin());
    		}
    	}*/
    }

    public void registerEvents() {
        registerEvent("PLUGIN_ENABLE", Priority.Normal);
    }

	private void registerEvent(String typeName, Priority priority) {
        try {
            Event.Type type = Event.Type.valueOf(typeName);
            PluginManager pm = plugin.getServer().getPluginManager();
            pm.registerEvent(type, this, priority, plugin);
        } catch (IllegalArgumentException e) {
            log.info("[BukkitManager] Unable to register missing event type " + typeName);
        }
    }
	
	public static final Logger log = Logger.getLogger("Minecraft");
	private Plugin plugin;

}
