package org.efreak1996.Bukkitmanager.Swing.Local;

import java.util.List;

import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.Swing.BmSwing;


public class GuiUpdateThread extends Thread {

	private static boolean run = true;
	private static BmConfiguration config;
	
	public void initialize() {
		config = new BmConfiguration();
		this.start();
	}
	
	public void setRun(boolean state) {
		run = state;
	}
	
	public void run() {
		while (run) {
			while (BmSwing.getLocalMainGui().isVisible()) {
				List<GuiObject> guiObjects = BmSwing.getLocalGuiObjects();
				for (int i = 0; i < guiObjects.size(); i++) guiObjects.get(i).update();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
