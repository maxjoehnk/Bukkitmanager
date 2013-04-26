package org.efreak.bukkitmanager;


public class ThreadManager {
	
	private static IOManager io;
	public static AutosaveThread saveThread;
	public static AutobackupThread backupThread;
	public static AutomessageThread msgThread;
	public static Configuration config;
	
	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
	}
	
	public static boolean isRunning(ThreadType type) {
		switch(type) {
		case AUTOBACKUP:
			if (backupThread == null || !backupThread.isAlive() || !backupThread.getRun()) return false;
			else return true;
		case AUTOSAVE:
			if (saveThread == null || !saveThread.isAlive() || !saveThread.getRun()) return false;
			else return true;
		case AUTOMESSAGE:
			if (msgThread == null || !msgThread.isAlive() || !msgThread.getRun()) return false;
			else return true;
		default:
			return false;
		}
	}
	
	public static boolean startThread(ThreadType type) {
		switch(type) {
		case AUTOBACKUP:
			if (backupThread == null || !backupThread.isAlive()) {
				backupThread = new AutobackupThread();
				backupThread.start();
				io.sendConsole(io.translate("Thread.Start").replaceAll("%thread%", "AutobackupThread").replaceAll("%interval%", config.getString("Autobackup.Interval")));
			}
			return true;
		case AUTOSAVE:
			if (saveThread == null || !saveThread.isAlive()) {
				saveThread = new AutosaveThread();
				saveThread.start();
				io.sendConsole(io.translate("Thread.Start").replaceAll("%thread%", "AutosaveThread").replaceAll("%interval%", config.getString("Autosave.Interval")));
			}
			return true;
		case AUTOMESSAGE:
			if (msgThread == null || !msgThread.isAlive()) {
				msgThread = new AutomessageThread();
			msgThread.start();
				io.sendConsole(io.translate("Thread.Start").replaceAll("%thread%", "AutomessageThread").replaceAll("%interval%", config.getString("Automessage.Interval")));
			}
			return true;			
		default:
			return false;
		}
	}

	public static boolean stopThread(ThreadType type) {
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