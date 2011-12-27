package me.efreak1996.BukkitManager;

import org.bukkit.plugin.Plugin;

public class BmShutdown {
    public static void Shutdown(Plugin plugin)
    {
    	out.sendConsole("disabling...");
    	BmDatabase.shutdown();
    	out.sendConsole("disabled!");
    }
	public static BmOutput out = new BmOutput();
}
