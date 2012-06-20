package org.efreak1996.Bukkitmanager.Commands;


import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.efreak1996.Bukkitmanager.Spout.*;

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