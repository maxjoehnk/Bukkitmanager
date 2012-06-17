package com.efreak1996.BukkitManager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.efreak1996.BukkitManager.BmPermissions;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmBukkitInfo {

	private static BmIOManager io;
	private static BmPermissions permHandler;
	
	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
	}
	public void shutdown() {}
	
	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 2) io.sendFewArgs(sender, "/bm bukkit info");
			else if (args.length > 2) io.sendManyArgs(sender, "/bm bukkit info");
			else {
				if (permHandler.has(sender, "bm.bukkit.info")) info(sender);
			}
		}else {
			if (args.length < 1) io.sendFewArgs(sender, "/bukkit info");
			else if (args.length > 1) io.sendManyArgs(sender, "/bukkit info");
			else {
				if (permHandler.has(sender, "bm.bukkit.info")) info(sender);
			}
		}
	}
	
	private void info(CommandSender sender) {
		io.send(sender, ChatColor.YELLOW + "---------------------" + ChatColor.WHITE + " SERVER INFOS " + ChatColor.YELLOW + "---------------------", false);
		io.send(sender, "Minecraftversion: " + Bukkit.getBukkitVersion().split("-")[0], false);
		io.send(sender, "API-Version: " + Bukkit.getBukkitVersion(), false);
		io.send(sender, "Bukkitbuild: " + Bukkit.getVersion().split("-b")[1].split("jnks")[0], false);
		if (!Bukkit.getIp().equalsIgnoreCase("")) io.send(sender, "Runs on: " + Bukkit.getIp() + ":" + Bukkit.getPort(), false);
		else io.send(sender, "Runs on all available IP's with port: " + Bukkit.getPort(), false);
	}
}
