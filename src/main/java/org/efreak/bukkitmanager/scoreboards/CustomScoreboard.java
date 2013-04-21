package org.efreak.bukkitmanager.scoreboards;

import org.bukkit.entity.Player;

public class CustomScoreboard {

	private Player player;
	private String name;
	
	public CustomScoreboard(String name, Player player) {
		this.name = name;
		this.player = player;
	}
		
	public void sendItem(String itemName, int value) {
	}
	
	public void removeItem(String itemName) {
	}
}
