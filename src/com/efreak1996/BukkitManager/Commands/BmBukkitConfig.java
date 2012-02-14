package com.efreak1996.BukkitManager.Commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmConfiguration;

public class BmBukkitConfig {
	
	public static void shutdown() {
	}
	
	public static void initialize()
	{
		try {
			streamIn = new FileInputStream("server.properties");
		} catch (FileNotFoundException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
		try {
			propertie.load(streamIn);
		} catch (IOException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void player(Player p, String[] args) {
		try {
			streamIn = new FileInputStream("server.properties");
			propertie.load(streamIn);
		} catch (IOException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
		if (args.length == 3) {
			p.sendMessage("The entry " + args[2] + " has the value: " + ChatColor.DARK_GRAY + propertie.getProperty(args[2]));	
		}else if (args.length == 4) {
			propertie.setProperty(args[2], args[3]);
			p.sendMessage("The entry " + args[2] + " was set to: " + ChatColor.DARK_GRAY + args[3]);
			try {
				streamOut = new FileOutputStream("server.properties");
			} catch (FileNotFoundException e) {
				if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
			}
			propertie.save(streamOut, null);
		}else if (args.length > 4) {
			p.sendMessage(ChatColor.RED + "Too many Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm bukkit config (entry) [value]");
		}else {
			p.sendMessage(ChatColor.RED + "Too few Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm bukkit config (entry) [value]");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void console(ConsoleCommandSender c, String[] args)
	{
		try {
			streamIn = new FileInputStream("server.properties");
			propertie.load(streamIn);
		} catch (IOException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}
		if (args.length == 3) {
			c.sendMessage("The entry " + args[2] + " has the value: " + ChatColor.DARK_GRAY + propertie.getProperty(args[2]));	
		}else if (args.length == 4) {
			propertie.setProperty(args[2], args[3]);
			c.sendMessage("The entry " + args[2] + " was set to: " + ChatColor.DARK_GRAY + args[3]);
			try {
				streamOut = new FileOutputStream("server.properties");
			} catch (FileNotFoundException e) {
				if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
			}
			propertie.save(streamOut, null);
		}else if (args.length > 4) {
			c.sendMessage(ChatColor.RED + "Too many Arguments.");
			c.sendMessage(ChatColor.RED + "Usage: /bm bukkit config (entry) [value]");
		}else {
			c.sendMessage(ChatColor.RED + "Too few Arguments.");
			c.sendMessage(ChatColor.RED + "Usage: /bm bukkit config (entry) [value]");
		}
	}
	
	public static FileInputStream streamIn = null;
	public static FileOutputStream streamOut = null;
	public static Properties propertie = new Properties();
	private static BmConfiguration config = new BmConfiguration();
}
