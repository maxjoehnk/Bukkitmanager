package com.efreak1996.BukkitManager.Util;

import com.efreak1996.BukkitManager.BmAutobackupThread;
import com.efreak1996.BukkitManager.BmAutomessageThread;
import com.efreak1996.BukkitManager.BmAutosaveThread;
import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmThreadType;

public class BmThreadManager {
	
	private static BmIOManager io;
	public static BmAutosaveThread saveThread;
	public static BmAutobackupThread backupThread;
	public static BmAutomessageThread msgThread;
	public static BmConfiguration config;
	
	public boolean initialize() {
		io = new BmIOManager();
		config = new BmConfiguration();
		return true;
	}
	
	public boolean startThread(BmThreadType type) {
		switch(type) {
		case AUTOSAVE:
			if (saveThread == null || !saveThread.isAlive()) {
				saveThread = new BmAutosaveThread();
				saveThread.start();
				io.sendConsole(io.translate("Thread.Start").replaceAll("%thread%", "AutosaveThread").replaceAll("%interval%", config.getString("Autosave.Interval")));
			}
			return true;
		case AUTOBACKUP:
			if (backupThread == null || !backupThread.isAlive()) {
				backupThread = new BmAutobackupThread();
				backupThread.start();
				io.sendConsole(io.translate("Thread.Start").replaceAll("%thread%", "AutobackupThread").replaceAll("%interval%", config.getString("Autobackup.Interval")));
			}
			return true;
		case AUTOMESSAGE:
			if (msgThread == null || !msgThread.isAlive()) {
				msgThread = new BmAutomessageThread();
				msgThread.start();
				io.sendConsole(io.translate("Thread.Start").replaceAll("%thread%", "AutomessageThread").replaceAll("%interval%", config.getString("Automessage.Interval")));
			}
			return true;			
		default:
			return false;
		}
	}

	public boolean stopThread(BmThreadType type) {
		switch(type) {
		case AUTOSAVE:
			if (saveThread == null) {
				return true;
			} else {
				if (saveThread.getRun()) {
					io.sendConsole(io.translate("Thread.Stopping").replaceAll("%thread%", "AutosaveThread"));
					saveThread.setRun(false);
					try {
						saveThread.join(5000);
						saveThread = null;
						io.sendConsole(io.translate("Thread.Stop").replaceAll("%thread%", "AutosaveThread"));
						return true;
					} catch (InterruptedException e) {
						io.sendConsoleWarning(io.translate("Thread.Error").replaceAll("%thread%", "AutosaveThread"));
						if (config.getDebug()) e.printStackTrace();
						return false;
					}
				}
			}
		case AUTOBACKUP:
			if (backupThread == null) {
				return true;
			} else {
				if (backupThread.getRun()) {
					io.sendConsole(io.translate("Thread.Stopping").replaceAll("%thread%", "AutobackupThread"));
					backupThread.setRun(false);
					try {
						backupThread.join(5000);
						backupThread = null;
						io.sendConsole(io.translate("Thread.Stop").replaceAll("%thread%", "AutobackupThread"));
						return true;
					} catch (InterruptedException e) {
						io.sendConsoleWarning(io.translate("Thread.Error").replaceAll("%thread%", "AutobackupThread"));
						if (config.getDebug()) e.printStackTrace();
						return false;
					}
				}
			}
		case AUTOMESSAGE:
			if (msgThread == null) {
				return true;
			} else {
				if (msgThread.getRun()) {
					io.sendConsole(io.translate("Thread.Stopping").replaceAll("%thread%", "AutomessageThread"));
					msgThread.setRun(false);
					try {
						msgThread.join(5000);
						msgThread = null;
						io.sendConsole(io.translate("Thread.Stop").replaceAll("%thread%", "AutomessageThread"));
						return true;
					} catch (InterruptedException e) {
						io.sendConsoleWarning(io.translate("Thread.Error").replaceAll("%thread%", "AutomessageThread"));
						if (config.getDebug()) e.printStackTrace();
						return false;
					}
				}
			}
		default:
			return false;
		}
	}
}