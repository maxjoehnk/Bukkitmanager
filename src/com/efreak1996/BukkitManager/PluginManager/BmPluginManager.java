package com.efreak1996.BukkitManager.PluginManager;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;

public class BmPluginManager implements PluginManager {

	private PluginManager pm = Bukkit.getPluginManager();
	
	@Override
	public void addPermission(Permission arg0) {
		pm.addPermission(arg0);
	}

	@Override
	public void callEvent(Event arg0) {
		pm.callEvent(arg0);
	}

	@Override
	public void clearPlugins() {
		pm.clearPlugins();
	}

	@Override
	public void disablePlugin(Plugin arg0) {
		pm.disablePlugin(arg0);
	}

	@Override
	public void disablePlugins() {
		pm.disablePlugins();
	}

	@Override
	public void enablePlugin(Plugin arg0) {
		pm.enablePlugin(arg0);
	}

	public void enablePlugins(Plugin[] plugins) {
		for (int i = 0; i < plugins.length; i++) enablePlugin(plugins[i]);
	}
	
	@Override
	public Set<Permissible> getDefaultPermSubscriptions(boolean arg0) {
		return pm.getDefaultPermSubscriptions(arg0);
	}

	@Override
	public Set<Permission> getDefaultPermissions(boolean arg0) {
		return pm.getDefaultPermissions(arg0);
	}

	@Override
	public Permission getPermission(String arg0) {
		return pm.getPermission(arg0);
	}

	@Override
	public Set<Permissible> getPermissionSubscriptions(String arg0) {
		return pm.getPermissionSubscriptions(arg0);
	}

	@Override
	public Set<Permission> getPermissions() {
		return pm.getPermissions();
	}

	@Override
	public Plugin getPlugin(String arg0) {
		return pm.getPlugin(arg0);
	}

	@Override
	public Plugin[] getPlugins() {
		return pm.getPlugins();
	}

	@Override
	public boolean isPluginEnabled(String arg0) {
		return pm.isPluginEnabled(arg0);
	}

	@Override
	public boolean isPluginEnabled(Plugin arg0) {
		return pm.isPluginEnabled(arg0);
	}

	@Override
	public Plugin loadPlugin(File arg0) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
		return pm.loadPlugin(arg0);
	}

	@Override
	public Plugin[] loadPlugins(File arg0) {
		return pm.loadPlugins(arg0);
	}

	@Override
	public void recalculatePermissionDefaults(Permission arg0) {
		pm.recalculatePermissionDefaults(arg0);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4) {
		pm.registerEvent(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4, boolean arg5) {
		pm.registerEvent(arg0, arg1, arg2, arg3, arg4, arg5);		
	}

	@Override
	public void registerEvents(Listener arg0, Plugin arg1) {
		pm.registerEvents(arg0, arg1);
	}

	@Override
	public void registerInterface(Class<? extends PluginLoader> arg0) throws IllegalArgumentException {
		pm.registerInterface(arg0);
	}

	@Override
	public void removePermission(Permission arg0) {
		pm.removePermission(arg0);
	}

	@Override
	public void removePermission(String arg0) {
		pm.removePermission(arg0);		
	}

	@Override
	public void subscribeToDefaultPerms(boolean arg0, Permissible arg1) {
		pm.subscribeToDefaultPerms(arg0, arg1);
	}

	@Override
	public void subscribeToPermission(String arg0, Permissible arg1) {
		pm.subscribeToPermission(arg0, arg1);
	}

	@Override
	public void unsubscribeFromDefaultPerms(boolean arg0, Permissible arg1) {
		pm.unsubscribeFromDefaultPerms(arg0, arg1);
	}

	@Override
	public void unsubscribeFromPermission(String arg0, Permissible arg1) {
		pm.unsubscribeFromPermission(arg0, arg1);
	}

	@Override
	public boolean useTimings() {
		return pm.useTimings();
	}
	
	public void updatePluginDB() {
		
	}
	
	public void updatePlugin(Plugin plugin) {
		
	}
	
	public boolean checkPlugin(Plugin plugin) {
		return false;
	}
}
