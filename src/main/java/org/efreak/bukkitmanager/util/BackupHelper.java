package org.efreak.bukkitmanager.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.ThreadManager;
import org.efreak.bukkitmanager.ThreadType;

public class BackupHelper {

	private static Plugin plugin;
	private static Configuration config;
	private static IOManager io;
	private static boolean inProgress = false;
	private static SaveHelper saveHelper;
	private static List<BackupStorage> backupStorage;
	
	static {
		plugin = Bukkitmanager.getInstance();
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
		backupStorage = new ArrayList<BackupStorage>();
		saveHelper = new SaveHelper();
	}
	
	public void performBackup() {
		if (config.getString("Autobackup.Taskmode").equalsIgnoreCase("sync")) plugin.getServer().getScheduler().runTask(plugin, new Runnable() {public void run() {backup();}});
		else plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {public void run() {backup();}});
	}
	
	private void backup() {
		if (inProgress) {
			io.sendConsoleWarning(io.translate("Autobackup.BackupInProgress"));
			return;
		}
		if (config.getBoolean("Autobackup.NoOffline") && plugin.getServer().getOnlinePlayers().length == 0) {
			io.sendConsole(io.translate("Autobackup.NoPlayer"));
			return;
		}
		inProgress = true;
		if (config.getBoolean("Notifications.Autobackup.Started")) NotificationsHandler.notify("Bukkitmanager", "Autobackup Started", "");
		if (config.getBoolean("Autobackup.Notification")) io.broadcast(io.translate("Autobackup.Notification.Start"));
		if (!ThreadManager.isRunning(ThreadType.AUTOSAVE)) saveHelper.performSave();
		try {
			FileUtils.cleanDirectory(FileHelper.getTempBackupDir());
			List<World> worlds = plugin.getServer().getWorlds();
			for (int i = 0; i < worlds.size(); i++) if (config.getBoolean("Autobackup.Backup.Worlds." + worlds.get(i).getName())) copyWorld(worlds.get(i));
			if (config.getBoolean("Autobackup.Backup.Plugins")) copyPlugins();
			if (config.getBoolean("Autobackup.Backup.craftbukkit")) copyJar();
			File backupFile = new File(FileHelper.getBackupDir() + File.separator + new Date().toGMTString().replaceAll(" ", "_").replaceAll(":", "-").replaceAll("_GMT", "") + ".zip");
			ArchiveManager.compressZip(FileHelper.getTempBackupDir(), backupFile);
			for (int i = 0; i < backupStorage.size(); i++) {
				if (backupStorage.get(i).isEnabled()) {
					backupStorage.get(i).setBackupFile(backupFile);
					backupStorage.get(i).start();
				}
			}
			if (config.getBoolean("Autobackup.PostExecution.Enabled")) Runtime.getRuntime().exec(new File(FileHelper.getPluginDir(), config.getString("Autobackup.PostExecution.File")).toString());
			if (config.getInt("Autobackup.KeepBackups") == 0) removeOld(0);
			if (config.getBoolean("Autobackup.Notification")) io.broadcast(io.translate("Autobackup.Notification.Finish"));		
			if (config.getBoolean("Notifications.Autobackup.Finished")) NotificationsHandler.notify("Bukkitmanager", "Autobackup Finished", "Size: " + Downloader.readableSize(backupFile.length()));
		}catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				FileUtils.cleanDirectory(FileHelper.getTempBackupDir());
			}catch (IOException e) {
				io.sendConsoleWarning("Couldn't clean Temporary Backup Folder!");
				if (config.getDebug()) e.printStackTrace();
			}
		}
		inProgress = false;
	}
	
	private void copyWorld(World world) {
		try {
			FileUtils.copyDirectory(world.getWorldFolder(), new File(FileHelper.getTempBackupDir() + File.separator + world.getName()));
		} catch (IOException e) {
			io.sendConsoleError("Error copying World " + world.getName());
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	private void copyPlugins() {
		try {
			FileUtils.copyDirectory(plugin.getDataFolder().getParentFile(), new File(FileHelper.getTempBackupDir() + File.separator + "plugins"));
		} catch (IOException e) {
			io.sendConsoleError("Error copying Plugins");
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	private void copyJar() {
		try {
			if (config.getBoolean("Autobackup.RenameJar")) FileUtils.copyFile(FileHelper.getBukkitFile(), new File(FileHelper.getTempBackupDir(), File.separator + "craftbukkit#" + Bukkit.getVersion().split("-b")[1].split("jnks")[0] + ".jar"));
			else FileUtils.copyFileToDirectory(FileHelper.getBukkitFile(), FileHelper.getTempBackupDir());
		}catch (IOException e) {
			io.sendConsoleError("Error copying Server Jar File");
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	private void removeOld(int amount) {
		File[] files = Arrays.asList(FileHelper.getBackupDir().listFiles()).toArray(new File[0]);
		if (files.length - amount < 1) return;
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File file1, File file2) {
				return Long.valueOf(file1.lastModified()).compareTo(file2.lastModified());
			}
		});
		 List<File> oldFiles;
		if (amount != 0) oldFiles = Arrays.asList(files).subList(0, files.length - 1 - amount);
		else oldFiles = Arrays.asList(files);
		for (File file : oldFiles) file.delete();
	}
	
	public static void registerBackupStorage(BackupStorage storage) {
		backupStorage.add(storage);
	}	
}