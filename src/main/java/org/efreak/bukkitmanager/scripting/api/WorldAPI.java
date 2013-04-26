package org.efreak.bukkitmanager.scripting.api;

import java.util.HashMap;

import org.efreak.bukkitmanager.scripting.APIObject;

public class WorldAPI implements APIObject {

	HashMap<String, APIWorld> worlds;
	
	public APIWorld getWorld(String name) {
		return worlds.get(name);
	}

	@Override
	public boolean loadAPI() {
		worlds = new HashMap<String, APIWorld>();
		return true;
	}
}
