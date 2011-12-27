package me.efreak1996.BukkitManager.Listener.Spout;

import java.util.logging.Logger;

import me.efreak1996.BukkitManager.Spout.BmGuiAdmin;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.gui.GenericButton;

public class BmSpoutScreenListener extends ScreenListener {
	
    public BmSpoutScreenListener(Plugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void onButtonClick(ButtonClickEvent event) {
    	if (event.getButton() instanceof GenericButton && event.getButton().getText().equals("General")) {
    		BmGuiAdmin.general();
    	}else if (event.getButton() instanceof GenericButton && event.getButton().getText().equals("Permissions")) {
    		BmGuiAdmin.permissions();
    	}
    }
    
    public void registerEvents() {
        registerEvent("CUSTOM_EVENT", Priority.Normal);
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
