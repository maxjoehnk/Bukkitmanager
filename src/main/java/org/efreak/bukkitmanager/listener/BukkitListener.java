package org.efreak.bukkitmanager.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Database;
import org.efreak.bukkitmanager.FakepluginsManager;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.util.ChatFilter;

public class BukkitListener implements Listener{
	
	public static IOManager io;
	public static Database db;
	public static Configuration config;
	
	static {
		db = Bukkitmanager.getDb();
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	@EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
		if (event.getPlayer().hasPermission("bm.unkickable")) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		BmPlayer player = new BmPlayer(event.getPlayer());
		player.onJoin();
		if (Permissions.has(event.getPlayer(), "bm.notification.updates")) {
			Plugin[] plugins = PluginManager.getPlugins();
			StringBuilder names = new StringBuilder();
			int updateCount = 0;
			for (int i = 0; i < plugins.length; i++) {
				try {
					if (!PluginManager.checkPlugin(plugins[i])) {
						if (names.length() > 0) names.append(", ");
						names.append(plugins[i]);
						updateCount++;
					}
				}catch(NullPointerException e) {
					io.sendError(event.getPlayer(), "Couldn't check for Plugin updates");
					if (config.getDebug()) e.printStackTrace();
				}
			}
			if (updateCount != 0) io.send(event.getPlayer(), io.translate("Command.Plugin.Update.UpdatesAvailable").replaceAll("%count%", String.valueOf(updateCount)).replaceAll("%names%", names.toString()));
		}
	}
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/plugins") && config.getBoolean("Fakepluginlist.Enabled")) {
			FakepluginsManager.execute(event.getPlayer());
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		BmPlayer player = new BmPlayer(event.getPlayer());
		player.onLeave();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chatFilter(AsyncPlayerChatEvent event) {
		if (config.getBoolean("Chatfilter.Enabled")) event.setMessage(ChatFilter.filter(event.getMessage()));
	}
}
