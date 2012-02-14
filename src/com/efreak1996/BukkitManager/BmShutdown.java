package com.efreak1996.BukkitManager;

import org.bukkit.ChatColor;

/**
 * 
 * The Stuff which should be done at servershutdown
 * 
 * @author efreak1996
 *
 * @version Alpha 1.3
 * 
 * @see Bukkitmanager#onDisable()
 *
 */

public class BmShutdown {
	
    public BmShutdown() {
    	//BmDatabase.shutdown();
    	BmFunctions.stopThread(BmThreadType.AUTOSAVE);
    	BmFunctions.stopThread(BmThreadType.AUTOMESSAGE);
    	out.sendConsole(ChatColor.GREEN + "Done!");
    }
    
	public static BmOutput out = new BmOutput();
}
