package org.efreak.bukkitmanager.remoteserver.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

import org.efreak.bukkitmanager.remoteserver.RemoteCommand;
import org.efreak.bukkitmanager.remoteserver.RemoteCommandHandler;
import org.efreak.bukkitmanager.remoteserver.ReturnCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WorldCommands extends RemoteCommandHandler {

	@RemoteCommand(name="worldlist")
	public String worldlist() {
		JSONArray json = new JSONArray();
		List<World> worlds = Bukkit.getWorlds();
		for (World world : worlds) {
			try {
				String object = new JSONObject()
					.put("name", world.getName())
					.put("worldtype", world.getWorldType().toString())
					.put("environment", world.getEnvironment().toString())
					.put("seed", world.getSeed())
					.put("pvp", world.getPVP())
					//.put("players", world.getPlayers())
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
