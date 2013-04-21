package org.efreak.bukkitmanager.util;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

public class SaveHelper {

	private static Plugin plugin;
	private static Configuration config;
	private static IOManager io;
	public static boolean saveInProgress = false;
	
	static {
		plugin = Bukkitmanager.getInstance();
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	

	public void performSave() {
		if (config.getString("Autosave.Taskmode").equalsIgnoreCase("sync")) plugin.getServer().getScheduler().runTask(plugin, new Runnable() {public void run() {save();}});
		else plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {public void run() {save();}});
	}
	
	private void save() {
		if (saveInProgress) {
			io.sendConsoleWarning(io.translate("Autosave.SaveInProgress"));
			return;
		}
		if (config.getBoolean("Autosave.NoOffline")) {
			if (Bukkit.getServer().getOnlinePlayers().length == 0) {
				io.sendConsole(io.translate("Autosave.NoPlayer"));
				return;
			}
		}
		saveInProgress = true;
		if (config.getBoolean("Autosave.Notification")) io.broadcast(io.translate("Autosave.Notification.Start"));
		savePlayers();
		saveWorlds();
		if (config.getBoolean("Autosave.Notification")) io.broadcast(io.translate("Autosave.Notification.Finish"));
		saveInProgress = false;
	}
	
	private void savePlayers() {
		io.sendConsole(io.translate("Autosave.Saving.Players"));
		Bukkit.getServer().savePlayers();
		io.sendConsole(io.translate("Autosave.Saved.Players"));
	}

	private void saveWorlds() {
		int i = 0;
		Iterator<World> itr = plugin.getServer().getWorlds().iterator();
		while (itr.hasNext()) {
		    World world = itr.next();
			io.sendConsole(io.translate("Autosave.Saving.World").replaceAll("%world%", world.getName()));
		    world.save();
		    i++;
		}
		io.sendConsole(io.translate("Autosave.Saved.Worlds").replaceAll("%worlds%", String.valueOf(i)));
	}
}
