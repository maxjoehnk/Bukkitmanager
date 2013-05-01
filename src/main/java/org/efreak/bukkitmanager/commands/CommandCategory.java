package org.efreak.bukkitmanager.commands;

public enum CommandCategory {

	/**
	 * 
	 * All commands, which are concerned with Bukkitmanager
	 * Layout: /bm (label) (args)
	 * 
	 */
	
	GENERAL,
	
	/**
	 * 	 
	 * All commands, which are concerned with Bukkit
	 * Layout: /bm bukkit (label) (args) or /bukkit (label) (args)
	 * 
	 */
	
	BUKKIT,
	
	/**
	 * 
	 * All commands, which are concerned with Plugins
	 * Layout: /bm plugin (label) (args) or /plugin (label) (args)
	 * 
	 */
	
	PLUGIN,
	
	/**
	 * 
	 * All commands, which are concerned with Players
	 * Layout: /bm player (label) (args) or /player (label) (args)
	 *  
	 */
	
	PLAYER,

	/**
	 * 
	 * All commands, which are concerned with the autobackup function
	 * Layout: /bm autobackup (label) (args) or /autobackup (label) (args)
	 *  
	 */
	
	AUTOBACKUP,
	
	/**
	 * 
	 * All commands, which are concerned with the autosave function
	 * Layout: /bm autosave (label) (args) or /autosave (label) (args)
	 *  
	 */
	
	AUTOSAVE,
	
	/**
	 * 
	 * All commands, which are concerned with the automessage function
	 * Layout: /bm automessage (label) (args) or /automessage (label) (args)
	 *  
	 */
	
	AUTOMESSAGE,
	
	/**
	 * 
	 * All commands which inform you about the server state
	 * Layout: /bm server (label) (args) or /server (label) (args)
	 * 
	 */
	
	SERVER,
	
	/**
	 * 
	 * All commands which manage the worlds
	 * Layout: /bm world (label) (args) or /world (label) (args)
	 * 
	 */
	
	WORLD
}
