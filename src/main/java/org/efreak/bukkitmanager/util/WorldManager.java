package org.efreak.bukkitmanager.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldManager {

	public static World createWorld(WorldCreator creator) {
		return Bukkit.createWorld(creator);
	}
	
	public static World loadWorld(File worldDir) {
		return null;
	}
	
	public static void unloadWorld(String name) {
		
	}
	
	public static void unloadWorld(World world) {
		
	}
	
	public static void deleteWorld(String name) {
		
	}
	
	public static void deleteWorld(World world) {
		
	}
	
}
