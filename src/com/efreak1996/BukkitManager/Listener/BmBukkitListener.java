package com.efreak1996.BukkitManager.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class BmBukkitListener implements Listener{
	
	@EventHandler
    public void onPlayerKick(PlayerKickEvent event) {if (event.getPlayer().hasPermission("bm.unkickable")) event.setCancelled(true);}
}
