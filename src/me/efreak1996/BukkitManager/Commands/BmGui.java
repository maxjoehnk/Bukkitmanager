package me.efreak1996.BukkitManager.Commands;

import me.efreak1996.BukkitManager.Spout.*;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BmGui {
	public static void initialize()
	{	
		
	}
	public static void shutdown()
	{
		//here are the events for stopping bukkit
	}
	public static void command(String[] args, Plugin plugin, Player p)
	{
		if (p.hasPermission("bm.gui.guest"))
		{
			BmGuiGuest.show(p);
		}
		if (p.hasPermission("bm.gui.member"))
		{
			BmGuiMember.show(p);
		}
		if (p.hasPermission("bm.gui.vip"))
		{
			BmGuiVip.show(p);
		}
		if (p.hasPermission("bm.gui.supporter"))
		{
			BmGuiSupporter.show(p);
		}
		if (p.hasPermission("bm.gui.admin"))
		{
			BmGuiAdmin.show(p);
		}
	  
	}
}