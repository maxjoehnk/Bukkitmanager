package com.efreak1996.BukkitManager.PluginManager;

import java.io.File;
import java.util.Set;

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

import com.efreak1996.BukkitManager.BmPlugin;

public class BmPluginManager implements PluginManager {
	
	@Override
	public void addPermission(Permission arg0) {
		BmPlugin.getPlugin().getServer().getPluginManager().addPermission(arg0);
	}

	@Override
	public void callEvent(Event arg0) {
		BmPlugin.getPlugin().getServer().getPluginManager().callEvent(arg0);
	}

	@Override
	public void clearPlugins() {
		BmPlugin.getPlugin().getServer().getPluginManager().clearPlugins();
	}

	@Override
	public void disablePlugin(Plugin arg0) {
		BmPlugin.getPlugin().getServer().getPluginManager().disablePlugin(arg0);
	}

	@Override
	public void disablePlugins() {
		BmPlugin.getPlugin().getServer().getPluginManager().disablePlugins();
	}

	@Override
	public void enablePlugin(Plugin arg0) {
		BmPlugin.getPlugin().getServer().getPluginManager().enablePlugin(arg0);
	}

	public void enablePlugins(Plugin[] plugins) {
		for (int i = 0; i < plugins.length; i++) enablePlugin(plugins[i]);
	}
	
	@Override
	public Set<Permissible> getDefaultPermSubscriptions(boolean arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().getDefaultPermSubscriptions(arg0);
	}

	@Override
	public Set<Permission> getDefaultPermissions(boolean arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().getDefaultPermissions(arg0);
	}

	@Override
	public Permission getPermission(String arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().getPermission(arg0);
	}

	@Override
	public Set<Permissible> getPermissionSubscriptions(String arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().getPermissionSubscriptions(arg0);
	}

	@Override
	public Set<Permission> getPermissions() {
		return BmPlugin.getPlugin().getServer().getPluginManager().getPermissions();
	}

	@Override
	public Plugin getPlugin(String arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().getPlugin(arg0);
	}

	@Override
	public Plugin[] getPlugins() {
		return BmPlugin.getPlugin().getServer().getPluginManager().getPlugins();
	}

	@Override
	public boolean isPluginEnabled(String arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().isPluginEnabled(arg0);
	}

	@Override
	public boolean isPluginEnabled(Plugin arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().isPluginEnabled(arg0);
	}

	@Override
	public Plugin loadPlugin(File arg0) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
		return BmPlugin.getPlugin().getServer().getPluginManager().loadPlugin(arg0);
	}

	@Override
	public Plugin[] loadPlugins(File arg0) {
		return BmPlugin.getPlugin().getServer().getPluginManager().loadPlugins(arg0);
	}

	@Override
	public void recalculatePermissionDefaults(Permission arg0) {
		BmPlugin.getPlugin().getServer().getPluginManager().recalculatePermissionDefaults(arg0);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4) {
		BmPlugin.getPlugin().getServer().getPluginManager().registerEvent(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4, boolean arg5) {
		BmPlugin.getPlugin().getServer().getPluginManager().registerEvent(arg0, arg1, arg2, arg3, arg4, arg5);		
	}

	@Override
	public void registerEvents(Listener arg0, Plugin arg1) {
		BmPlugin.getPlugin().getServer().getPluginManager().registerEvents(arg0, arg1);
	}

	@Override
	public void registerInterface(Class<? extends PluginLoader> arg0) throws IllegalArgumentException {
		BmPlugin.getPlugin().getServer().getPluginManager().registerInterface(arg0);
	}

	@Override
	public void removePermission(Permission arg0) {
		BmPlugin.getPlugin().getServer().getPluginManager().removePermission(arg0);
	}

	@Override
	public void removePermission(String arg0) {
		BmPlugin.getPlugin().getServer().getPluginManager().removePermission(arg0);		
	}

	@Override
	public void subscribeToDefaultPerms(boolean arg0, Permissible arg1) {
		BmPlugin.getPlugin().getServer().getPluginManager().subscribeToDefaultPerms(arg0, arg1);
	}

	@Override
	public void subscribeToPermission(String arg0, Permissible arg1) {
		BmPlugin.getPlugin().getServer().getPluginManager().subscribeToPermission(arg0, arg1);
	}

	@Override
	public void unsubscribeFromDefaultPerms(boolean arg0, Permissible arg1) {
		BmPlugin.getPlugin().getServer().getPluginManager().unsubscribeFromDefaultPerms(arg0, arg1);
	}

	@Override
	public void unsubscribeFromPermission(String arg0, Permissible arg1) {
		BmPlugin.getPlugin().getServer().getPluginManager().unsubscribeFromPermission(arg0, arg1);
	}

	@Override
	public boolean useTimings() {
		return BmPlugin.getPlugin().getServer().getPluginManager().useTimings();
	}
	
	public void updatePluginDB() {
		
	}
	
	public void updatePlugin(Plugin plugin) {
		
	}
	
	public boolean checkPlugin(Plugin plugin) {
		return false;
	}
}
