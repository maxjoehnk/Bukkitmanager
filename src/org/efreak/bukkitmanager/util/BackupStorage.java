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
	
	public abstract boolean storeFile();
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setBackupFile(File file) {
		backupFile = file;
	}
	
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
