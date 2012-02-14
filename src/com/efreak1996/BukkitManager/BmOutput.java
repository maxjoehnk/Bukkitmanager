package com.efreak1996.BukkitManager;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * 
 * The main Output class, which handeles all stuff which is send to console or chat
 * 
 * @author urselbu
 *
 * @version Alpha 1.3
 * 
 */

public class BmOutput {

	public static BmConfiguration config = new BmConfiguration();
	public static Logger log = Logger.getLogger("Minecraft");
	
	/**
	 * 
	 * The prefix which will be shown in messages
	 * 
	 * @return The prefix which will be shown
	 */
	
	public String prefix() {
		return ChatColor.DARK_RED + "[BukkitManager] " + ChatColor.WHITE;
	}
	
	/**
	 * 
	 * The additional prefix which will be shown before a warning message
	 * 
	 * @return The additional prefix
	 */
	
	public String warning() {
		return ChatColor.RED + "[Warning] ";
	}
	
	/**
	 * 
	 * The additional prefix which will be shown before an error message
	 * 
	 * @return The additional prefix
	 */	
	public String error() {
		return ChatColor.RED + "[Error] ";
	}
	
	/**
	 * 
	 * Returns the Usage of the command
	 * 
	 * @param usage How to use the command
	 * @return a full Usage description
	 */
	
	public String usage(String usage) {
		return ChatColor.RED + "Usage: " + usage;
	}
	
	/**
	 * 
	 * @return The 'Too few Arguments' Error
	 * 
	 */
	
	public String fewArgs() {
		return ChatColor.RED + "Too few Arguments!";
	}

	/**
	 * 
	 * @return The 'Too many Arguments' Error
	 * 
	 */
	
	public String manyArgs() {
		return ChatColor.RED + "Too many Arguments!";
	}
	
	/**
	 * 
	 * Sends a color formatted Message to the console
	 * 
	 * @param msg The message which should be send
	 */
	
	public void sendConsole(String msg) {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix() + msg);
	}
	
	public void sendConsole(boolean prefix, String msg) {
		if (prefix) Bukkit.getServer().getConsoleSender().sendMessage(prefix() + msg);
		else Bukkit.getServer().getConsoleSender().sendMessage(msg);
	}
	
	public void sendConsoleWarning(String msg) {
		log.warning(prefix() + msg);
	}
	
	public void sendConsoleWarning(boolean prefix, String msg) {
		if (prefix) log.warning(prefix() + msg);
		else log.warning(msg);
	}
	
	public void sendConsoleError(String msg) {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix() + error() + msg);
	}

	public void sendConsoleError(boolean prefix, String msg) {
		if (prefix) Bukkit.getServer().getConsoleSender().sendMessage(prefix() + error() + msg);
		else Bukkit.getServer().getConsoleSender().sendMessage(error() + msg);
	}

	public void sendConsoleFewArgs(Boolean prefix, String usage) {
		if (prefix) {
			Bukkit.getServer().getConsoleSender().sendMessage(prefix() + fewArgs());
			Bukkit.getServer().getConsoleSender().sendMessage(prefix() + usage(usage));
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(fewArgs());
			Bukkit.getServer().getConsoleSender().sendMessage(usage(usage));
		}
	}
	
	public void sendConsoleFewArgs(String usage) {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix() + fewArgs());
		Bukkit.getServer().getConsoleSender().sendMessage(prefix() + usage(usage));
	}

	public void sendConsoleManyArgs(Boolean prefix, String usage) {
		if (prefix) {
			Bukkit.getServer().getConsoleSender().sendMessage(prefix() + manyArgs());
			Bukkit.getServer().getConsoleSender().sendMessage(prefix() + usage(usage));
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(manyArgs());
			Bukkit.getServer().getConsoleSender().sendMessage(usage(usage));
		}
	}
	
	public void sendConsoleManyArgs(String usage) {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix() + manyArgs());
		Bukkit.getServer().getConsoleSender().sendMessage(prefix() + usage(usage));
	}

	public void sendPlayer(Boolean prefix, Player p, String msg) {
		if (prefix) p.sendMessage(prefix() + msg);
		else p.sendMessage(msg);
	}
	
	public void sendPlayer(Player p, String msg) {
		if (config.Config.getBoolean("General.Show-Prefix")) p.sendMessage(prefix() + msg);
		else p.sendMessage(msg);
	}
	
	public void sendPlayerWarning(Boolean prefix, Player p, String msg) {
		if (prefix) p.sendMessage(prefix() + warning() + msg);
		else p.sendMessage(warning() + msg);
	}
	
	public void sendPlayerWarning(Player p, String msg) {
		if (config.Config.getBoolean("General.Show-Prefix")) p.sendMessage(prefix() + warning() + msg);
		else p.sendMessage(warning() + msg);
	}

	public void sendPlayerError(Boolean prefix, Player p, String msg) {
		if (prefix) p.sendMessage(prefix() + error() + msg);
		else p.sendMessage(error() + msg);
	}
	
	public void sendPlayerError(Player p, String msg) {
		if (config.Config.getBoolean("General.Show-Prefix")) p.sendMessage(prefix() + error() + msg);
		else p.sendMessage(error() + msg);
	}

	public void sendPlayerFewArgs(Boolean prefix, Player p, String usage) {
		if (prefix) {
			p.sendMessage(prefix() + fewArgs());
			p.sendMessage(prefix() + usage(usage));
		}
		else {
			p.sendMessage(fewArgs());
			p.sendMessage(usage(usage));
		}
	}
	
	public void sendPlayerFewArgs(Player p, String usage) {
		if (config.Config.getBoolean("General.Show-Prefix")) {
			p.sendMessage(prefix() + fewArgs());
			p.sendMessage(prefix() + usage(usage));
		}
		else {
			p.sendMessage(fewArgs());
			p.sendMessage(usage(usage));
		}
	}

	public void sendPlayerManyArgs(Boolean prefix, Player p, String usage) {
		if (prefix) {
			p.sendMessage(prefix() + manyArgs());
			p.sendMessage(prefix() + usage(usage));
		}
		else {
			p.sendMessage(manyArgs());
			p.sendMessage(usage(usage));
		}
	}
	
	public void sendPlayerManyArgs(Player p, String usage) {
		if (config.Config.getBoolean("General.Show-Prefix")) {
			p.sendMessage(prefix() + manyArgs());
			p.sendMessage(prefix() + usage(usage));
		}
		else {
			p.sendMessage(manyArgs());
			p.sendMessage(usage(usage));
		}
	}

	//config.Config.getBoolean("General.Show-Prefix")
	public void sendBroadcast(Boolean prefix, String msg) {
		if (prefix) Bukkit.getServer().broadcastMessage(prefix() + msg);
		else Bukkit.getServer().broadcastMessage(msg);
	}

	public void sendBroadcast(String msg) {
		if (config.Config.getBoolean("General.Show-Prefix")) Bukkit.getServer().broadcastMessage(prefix() + msg);
		else Bukkit.getServer().broadcastMessage(msg);
	}
	
	public void sendBroadcast(Boolean prefix, String msg, String perm) {
		if (prefix) Bukkit.getServer().broadcast(prefix() + msg, perm);
		else Bukkit.getServer().broadcast(msg, perm);
	}

	
	public void sendBroadcast(String msg, String perm) {
		if (config.Config.getBoolean("General.Show-Prefix")) Bukkit.getServer().broadcast(prefix() + msg, perm);
		else Bukkit.getServer().broadcast(msg, perm);
	}
}
