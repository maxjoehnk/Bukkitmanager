package com.efreak1996.BukkitManager.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.efreak1996.BukkitManager.BmCustomMessageManager;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmCustomMessageListener implements Listener {

	private static BmCustomMessageManager msgManager;
	
	public BmCustomMessageListener() {
		super();
		msgManager = new BmCustomMessageManager();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(parse(msgManager.getPlayerJoin(), event));
	}
	
	
	private String parse(String msg, PlayerEvent event) {
		return BmIOManager.parseColor(msg.replaceAll("%player%", event.getPlayer().getName()).replaceAll("%world%", event.getPlayer().getWorld().getName()).replaceAll("%time%", String.valueOf(event.getPlayer().getWorld().getTime()*1000)));
	}
}
