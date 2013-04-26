package org.efreak.bukkitmanager;

import org.efreak.bukkitmanager.util.SaveHelper;

public class AutosaveThread extends Thread {

	private boolean run = true;
	private static Configuration config;
	private static SaveHelper saveHelper;
	private static IOManager io;

	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
		saveHelper = new SaveHelper();
	}
	
	public void setRun(boolean run) {this.run = run;}
	
	public boolean getRun() {return run;}
	
	public void run() {
		setName("Bukkitmanager Autosave");
		while (run) {
			if(config.getInt("Autosave.Interval") == 0) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
				continue;
			}
			for (int i = 0; i < config.getInt("Autosave.Interval"); i++) {
				try {
					if (!run) return;
					boolean warn = false;
					for (int w : config.getIntegerList("Autosave.Warntimes")) {
						if (w != 0 && w + i == config.getInt("Autosave.Interval")) warn = true;
					}
					if (warn) io.broadcast(io.translate("Autosave.Warn").replaceAll("%timeleft%", String.valueOf(config.getInt("Autosave.Interval") - i)));
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
			}
			saveHelper.performSave();
		}
	}
}

