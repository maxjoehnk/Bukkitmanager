package org.efreak1996.Bukkitmanager;

public class BmAutomessageThread extends Thread {

	private static boolean run = true;
	private static BmConfiguration config;
	private static BmAutomessageReader msgReader;
	
	public boolean initialize() {
		config = new BmConfiguration();
		msgReader = new BmAutomessageReader();
		msgReader.initialize();
		return true;
	}
	
	public void setRun(boolean run) {
		this.run = run;
	}
	
	public boolean getRun() {
		return run;
	}
	
	public void run() {
		while (run) {
			if(config.getInt("Automessage.Interval") == 0) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
				continue;
			}

			for (int i = 0; i < config.getInt("Automessage.Interval"); i++) {
				try {
					if (!run) {
					return;
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					if (config.getDebug()) e.printStackTrace();
				}
			}
			msgReader.sendMsg();
		}
	}
}

