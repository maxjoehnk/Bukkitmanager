package com.efreak1996.BukkitManager;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmOutput;

public class BmAutobackupThread extends Thread {
	
	public static Date lastBackup = new Date();
	public static boolean backupInProgress = false;
	private boolean run = true;
	private BmConfiguration config = new BmConfiguration();
	private static BmOutput out = new BmOutput();
	private Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitManager");
	private BmAutosaveThread saveThread = new BmAutosaveThread();
	private File backupFolder = new File(plugin.getDataFolder().getParentFile().getParentFile() + File.separator + "backups");
	private File backupTempFolder = new File(plugin.getDataFolder().getParentFile().getParentFile() + File.separator + "backups" + File.separator + "temp");

	public void performBackup() {
		if (backupInProgress) {
			out.sendConsoleWarning("Multiple concurrent backup attempted! Backup interval is likely too short!");
			return;
		}
		if (Bukkit.getServer().getOnlinePlayers().length == 0) {
			out.sendConsole("Skipping backup, no players online.");
			return;
		}
		backupInProgress = true;
		saveThread.performSave();
		try {
			if (config.Config.getBoolean("Autobackup.Backup.Worlds")) backupWorlds();
			if (config.Config.getBoolean("Autobackup.Backup.Plugins")) backupPlugins();
			if (config.Config.getBoolean("Autobackup.Backup.craftbukkit.jar")) FileUtils.copyFile(new File(plugin.getDataFolder().getParentFile().getParentFile() + File.separator + "craftbukkit.jar"), new File(backupTempFolder + File.separator + "craftbukkit.jar"));
			compress();
		}catch (IOException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
		lastBackup = new Date();
		backupInProgress = false;
	}
	
	private void compress() {
		
	}

	private void backupWorlds() throws IOException {
		List<World> worlds = Bukkit.getServer().getWorlds();
		for (int i = 0; i < worlds.size(); i++) {
			FileUtils.copyDirectory(worlds.get(i).getWorldFolder(), new File(backupTempFolder + File.separator + worlds.get(i).getName()));
		}
	}

	private void backupPlugins() throws IOException {
		List<World> worlds = Bukkit.getServer().getWorlds();
		for (int i = 0; i < worlds.size(); i++) {
			FileUtils.copyDirectory(plugin.getDataFolder().getParentFile(), new File(backupTempFolder + File.separator + "plugins"));
		}
	}

	
	public void setRun(boolean run) {this.run = run;}
	
	public boolean getRun() {return run;}
	
	public void run() {
		out.sendConsole("AutobackupThread Started! Interval is " + config.Config.getInt("Autobackup.Interval") + " seconds");
		while (run) {
			if(config.Config.getInt("Autobackup.Interval") == 0) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {
					if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
				}
				continue;
			}

			for (int i = 0; i < config.Config.getInt("Autobackup.Interval"); i++) {
				try {
					if (!run) return;
					/*boolean warn = false;
					for (int w : config.Config.getIntegerList("Autobackup.Warntimes")) {
						if (w != 0 && w + i == config.Config.getInt("Autobackup.Interval")) {
							warn = true;
						}
					}

					if (warn) {
						int timeLeft = config.Config.getInt("Autobackup.Interval") - i;
						out.sendBroadcast("Autobackup in " + timeLeft);
						out.sendConsole("Autobackup in " + timeLeft);
					}*/
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
				}
			}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							performBackup();
						}
					});
		}
	}
}
