package org.efreak1996.Bukkitmanager.Commands.Bukkit;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class BukkitInfoCmd extends Command{
	
	public BukkitInfoCmd() {
		super("info", "Bukkit.Info", new ArrayList<String>(), CommandCategory.BUKKIT);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm bukkit info");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm bukkit info");
		else {
			if (has(sender, "bm.bukkit.info")) {
				io.send(sender, ChatColor.YELLOW + "---------------------" + ChatColor.WHITE + " SERVER INFOS " + ChatColor.YELLOW + "---------------------", false);
				io.send(sender, "Minecraftversion: " + Bukkit.getBukkitVersion().split("-")[0], false);
				io.send(sender, "API-Version: " + Bukkit.getBukkitVersion(), false);
				io.send(sender, "Bukkitbuild: " + Bukkit.getVersion().split("-b")[1].split("jnks")[0], false);
				if (!Bukkit.getIp().equalsIgnoreCase("")) io.send(sender, "Runs on: " + Bukkit.getIp() + ":" + Bukkit.getPort(), false);
				else io.send(sender, "Runs on all available IP's with port: " + Bukkit.getPort(), false);
			}
		}
		return true;
	}
}
