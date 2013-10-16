package org.efreak.bukkitmanager.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;

public class WorldManager {

	private static Configuration config;
	
	static {
		config = Bukkitmanager.getConfiguration();
	}
	
	public static World createWorld(WorldCreator creator) {
		World world = Bukkit.createWorld(creator);
		saveWorld(world);
		return world;
	}
		
	public static World loadWorld(String name) {
		if (config.contains("Worlds." + name)) {
			WorldCreator creator = new WorldCreator(name);
			creator.environment(Environment.valueOf(config.getString("Worlds." + name + ".Environment").toUpperCase()));
			creator.seed(config.getLong("Worlds." + name + ".Seed"));
			creator.type(WorldType.valueOf(config.getString("Worlds." + name + ".Type").toUpperCase()));
			return createWorld(creator);
		}else return null;
	}
	
	public static World loadWorld(WorldCreator creator) {
		if (config.contains("Worlds." + creator.name())) {
			creator.environment(Environment.valueOf(config.getString("Worlds." + creator.name() + ".Environment").toUpperCase()));
			creator.seed(config.getLong("Worlds." + creator.name() + ".Seed"));
			creator.type(WorldType.valueOf(config.getString("Worlds." + creator.name() + ".Type").toUpperCase()));
			return createWorld(creator);
		}else return null;
	}
	
	public static void unloadWorld(String name) {
		Bukkit.getServer().unloadWorld(name, true);
	}
	
	public static void unloadWorld(World world) {
		Bukkit.getServer().unloadWorld(world, true);
	}
	
	public static void deleteWorld(String name) {
		deleteWorld(Bukkit.getWorld(name));
	}
	
	public static void deleteWorld(World world) {
		File worldDir = world.getWorldFolder();
		config.remove("Worlds." + world.getName());
		unloadWorld(world);
		worldDir.deleteOnExit();
	}
	
	public static void loadWorlds() {
		if (config.contains("Worlds")) {
			for (String worldName : config.getConfig().getConfigurationSection("Worlds").getKeys(false)) {
				if (worldName != "Autoload") loadWorld(worldName);
			}
		}
	}
	
	public static void saveWorlds() {
		for (World world : Bukkit.getWorlds()) saveWorld(world);
	}
	
	public static void saveWorld(World world) {
		config.set("Worlds." + world.getName() + ".Environment", world.getEnvironment().toString());
		config.set("Worlds." + world.getName() + ".Seed", world.getSeed());
		config.set("Worlds." + world.getName() + ".Type", world.getWorldType().toString());
	}
	
}
