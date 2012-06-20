package org.efreak1996.Bukkitmanager.Swing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmPlugin;
import org.efreak1996.Bukkitmanager.Swing.Local.*;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmSwing {
	
	private static BmConfiguration config;
	private static BmIOManager io;
	private static Plugin plugin;
	private static org.efreak1996.Bukkitmanager.Swing.Remote.MultipleConnections.BmSwingServer sserverMultiple = null;
	private static org.efreak1996.Bukkitmanager.Swing.Remote.SingleConnections.BmSwingServer sserverSingle = null;
	private static LocalMainGui localGui = null;
	private static List<GuiObject> localGuiObjects;
	private static GuiUpdateThread localGuiUpdateThread;
	
	public void initialize() {
		config = new BmConfiguration();
		io = new BmIOManager();
		plugin = BmPlugin.getPlugin();
		if (config.getBoolean("Swing.Enabled")) {
			io.sendConsoleDev("Loading SwingGUI...");
			ConsoleOutputHandler outputHandler = new ConsoleOutputHandler();
			outputHandler.initialize();
			localGui = new LocalMainGui();
			localGui.initialize();
			localGuiObjects = new ArrayList<GuiObject>();
			localGuiObjects.add(localGui);
			localGuiUpdateThread = new GuiUpdateThread();
			localGuiUpdateThread.initialize();
		}
		if (config.getBoolean("Swing.Remote.Enabled")) {
			io.sendConsoleDev("Starting Swing Remote Server...");
			if (config.contains("Swing.Remote.Ports")) {
				sserverMultiple = new org.efreak1996.Bukkitmanager.Swing.Remote.MultipleConnections.BmSwingServer();
				sserverMultiple.start();
			}else {
				sserverSingle = new org.efreak1996.Bukkitmanager.Swing.Remote.SingleConnections.BmSwingServer();
				sserverSingle.start();
			}
		}
	}
		
	public void shutdown() {
		if (sserverMultiple != null) {
			sserverMultiple.listening = false;
			try {
				if (!sserverMultiple.sSocket.isClosed()) sserverMultiple.sSocket.close();
			} catch (IOException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		if (sserverSingle != null) {
			sserverSingle.listening = false;
			try {
				if (!sserverSingle.sSocket.isClosed()) sserverSingle.sSocket.close();
			} catch (IOException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		if (localGui != null) closeLocalGui();
	}
	
	public static LocalMainGui getLocalMainGui() {
		return localGui;
	}
	
	public static void openLocalGui() {
		localGui.open();
	}
	
	public static void closeLocalGui() {
		localGui.close();
	}
	
	public static List<GuiObject> getLocalGuiObjects() {
		return localGuiObjects;
	}
	
	public static void addLocalGuiObject(GuiObject object) {
		localGuiObjects.add(object);
	}
	
	public static void removeLocalGuiObject(int index) {
		localGuiObjects.remove(index);
	}
	
	public static void removeLocalGuiObject(GuiObject object) {
		localGuiObjects.remove(object);
	}
}
