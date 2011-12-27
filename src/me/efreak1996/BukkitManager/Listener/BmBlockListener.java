package me.efreak1996.BukkitManager.Listener;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class BmBlockListener extends PlayerListener{
	
    public BmBlockListener(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        
    }

    @SuppressWarnings("unused")
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
