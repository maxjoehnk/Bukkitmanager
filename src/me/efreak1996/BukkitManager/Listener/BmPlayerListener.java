package me.efreak1996.BukkitManager.Listener;

import java.util.logging.Logger;

import me.efreak1996.BukkitManager.Bukkitmanager;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;

public class BmPlayerListener extends PlayerListener{
	
    public BmPlayerListener(Bukkitmanager plugin) {
        this.plugin = plugin;
    }

    public void onPlayerKick(PlayerKickEvent event) {if (event.getPlayer().hasPermission("bm.unkickable")) event.setCancelled(true);}
    
    public void registerEvents() {
        registerEvent("PLAYER_INTERACT", Priority.Normal);
        registerEvent("PLAYER_DROP_ITEM", Priority.Normal);
        registerEvent("PLAYER_PICKUP_ITEM", Priority.Normal);
        registerEvent("PLAYER_JOIN", Priority.Normal);
        registerEvent("PLAYER_LOGIN", Priority.Normal);
        registerEvent("PLAYER_QUIT", Priority.Normal);
        registerEvent("PLAYER_BUCKET_FILL", Priority.Normal);
        registerEvent("PLAYER_BUCKET_EMPTY", Priority.Normal);
        registerEvent("PLAYER_RESPAWN", Priority.Normal);
        registerEvent("PLAYER_ITEM_HELD", Priority.Normal);
        registerEvent("PLAYER_BED_ENTER", Priority.Normal);
        registerEvent("PLAYER_COMMAND_PREPROCESS", Priority.Normal);
        registerEvent("PLAYER_MOVE", Priority.Normal);
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
	private Bukkitmanager plugin;

}
