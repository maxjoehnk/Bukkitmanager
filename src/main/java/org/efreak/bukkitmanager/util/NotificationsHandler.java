package org.efreak.bukkitmanager.util;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

import me.muizers.Notifications.Notification;
import me.muizers.Notifications.Notifications;

public class NotificationsHandler {

	private static Notifications notificationsPlugin;
	private static Configuration config;
	
	static {
		config = Bukkitmanager.getConfiguration();
		notificationsPlugin = (Notifications) PluginManager.getPlugin("Notifications");
	}
	
	public static void notify(String message) {
		notify(null, message);
	}
	
	public static void notify(String title, String message) {
		notify(null, message, null);
	}
	
	public static void notify(String title, String line1, String line2) {
		if (config.getBoolean("Notifications.Enabled")) {
			if (notificationsPlugin != null) {
				Notification notification;
				if (title != null) {
					if (line2 != null) notification = new Notification(title, line1, line2);
					else notification = new Notification(title, line1, "");
				}else {
					if (line2 != null) notification = new Notification(line1, line2);
					else notification = new Notification(line1, "");
				}
				notificationsPlugin.showNotification(notification);
			}
		}
	}
}
