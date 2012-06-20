package org.efreak1996.Bukkitmanager.Swing.Local;

import java.util.List;

import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.Swing.Swing;


public class GuiUpdateThread extends Thread {

	private static boolean run = true;
	private static Configuration config;
	
	public void initialize() {
		config = new Configuration();
		this.start();
	}
	
	public void setRun(boolean state) {
		run = state;
	}
	
	public void run() {
		while (run) {
			while (Swing.getLocalMainGui().isVisible()) {
				List<GuiObject> guiObjects = Swing.getLocalGuiObjects();
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
