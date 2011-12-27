package me.efreak1996.BukkitManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BmOutput {

	public String prefix() {
		return ChatColor.DARK_RED + "[BukkitManager] " + ChatColor.WHITE;
	}
	
	public void sendConsole(String msg) {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix() + msg);
	}
	
	public void sendConsole(boolean prefix, String msg) {
		if (prefix) Bukkit.getServer().getConsoleSender().sendMessage(prefix() + msg);
		else Bukkit.getServer().getConsoleSender().sendMessage(msg);
	}
	
	public void sendPlayer(Boolean prefix, Player p, String msg) {
		if (prefix) p.sendMessage(prefix() + msg);
		else p.sendMessage(msg);
	}
	
	public void sendPlayer(Player p, String msg) {
		if (config.getBoolean("General.Show-Prefix", true)) p.sendMessage(prefix() + msg);
		else p.sendMessage(msg);
	}
	
	//config.getBoolean("General.Show-Prefix", true)
	public void sendBroadcast(Boolean prefix, String msg) {
		if (prefix) Bukkit.getServer().broadcastMessage(prefix() + msg);
		else Bukkit.getServer().broadcastMessage(msg);
	}

	public void sendBroadcast(String msg) {
		if (config.getBoolean("General.Show-Prefix", true)) Bukkit.getServer().broadcastMessage(prefix() + msg);
		else Bukkit.getServer().broadcastMessage(msg);
	}
	
	public void sendBroadcast(Boolean prefix, String msg, String perm) {
		if (prefix) Bukkit.getServer().broadcast(prefix() + msg, perm);
		else Bukkit.getServer().broadcast(msg, perm);
	}

	
	public void sendBroadcast(String msg, String perm) {
		if (config.getBoolean("General.Show-Prefix", true)) Bukkit.getServer().broadcast(prefix() + msg, perm);
		else Bukkit.getServer().broadcast(msg, perm);
	}

	public static BmConfiguration config = new BmConfiguration();
}
