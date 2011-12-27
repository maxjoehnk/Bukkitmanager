package me.efreak1996.BukkitManager.Commands;

import me.efreak1996.BukkitManager.BmOutput;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BmBukkitInfo {

	static BmOutput out = new BmOutput();
	
	public static void initialize() {}
	public static void shutdown() {}
	
	public static void player(Player p, String[] args) {
		//here is the player command
	}
	public static void console(ConsoleCommandSender c, String[] args) {
    	c.sendMessage(ChatColor.YELLOW + "------------------------" + ChatColor.WHITE + " SERVER INFOS " + ChatColor.YELLOW + "------------------------");
      //c.sendMessage("------------------------ SERVER INFOS ------------------------");
    	c.sendMessage("Minecraftversion: " + Bukkit.getBukkitVersion());
    	c.sendMessage("Bukkitbuild: " + Bukkit.getVersion().split("-b")[1].split("jnks")[0]);
    	if (!Bukkit.getIp().equalsIgnoreCase("")) c.sendMessage("Runs on: " + Bukkit.getIp() + ":" + Bukkit.getPort());
    	else c.sendMessage("Runs on all available IP's with port:" + Bukkit.getPort());
	}
}
