package me.efreak1996.BukkitManager.Commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BmConfig {
	
	public static void shutdown() {
	}
	
	public static void initialize()
	{
		try {
			streamIn = new FileInputStream("server.properties");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			propertie.load(streamIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	
	@SuppressWarnings("deprecation")
	public static void player(Player p, String[] args) {
		if (args.length == 2) {
			p.sendMessage("The entry " + args[1] + " has the value: " + ChatColor.DARK_GRAY + propertie.getProperty(args[1]));	
		}else if (args.length == 3) {
			propertie.setProperty(args[1], args[2]);
			p.sendMessage("The entry " + args[1] + " was set to: " + ChatColor.DARK_GRAY + args[2]);
			try {
				streamOut = new FileOutputStream("server.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			propertie.save(streamOut, null);
		}else if (args.length >3) {
			p.sendMessage(ChatColor.RED + "Too many Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm config (entry) [value]");
		}else {
			p.sendMessage(ChatColor.RED + "Too few Arguments.");
			p.sendMessage(ChatColor.RED + "Usage: /bm config (entry) [value]");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void console(ConsoleCommandSender c, String[] args)
	{
		if (args.length == 2)
		{
			c.sendMessage("The entry " + args[1] + " has the value: " + propertie.getProperty(args[1]));	
		}else if (args.length == 3)
		{
			propertie.setProperty(args[1], args[2]);
			c.sendMessage("The entry " + args[1] + " was set to: " + args[2]);
			try {
				streamOut = new FileOutputStream("server.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			propertie.save(streamOut, null);
		}else if (args.length >3)
		{
			c.sendMessage("Too many Arguments.");
			c.sendMessage("Usage: /bm config (entry) [value]");
		}else
		{
			c.sendMessage("Too few Arguments.");
			c.sendMessage("Usage: /bm config (entry) [value]");
		}
	}
	
    public static final Logger log = Logger.getLogger("Minecraft");
	public static FileInputStream streamIn = null;
	public static FileOutputStream streamOut = null;
	public static Properties propertie = new Properties();
}
