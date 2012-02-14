package com.efreak1996.BukkitManager;

public class BmFunctions {
	public static boolean startThread(BmThreadType type) {
		switch(type) {
		case AUTOSAVE:
			if (saveThread == null || !saveThread.isAlive()) {
				saveThread = new BmAutosaveThread();
				saveThread.start();
			}
			return true;
		case AUTOBACKUP:
			
		case AUTOMESSAGE:
			if (msgThread == null || !msgThread.isAlive()) {
				msgThread = new BmAutomessageThread();
				msgThread.start();
			}
			return true;			
		default:
			return false;
		}
	}

	public static boolean stopThread(BmThreadType type) {
		switch(type) {
		case AUTOSAVE:
			if (saveThread == null) {
				return true;
			} else {
				if (saveThread.getRun()) {
					out.sendConsole("Stopping Autosave Thread!");
					saveThread.setRun(false);
					try {
						saveThread.join(5000);
						saveThread = null;
						out.sendConsole("Done!");
						return true;
					} catch (InterruptedException e) {
						out.sendConsoleWarning("Could not stop AutoSaveThread");
						return false;
					}
				}
			}
		case AUTOBACKUP:

		case AUTOMESSAGE:
			if (msgThread == null) {
				return true;
			} else {
				if (msgThread.getRun()) {
					out.sendConsole("Stopping Automessage Thread!");
					msgThread.setRun(false);
					try {
						msgThread.join(5000);
						msgThread = null;
						out.sendConsole("Done!");
						return true;
					} catch (InterruptedException e) {
						out.sendConsoleWarning("Could not stop AutomessageThread");
						return false;
					}
				}
			}
		default:
			return false;
		}
	}
	private static BmOutput out = new BmOutput();
	public static BmAutosaveThread saveThread;
	public static BmAutomessageThread msgThread;
}