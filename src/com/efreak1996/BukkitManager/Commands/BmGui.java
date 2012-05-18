package com.efreak1996.BukkitManager.Commands;


import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.Spout.*;

public class BmGui {
	public static void initialize() {}
	public static void shutdown() {}
	public static void command(String[] args, Plugin plugin, Player p)
	{
		if (p.hasPermission("bm.gui.guest")) BmGuiGuest.show(p);
		else if (p.hasPermission("bm.gui.member")) BmGuiMember.show(p);
		else if (p.hasPermission("bm.gui.vip")) BmGuiVip.show(p);
		else if (p.hasPermission("bm.gui.supporter")) BmGuiSupporter.show(p);
		else if (p.hasPermission("bm.gui.admin")) BmGuiAdmin.show(p);
	}
}