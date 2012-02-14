package com.efreak1996.BukkitManager;

import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class BmAutosaveThread extends Thread {

	private boolean run = true;
	private BmConfiguration config = new BmConfiguration();
	public static Date lastSave = new Date();
	public static boolean saveInProgress = false;
	private static BmOutput out = new BmOutput();
	private Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitManager");

	public void setRun(boolean run) {
		this.run = run;
	}
	
	public boolean getRun() {
		return run;
	}

	private void savePlayers() {
		out.sendConsole("Saving players");
		Bukkit.getServer().savePlayers();
	}

	private int saveWorlds() {
		int i = 0;
		List<World> worlds = Bukkit.getServer().getWorlds();
		for (World world : worlds) {
			out.sendConsole(String.format("Saving world: %s", world.getName()));
			world.save();
			i++;
		}
		return i;
	}

	public void performSave() {
		if (saveInProgress) {
			out.sendConsoleWarning("Multiple concurrent saves attempted! Save interval is likely too short!");
			return;
		}
		if (Bukkit.getServer().getOnlinePlayers().length == 0) {
			out.sendConsole("Skipping save, no players online.");
			return;
		}
		saveInProgress = true;
		savePlayers();
		out.sendConsole("Saved Players");
		int saved = 0;
		saved += saveWorlds();
		out.sendConsole(String.format("Saved %d Worlds", saved));
		lastSave = new Date();
		saveInProgress = false;
	}
	
	public void run() {
		out.sendConsole("AutoSaveThread Started! Interval is " + config.Config.getInt("Autosave.Interval") + " seconds");
		while (run) {
			if(config.Config.getInt("Autosave.Interval") == 0) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {
					if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
				}
				continue;
			}

			for (int i = 0; i < config.Config.getInt("Autosave.Interval"); i++) {
				try {
					if (!run) {
						/*if (config.Config.getBoolean("General.Debug")) {
							log.info(String.format("[%s] Graceful quit of AutoSaveThread", plugin.getDescription().getName()));
						}*/
					return;
					}
					/*boolean warn = false;
					for (int w : config.Config.getIntegerList("Autosave.Warntimes")) {
						if (w != 0 && w + i == config.Config.getInt("Autosave.Interval")) {
							warn = true;
						}
					}

					if (warn) {
						int timeLeft = config.Config.getInt("Autosave.Interval") - i;
						out.sendBroadcast("Autosave in " + timeLeft);
						out.sendConsole("Autosave in " + timeLeft);
					}*/
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
				}
			}

			//switch (config.Config.getInt("Autosave.Task-Mode")) {
				//case 0:
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							performSave();
							lastSave = new Date();
						}
					});
					//break;
				/*case 1:
					Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
						public void run() {
							performSave();
							lastSave = new Date();
						}
					});
					break;
				default:
					out.sendConsoleError("Invalid Autosave Task-Mode!");
					break;
			}*/
		}
	}
}

