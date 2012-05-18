package com.efreak1996.BukkitManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;

public class BmAutobackupThread extends Thread {
	
	public static Date lastBackup = new Date();
	public static boolean backupInProgress = false;
	private static boolean run = true;
	private static BmConfiguration config;
	private static BmIOManager io;
	private static Plugin plugin;
	private static BmAutosaveThread saveThread;
	private static File bukkitFolder;
	private static File backupFolder;
	private static File backupTempFolder;
	private static File[] tempFiles = null;
	private static ZipOutputStream zipOut = null;

	public void initialize() {
		config = new BmConfiguration();
		io = new BmIOManager();
		plugin = BmPlugin.getPlugin();
		saveThread = new BmAutosaveThread();
		bukkitFolder = plugin.getDataFolder().getParentFile().getAbsoluteFile().getParentFile();
		backupFolder = new File(bukkitFolder + File.separator + "backups");
		backupTempFolder = new File(backupFolder + File.separator + "temp");
	}
	
	public void performBackup() {
		if (backupInProgress) {
			io.sendConsoleWarning(io.translate("Autobackup.BackupInProgress"));
			return;
		}
		if (config.getBoolean("Autobackup.NoOffline")) {
			if (Bukkit.getServer().getOnlinePlayers().length == 0) {
				io.sendConsole(io.translate("Autobackup.NoPlayer"));
				return;
			}
		}
		if (config.getBoolean("Autobackup.Notification")) io.broadcast(io.translate("Autobackup.Notification.Start"));
		backupInProgress = true;
		saveThread.performSave();
		try {
			FileUtils.cleanDirectory(backupTempFolder);
			backupWorlds();
			if (config.getBoolean("Autobackup.Backup.Plugins")) backupPlugins();
			if (config.getBoolean("Autobackup.Backup.craftbukkit")) backupBukkit();
			compress();
		}catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				FileUtils.cleanDirectory(backupTempFolder);
			} catch (IOException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		lastBackup = new Date();
		backupInProgress = false;
		if (config.getBoolean("Autobackup.Notification")) io.broadcast(io.translate("Autobackup.Notification.Finish"));
	}	

	private void compress() {
		tempFiles = backupTempFolder.listFiles();
		if (tempFiles != null) {
			try {
				zipOut = new ZipOutputStream(new FileOutputStream(backupFolder + File.separator + new Date().toGMTString().replaceAll(" ", "_").replaceAll(":", "-").replaceAll("_GMT", "") + ".zip"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < tempFiles.length; i++) {
				if (!(tempFiles[i].isDirectory())) compressBukkit(i);
				else if (tempFiles[i].isDirectory() && tempFiles[i].getName().equals("plugins")) compressPlugins(i);
				else compressWorlds(i);
			}
			try {
				if(zipOut!=null) zipOut.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void compressPlugins(int i) {
		File[] pluginFiles = tempFiles[i].getAbsoluteFile().listFiles();
		io.sendConsole(io.translate("Autobackup.Compressing.Plugins"));
		for (int i2 = 0; i2 < pluginFiles.length; i2++) {
			if (pluginFiles[i2].isFile()) {
				BufferedInputStream bis = null;
				try {
					bis = new BufferedInputStream(new FileInputStream(pluginFiles[i2].getAbsolutePath()));
					int avail = bis.available();
					byte[] buffer = new byte[avail] ;
					if (avail>0) {
						bis.read(buffer, 0, avail) ;
					}
					ZipEntry ze = new ZipEntry(tempFiles[i].getName() + File.separator + pluginFiles[i2].getName());
					zipOut.putNextEntry(ze);
					zipOut.write(buffer, 0, buffer.length);
					zipOut.closeEntry();								
				}catch(IOException e) {
					if (config.getDebug()) e.printStackTrace();
				}finally {
					try {
						if(bis!=null) bis.close();
					}catch(Exception e) {
						if (config.getDebug()) e.printStackTrace();
					}
				}
			}else if(pluginFiles[i2].isDirectory()) {
				File[] pluginFiles2 = pluginFiles[i2].listFiles();
				for (int i3 = 0; i3 < pluginFiles2.length; i3++) {
					if (pluginFiles2[i3].isFile()) {
						BufferedInputStream bis = null;
						try {
							bis = new BufferedInputStream(new FileInputStream(pluginFiles2[i3].getAbsolutePath()));
							int avail = bis.available();
							byte[] buffer = new byte[avail] ;
							if (avail>0) bis.read(buffer, 0, avail) ;
							ZipEntry ze = new ZipEntry(tempFiles[i].getName() + File.separator + pluginFiles[i2].getName() + File.separator + pluginFiles2[i3].getName());
							zipOut.putNextEntry(ze);
							zipOut.write(buffer, 0, buffer.length);
							zipOut.closeEntry();								
						}catch(IOException e) {
							if (config.getDebug()) e.printStackTrace();
						}finally {
							try {
								if(bis!=null) bis.close();
								}catch(Exception e) {
									if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if(pluginFiles2[i3].isDirectory()) {
						File[] pluginFiles3 = pluginFiles2[i3].listFiles();
						for (int i4 = 0; i4 < pluginFiles3.length; i4++) {
								if (pluginFiles3[i4].isFile()) {
								BufferedInputStream bis = null;
								try {
									bis = new BufferedInputStream(new FileInputStream(pluginFiles3[i4].getAbsolutePath()));
									int avail = bis.available();
									byte[] buffer = new byte[avail] ;
									if (avail>0) {
										bis.read(buffer, 0, avail) ;
									}
									ZipEntry ze = new ZipEntry(tempFiles[i].getName() + File.separator + pluginFiles[i2].getName() + File.separator + pluginFiles2[i3].getName() + File.separator + pluginFiles3[i4].getName());
									zipOut.putNextEntry(ze);
									zipOut.write(buffer, 0, buffer.length);
									zipOut.closeEntry();								
								}catch(IOException e) {
									if (config.getDebug()) e.printStackTrace();
								}finally {
									try {
										if(bis!=null) bis.close();
									}catch(Exception e) {
										if (config.getDebug()) e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
		io.sendConsole(ChatColor.GREEN + "Done!");
	}
	private void compressWorlds(int i) {
			File[] worldFiles = tempFiles[i].getAbsoluteFile().listFiles();
			io.sendConsole(io.translate("Autobackup.Compressing.World").replaceAll("%world%", tempFiles[i].getName()));
			for (int i2 = 0; i2 < worldFiles.length; i2++) {
				if (worldFiles[i2].isFile()) {
					BufferedInputStream bis = null;
					try {
						bis = new BufferedInputStream(new FileInputStream(worldFiles[i2].getAbsolutePath()));
						int avail = bis.available();
						byte[] buffer = new byte[avail] ;
						if (avail>0) {
							bis.read(buffer, 0, avail) ;
						}
						ZipEntry ze = new ZipEntry(tempFiles[i].getName() + File.separator + worldFiles[i2].getName());
						zipOut.putNextEntry(ze);
						zipOut.write(buffer, 0, buffer.length);
						zipOut.closeEntry();								
					}catch(IOException e) {
						if (config.getDebug()) e.printStackTrace();
					}finally {
						try {
							if(bis!=null) bis.close();
						}catch(Exception e) {
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (worldFiles[i2].isDirectory()) {
					File[] worldFiles2 = worldFiles[i2].getAbsoluteFile().listFiles();
					for (int i3 = 0; i3 < worldFiles2.length; i3++) {
						if (worldFiles2[i3].isFile()) {
							BufferedInputStream bis2 = null;
							try {
								bis2 = new BufferedInputStream(new FileInputStream(worldFiles2[i3].getAbsolutePath()));
								int avail2 = bis2.available();
								byte[] buffer2 = new byte[avail2] ;
								if (avail2>0) bis2.read(buffer2, 0, avail2);
								ZipEntry ze2 = new ZipEntry(tempFiles[i].getName() + File.separator + worldFiles[i2].getName() + File.separator + worldFiles2[i3].getName());
								zipOut.putNextEntry(ze2);
								zipOut.write(buffer2, 0, buffer2.length);
								zipOut.closeEntry();								
							}catch(IOException e) {
								if (config.getDebug()) e.printStackTrace();
							}finally {
								try {
									if(bis2!=null) bis2.close();
								}catch(Exception e) {
									if (config.getDebug()) e.printStackTrace();
								}
							}
						}
					}
				}
			}
			io.sendConsole(ChatColor.GREEN + "Done!");
	}
	private void compressBukkit(int i) {
		File jar = tempFiles[i];
		BufferedInputStream bis = null;
		try {
			io.sendConsole(io.translate("Autobackup.Compressing.Bukkit").replaceAll("%jar%", jar.getName()));
			bis = new BufferedInputStream(new FileInputStream(jar));
			int avail = bis.available();
			byte[] buffer = new byte[avail] ;
			if (avail>0) bis.read(buffer, 0, avail) ;
			ZipEntry ze = new ZipEntry(jar.getName());
			zipOut.putNextEntry(ze);
			zipOut.write(buffer, 0, buffer.length);
			zipOut.closeEntry();	
			io.sendConsole(ChatColor.GREEN + "Done!");
		}catch(IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				if(bis!=null) bis.close();
			}catch(Exception e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
	}
	
	private void backupWorlds() throws IOException {
		List<World> worlds = plugin.getServer().getWorlds();
		for (int i = 0; i < worlds.size(); i++) {
			if (config.getBoolean("Autobackup.Backup.Worlds." + worlds.get(i).getName())) FileUtils.copyDirectory(worlds.get(i).getWorldFolder(), new File(backupTempFolder + File.separator + worlds.get(i).getName()));
		}
	}
	private void backupPlugins() throws IOException {
		FileUtils.copyDirectory(plugin.getDataFolder().getParentFile(), new File(backupTempFolder + File.separator + "plugins"));
	}
	private void backupBukkit() throws IOException {
		if (new File(bukkitFolder + File.separator + "craftbukkit.jar").exists()) FileUtils.copyFileToDirectory(new File(bukkitFolder + File.separator + "craftbukkit.jar"), backupTempFolder);
		else if (config.contains("Autobackup.BukkitFile")) {
			if (new File(bukkitFolder + File.separator + config.getString("Autobackup.BukkitFile")).exists()) FileUtils.copyFileToDirectory(new File(bukkitFolder + File.separator + config.getString("Autobackup.BukkitFile")), backupTempFolder);
			else io.sendConsoleError(io.translate("Autobackup.JarDoesntExists").replaceAll("%file%", config.getString("Autobackup.BukkitFile")));
		}
		else {
			File[] bukkitFolderFiles = bukkitFolder.listFiles(new jarFilter());
			if (bukkitFolderFiles.length > 1) io.sendConsoleError(io.translate("Autobackup.MultipleJars"));
			else if (bukkitFolderFiles.length == 0) io.sendConsoleError(io.translate("Autobackup.NoJar"));
			else FileUtils.copyFileToDirectory(bukkitFolderFiles[0], backupTempFolder);
		}
	}

	public void setRun(boolean run) {this.run = run;}
	
	public boolean getRun() {return run;}
	
	public void run() {
		while (run) {
			if(config.getInt("Autobackup.Interval") == 0) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
				continue;
			}

			for (int i = 0; i < config.getInt("Autobackup.Interval"); i++) {
				try {
					if (!run) return;
					boolean warn = false;
					for (int w : config.getIntegerList("Autobackup.Warntimes")) {
						if (w != 0 && w + i == config.getInt("Autobackup.Interval")) warn = true;
					}
					if (warn) io.broadcast(io.translate("Autobackup.Warn").replaceAll("%timeleft%", String.valueOf(config.getInt("Autobackup.Interval") - i)));
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
			}
			if (config.getBackupMode().equalsIgnoreCase("SYNC")) {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						performBackup();
					}
				});
			}else {
				plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					public void run() {
						performBackup();
					}
				});
			}
		}
	}
}

class jarFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".jar"));
    }
}