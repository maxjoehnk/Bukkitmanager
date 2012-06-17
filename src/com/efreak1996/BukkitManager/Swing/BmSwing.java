package com.efreak1996.BukkitManager.Swing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;
import com.efreak1996.BukkitManager.BmPlugin;
import com.efreak1996.BukkitManager.Swing.Local.*;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmSwing {
	
	private static BmConfiguration config;
	private static BmIOManager io;
	private static Plugin plugin;
	private static com.efreak1996.BukkitManager.Swing.Remote.MultipleConnections.BmSwingServer sserverMultiple = null;
	private static com.efreak1996.BukkitManager.Swing.Remote.SingleConnections.BmSwingServer sserverSingle = null;
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
				sserverMultiple = new com.efreak1996.BukkitManager.Swing.Remote.MultipleConnections.BmSwingServer();
				sserverMultiple.start();
			}else {
				sserverSingle = new com.efreak1996.BukkitManager.Swing.Remote.SingleConnections.BmSwingServer();
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
