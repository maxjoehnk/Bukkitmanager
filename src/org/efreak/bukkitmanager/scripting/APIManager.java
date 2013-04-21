package org.efreak.bukkitmanager.scripting;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.scripting.api.*;

public class APIManager {

	private HashMap<String, APIObject> apiObjects;
	private HashMap<String, APICommand> cmds;

	public APIManager() {
		apiObjects = new HashMap<String, APIObject>();
		cmds = new HashMap<String, APICommand>();
	}
	
	public void addAPI(String name, APIObject object) {
		object.loadAPI();
		apiObjects.put(name, object);
	}
	
	public APIObject getAPI(String name) {
		return apiObjects.get(name);
	}
		
	public APIPlugin getPlugin(String name) {
		return ((PluginAPI) apiObjects.get("plugin")).getPlugin(name);
	}
	
	public APIPlayer getPlayer(String name) {
		return ((PlayerAPI) apiObjects.get("player")).getPlayer(name);
	}
	
	public APIWorld getWorld(String name) {
		return ((WorldAPI) apiObjects.get("world")).getWorld(name);
	}
	
	public APICommand registerCommand(String label, String function, String perm) {
		APICommand cmd;
		if (perm != null) cmd = new APICommand(function, perm);
		else cmd = new APICommand(function);
		cmds.put(label, cmd);
		return cmd;
	}
	
	public void runCommand(CommandSender sender, String label, String[] args) {
		if (cmds.containsKey(label)) cmds.get(label).execute(sender, args);
	}
}
