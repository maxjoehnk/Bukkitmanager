package org.efreak.bukkitmanager.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.efreak.bukkitmanager.CustomMessageManager;
import org.efreak.bukkitmanager.IOManager;


public class CustomMessageListener implements Listener {

	private static CustomMessageManager msgManager;
	
	public CustomMessageListener() {
		super();
		msgManager = new CustomMessageManager();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(parse(msgManager.getPlayerJoin(), event));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(parse(msgManager.getPlayerLeave(), event));
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		event.setLeaveMessage(parse(msgManager.getPlayerKick().replaceAll("%reason%", event.getReason()), event));
	}
	
	private String parse(String msg, PlayerEvent event) {
		return IOManager.parseColor(msg.replaceAll("%player%", event.getPlayer().getName()).replaceAll("%world%", event.getPlayer().getWorld().getName()).replaceAll("%time%", String.valueOf(event.getPlayer().getWorld().getTime()*1000)));
	}
}
