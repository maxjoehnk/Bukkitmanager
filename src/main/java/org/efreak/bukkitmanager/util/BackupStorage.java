package org.efreak.bukkitmanager.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

public abstract class BackupStorage extends Thread {

	protected static IOManager io;
	protected static Configuration config;
	protected boolean enabled = true;
	protected File backupFile;
	protected File tempDir;

	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
	}
	
	/**
	 * 
	 * Stores the backup file to the new Storage
	 * 
	 * @return success of the transfer
	 */
	public abstract boolean storeFile();
	
	/**
	 * 
	 * @return Whether this storage is enabled
	 * 
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * 
	 * Toggle the enabled state of this Storage
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * 
	 * Set the original Backup File
	 * 
	 * @param file the original Backup File
	 * 
	 */
	public void setBackupFile(File file) {
		backupFile = file;
	}
	
	/**
	 * 
	 * This is executed after every backup.
	 * 
	 * It copies the original Backup to a temporary Folder from where it can be transfered.
	 * After the transfer the temporary File is deleted
	 * 
	 */
	@Override
	public void run() {
		if (!tempDir.exists()) tempDir.mkdir();
		if (!tempDir.isDirectory()) {
			tempDir.delete();
			tempDir.mkdir();
		}
		try {
			FileUtils.copyFileToDirectory(backupFile, tempDir);
			backupFile = new File(tempDir, backupFile.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		storeFile();
		backupFile.delete();
	}
}
