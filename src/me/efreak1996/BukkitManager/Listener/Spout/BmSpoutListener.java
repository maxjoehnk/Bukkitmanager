package me.efreak1996.BukkitManager.Listener.Spout;

import java.util.logging.Logger;

import me.efreak1996.BukkitManager.Bukkitmanager;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;

import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;

public class BmSpoutListener extends SpoutListener {
	
    public BmSpoutListener() {
    }
    public void registerEvents() {
        registerEvent("CUSTOM_EVENT", Priority.Normal);
    }
    private void registerEvent(String typeName, Priority priority) {
        try {
            Event.Type type = Event.Type.valueOf(typeName);
            PluginManager pm = Bukkit.getServer().getPluginManager();
            pm.registerEvent(type, this, priority, new Bukkitmanager());
        } catch (IllegalArgumentException e) {
            log.info("[BukkitManager] Unable to register missing event type " + typeName);
        }
    }
    
    @Override
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
    	//SpoutPlayer splayer = event.getPlayer();
    	//splayer.sendNotification("Achievement get!", "Logged in!", Material.DIAMOND);
    }
    
	public static final Logger log = Logger.getLogger("Minecraft");

}
