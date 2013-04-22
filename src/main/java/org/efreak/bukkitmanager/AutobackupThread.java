package org.efreak.bukkitmanager;

import org.efreak.bukkitmanager.util.BackupHelper;

public class AutobackupThread extends Thread {
	
	private boolean run = true;
	private static Configuration config;
	private static IOManager io;
	private static BackupHelper backupHelper;

	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
		backupHelper = new BackupHelper();
	}
	
	public void setRun(boolean run) {this.run = run;}
	
	public boolean getRun() {return run;}
	
	public void run() {
		setName("Bukkitmanager Autobackup");
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
			backupHelper.performBackup();
		}
	}
}
