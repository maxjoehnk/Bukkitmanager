package org.efreak.bukkitmanager.scripting.api;

import java.util.HashMap;

import org.bukkit.OfflinePlayer;
import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.scripting.APIObject;

public class PlayerAPI implements APIObject {

	HashMap<String, APIPlayer> players;
	
	public APIPlayer getPlayer(String name) {
		return players.get(name);
	}

	@Override
	public boolean loadAPI() {
		players = new HashMap<String, APIPlayer>();
		return true;
	}
	
	public void addPlayer(OfflinePlayer player) {
		players.put(player.getName(), new APIPlayer(new BmPlayer(player)));
	}

	public void addPlayer(BmPlayer player) {
		players.put(player.getName(), new APIPlayer(player));
	}
	
	public void remPlayer(String name) {
		players.remove(name);
	}
	
	public void remPlayer(OfflinePlayer player) {
		players.remove(player.getName());
	}
	
	public void remPlayer(BmPlayer player) {
		players.remove(player.getName());
	}
}
