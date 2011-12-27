package me.efreak1996.BukkitManager;

import org.bukkit.plugin.java.JavaPlugin;

public class Bukkitmanager extends JavaPlugin {
	
	@Override
	public void onDisable() {
        BmShutdown.Shutdown(this);
	}

	@Override
	public void onLoad() {
		
	}
	
	@Override
	public void onEnable() {
			BmInitialize.Initialize();
	}
}
