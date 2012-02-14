package com.efreak1996.BukkitManager.Commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmOutput;

public class BmBukkitInfo {

	static BmOutput out = new BmOutput();
	
	public static void initialize() {}
	public static void shutdown() {}
	
	public static void player(Player p, String[] args) {
    	p.sendMessage(ChatColor.YELLOW + "------------------------" + ChatColor.WHITE + " SERVER INFOS " + ChatColor.YELLOW + "------------------------");
      	p.sendMessage("Minecraftversion: " + Bukkit.getBukkitVersion());
      	p.sendMessage("Bukkitbuild: " + Bukkit.getVersion().split("-b")[1].split("jnks")[0]);
      	if (!Bukkit.getIp().equalsIgnoreCase("")) p.sendMessage("Runs on: " + Bukkit.getIp() + ":" + Bukkit.getPort());
      	else p.sendMessage("Runs on all available IP's with port: " + Bukkit.getPort());
	}
	public static void console(ConsoleCommandSender c, String[] args) {
    	c.sendMessage(ChatColor.YELLOW + "------------------------" + ChatColor.WHITE + " SERVER INFOS " + ChatColor.YELLOW + "------------------------");
      //c.sendMessage("------------------------ SERVER INFOS ------------------------");
    	c.sendMessage("Minecraftversion: " + Bukkit.getBukkitVersion());
    	c.sendMessage("Bukkitbuild: " + Bukkit.getVersion().split("-b")[1].split("jnks")[0]);
    	if (!Bukkit.getIp().equalsIgnoreCase("")) c.sendMessage("Runs on: " + Bukkit.getIp() + ":" + Bukkit.getPort());
    	else c.sendMessage("Runs on all available IP's with port: " + Bukkit.getPort());
	}
}
