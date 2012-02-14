package com.efreak1996.BukkitManager.Commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

@SuppressWarnings("deprecation")
public class BmPluginConfig {
	
	public static void shutdown() {}
	
	public static void initialize() {}
	
	public static void player(Player p, String[] args)
	{
		if (args.length == 4) {
			if (Bukkit.getServer().getPluginManager().getPlugin(args[2]) == null) {
				p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.RED + " This Plugin does not exists.");
				p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.RED + " Available Plugins: " + getPluginList());
				return;
			}else {
				Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
				File pluginFolder = plugin.getDataFolder();
				if (new File(pluginFolder + File.separator + "config.yml").exists()) {
					FileConfiguration pluginConfig = plugin.getConfig();//new Configuration(new File(pluginFolder + File.separator + "config.yml"));
					
					if (pluginConfig.get(args[3]) != null) {
						p.sendMessage(ChatColor.DARK_RED + "[BukkitManager] " + ChatColor.WHITE + "The Entry " + args[3] + " has the value: " + pluginConfig.get(args[3]));
					}else p.sendMessage(ChatColor.RED + "[BukkitManager]" + ChatColor.RED + " This Entry isn't available!");
				}else p.sendMessage(ChatColor.RED + "[BukkitManager]" + ChatColor.RED + " The plugin Folder contains no config.yml!");
			}
		}else if (args.length == 5) {
			if (Bukkit.getServer().getPluginManager().getPlugin(args[2]) == null) {
				p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.RED + " This Plugin does not exists.");
				p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.RED + " Available Plugins: " + getPluginList());
				return;
			}else {
				Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
				File pluginFolder = plugin.getDataFolder();
				if (new File(pluginFolder + File.separator + "config.yml").exists()) {
					Configuration pluginConfig = new Configuration(new File(pluginFolder + File.separator + "config.yml"));
					if (pluginConfig.getProperty(args[3]) != null) {
						String old = pluginConfig.getProperty(args[3]).toString();
						pluginConfig.setProperty(args[3], args[4]);
						p.sendMessage(ChatColor.DARK_RED + "[BukkitManager] " + ChatColor.WHITE + "The Entry " + args[3] + " was set from: " + old + " to: " + pluginConfig.getProperty(args[3]));
					}else p.sendMessage(ChatColor.RED + "[BukkitManager]" + ChatColor.RED + " This Entry isn't available!");
				}else p.sendMessage(ChatColor.RED + "[BukkitManager]" + ChatColor.RED + " The plugin Folder contains no config.yml!");
			}			
		}else if (args.length >5)
		{
			p.sendMessage(ChatColor.RED + "Too many Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm plugin config (plugin) (entry) [value]");
		}else
		{
			p.sendMessage(ChatColor.RED + "Too few Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm plugin config (plugin) (entry) [value]");
		}
	}
	
	public static void console(ConsoleCommandSender c, String[] args)
	{
		if (args.length == 4) {
			if (Bukkit.getServer().getPluginManager().getPlugin(args[2]) == null) {
				c.sendMessage("[BukkitManager] This Plugin does not exists.");
				c.sendMessage("[BukkitManager] Available Plugins: " + getPluginList());
				return;
			}else {
				Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
				File pluginFolder = plugin.getDataFolder();
				if (new File(pluginFolder + File.separator + "config.yml").exists()) {
					Configuration pluginConfig = new Configuration(new File(pluginFolder + File.separator + "config.yml"));
					if (pluginConfig.getProperty(args[3]) != null) {
						c.sendMessage("[BukkitManager] The Entry " + args[3] + " has the value: " + pluginConfig.getProperty(args[3]));
					}else c.sendMessage("[BukkitManager] This Entry isn't available!");
				}else c.sendMessage("[BukkitManager] The plugin Folder contains no config.yml!");
			}
		}else if (args.length == 5) {
			if (Bukkit.getServer().getPluginManager().getPlugin(args[2]) == null) {
				c.sendMessage("[BukkitManager] This Plugin does not exists.");
				c.sendMessage("[BukkitManager] Available Plugins: " + getPluginList());
				return;
			}else {
				Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(args[2]);
				File pluginFolder = plugin.getDataFolder();
				if (new File(pluginFolder + File.separator + "config.yml").exists()) {
					Configuration pluginConfig = new Configuration(new File(pluginFolder + File.separator + "config.yml"));
					if (pluginConfig.getProperty(args[3]) != null) {
						String old = pluginConfig.getProperty(args[3]).toString();
						pluginConfig.setProperty(args[3], args[4]);
						c.sendMessage("[BukkitManager] The Entry " + args[3] + " was set from: " + old + " to: " + pluginConfig.getProperty(args[3]));
					}else c.sendMessage("[BukkitManager] This Entry isn't available!");
				}else c.sendMessage("[BukkitManager] The plugin Folder contains no config.yml!");
			}			
		}else if (args.length >5)
		{
			c.sendMessage("Too many Arguments.");
			c.sendMessage("Usage: /bm plugin config (plugin) (entry) [value]");
		}else
		{
			c.sendMessage("Too few Arguments.");
			c.sendMessage("Usage: /bm plugin config (plugin) (entry) [value]");
		}
	}
	
	private static String getPluginList()
	{
		StringBuilder pluginList = new StringBuilder();
		Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
		
		for (Plugin plugin : plugins) {
			if (pluginList.length() > 0) {
				pluginList.append(ChatColor.WHITE);
				pluginList.append(", ");
			}
			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getName());
		}
		return pluginList.toString();
	}
	
    public static final Logger log = Logger.getLogger("Minecraft");
	public static FileInputStream streamIn = null;
	public static FileOutputStream streamOut = null;
	public static Properties propertie = new Properties();
}
