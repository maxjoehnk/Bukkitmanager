package org.efreak.bukkitmanager.remoteserver.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.remoteserver.RemoteCommand;
import org.efreak.bukkitmanager.remoteserver.RemoteCommandHandler;
import org.efreak.bukkitmanager.remoteserver.ReturnCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerCommands extends RemoteCommandHandler {

	@RemoteCommand(name="playerlist")
	public String worldlist() {
		JSONArray json = new JSONArray();
		for (Player player : Bukkit.getOnlinePlayers()) {
			try {
				String object = new JSONObject()
					.put("name", player.getName())
					.put("level", player.getLevel())
					.put("health", player.getHealth())
					.put("foodlevel", player.getFoodLevel())
					.put("gamemode", player.getGameMode().toString())
					.put("world", player.getWorld().getName())
					.toString();
				json.put(object);
			} catch (JSONException e) {
				if (config.getDebug()) e.printStackTrace();
				return ReturnCodes.echo500();
			}
		}
		return json.toString();
	}
	
}
