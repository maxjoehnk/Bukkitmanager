package me.efreak1996.BukkitManager.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BmHelp {
	
	
	public static void initialize() {}
	public static void shutdown() {}

	public static void player(Player p, String[] args)
	{
		if (args.length == 1)
		{
		p.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(1/2) " + ChatColor.YELLOW + "--------------");
		p.sendMessage(ChatColor.RED + "/bm help" + ChatColor.WHITE + "                     : " + ChatColor.YELLOW + "Shows this help");
		p.sendMessage(ChatColor.RED + "/bm update" + ChatColor.WHITE + "                   : " + ChatColor.YELLOW + "Updates the source database");
		p.sendMessage(ChatColor.RED + "/bm updatebukkit [rc|stable]" + ChatColor.WHITE + " : " + ChatColor.YELLOW + "Updates bukkit");
		p.sendMessage(ChatColor.RED + "/bm updateplugin [plugin|all]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
		p.sendMessage(ChatColor.RED + "/bm plugin list" + ChatColor.WHITE + "              : " + ChatColor.YELLOW + "Lists all plugins");
		p.sendMessage(ChatColor.RED + "/bm plugin info [plugin]" + ChatColor.WHITE + "     : " + ChatColor.YELLOW + "Shows a short info about a plugin");
		p.sendMessage(ChatColor.RED + "/bm plugin enable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Enables a Plugin");
		p.sendMessage(ChatColor.RED + "/bm plugin disable (plugin)" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Disables a Plugin");
		p.sendMessage(ChatColor.RED + "/bm plugin restart (plugin)" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Restarts a Plugin");
        }else if (args.length == 2 && args[1].equalsIgnoreCase("1"))
		{
		p.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(1/2) " + ChatColor.YELLOW + "--------------");
		p.sendMessage(ChatColor.RED + "/bm help" + ChatColor.WHITE + "                     : " + ChatColor.YELLOW + "Shows this help");
		p.sendMessage(ChatColor.RED + "/bm update" + ChatColor.WHITE + "                   : " + ChatColor.YELLOW + "Updates the source database");
		p.sendMessage(ChatColor.RED + "/bm updatebukkit [rc|stable]" + ChatColor.WHITE + " : " + ChatColor.YELLOW + "Updates bukkit");
		p.sendMessage(ChatColor.RED + "/bm updateplugin [plugin|all]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
		p.sendMessage(ChatColor.RED + "/bm plugin list" + ChatColor.WHITE + "              : " + ChatColor.YELLOW + "Lists all plugins");
		p.sendMessage(ChatColor.RED + "/bm plugin info [plugin]" + ChatColor.WHITE + "     : " + ChatColor.YELLOW + "Shows a short info about a plugin");
		p.sendMessage(ChatColor.RED + "/bm plugin enable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Enables a Plugin");
		p.sendMessage(ChatColor.RED + "/bm plugin disable (plugin)" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Disables a Plugin");
		p.sendMessage(ChatColor.RED + "/bm plugin restart (plugin)" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Restarts a Plugin");
        }else  if (args.length == 2 && args[1].equalsIgnoreCase("2"))
		{
		p.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(2/2) " + ChatColor.YELLOW + "--------------");
		p.sendMessage(ChatColor.RED + "/bm config (entry) [value]" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Get/Set a bukkit config entry");
        }
    }
	
	public static void console(ConsoleCommandSender c, String[] args)
	{
		if (args.length == 1) {
        }else if (args.length == 2 && args[1].equalsIgnoreCase("1")) {
        	c.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(1/2) " + ChatColor.YELLOW + "-------------------");
			c.sendMessage(ChatColor.RED + "bm help" + ChatColor.WHITE + "                      : " + ChatColor.YELLOW + "Shows this help");
			c.sendMessage(ChatColor.RED + "bm bukkit info" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Shows a some info about a plugin");
			c.sendMessage(ChatColor.RED + "bm bukkit update [rc|stable]" + ChatColor.WHITE + " : " + ChatColor.YELLOW + "Updates plugins");
			c.sendMessage(ChatColor.RED + "bm bukkit config (entry) [value]" + ChatColor.WHITE + "                                                            : " + ChatColor.YELLOW + "Updates plugins");
			c.sendMessage(ChatColor.RED + "bm addon list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
			c.sendMessage(ChatColor.RED + "bm addon info (addon)" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows a some info about a plugin");
			c.sendMessage(ChatColor.RED + "bm addon enable (addon)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
			c.sendMessage(ChatColor.RED + "bm addon disable (addon)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
        }else  if (args.length == 2 && args[1].equalsIgnoreCase("2")) {
        	c.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(2/2) " + ChatColor.YELLOW + "-------------------");
			c.sendMessage(ChatColor.RED + "bm plugin list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
			c.sendMessage(ChatColor.RED + "bm plugin info [plugin]" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows a some info about a plugin");
			c.sendMessage(ChatColor.RED + "bm plugin enable (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin disable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin restart (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Restarts a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin update [plugin|all]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
			c.sendMessage(ChatColor.RED + "bm plugin install (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Updates plugins");
			c.sendMessage(ChatColor.RED + "bm plugin download (plugin)" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Lists all plugins");
			c.sendMessage(ChatColor.RED + "bm plugin config (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Updates plugins");
        }
	}

	public static void pluginHelpConsole(ConsoleCommandSender c) {
    	c.sendMessage(ChatColor.YELLOW + "------------------" + ChatColor.WHITE + " BUKKITMANAGER PLUGIN HELP " + ChatColor.YELLOW + "------------------");
		c.sendMessage(ChatColor.RED + "bm plugin list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
		c.sendMessage(ChatColor.RED + "bm plugin info [plugin]" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows a short info about a plugin");
		c.sendMessage(ChatColor.RED + "bm plugin enable (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
		c.sendMessage(ChatColor.RED + "bm plugin disable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
		c.sendMessage(ChatColor.RED + "bm plugin restart (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Restarts a Plugin");
		c.sendMessage(ChatColor.RED + "bm plugin update [plugin|all]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
		c.sendMessage(ChatColor.RED + "bm plugin install (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Installs a plugin");
		c.sendMessage(ChatColor.RED + "bm plugin download (plugin)" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Downloads a plugin");
	}

	public static void pluginHelpPlayer(Player p) {
		
	}

}
