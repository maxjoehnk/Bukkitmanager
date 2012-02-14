package com.efreak1996.BukkitManager.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BmHelp {
	
	
	public static void initialize() {}
	public static void shutdown() {}

	public static void player(Player p, String[] args) {
		if (args.length == 1) {
        	p.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(1/2) " + ChatColor.YELLOW + "-------------------");
			p.sendMessage(ChatColor.RED + "bm help [#|command|" + ChatColor.YELLOW + "caption" + ChatColor.RED + "]" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Helps you :D");
			p.sendMessage(ChatColor.RED + "bm plugin config (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Updates plugins");
			p.sendMessage(ChatColor.RED + "bm plugin disable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
			p.sendMessage(ChatColor.RED + "bm plugin enable (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
			p.sendMessage(ChatColor.RED + "bm plugin info [plugin]" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows some info about a plugin");
			p.sendMessage(ChatColor.RED + "bm plugin install (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Installs plugins");
			p.sendMessage(ChatColor.RED + "bm plugin list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
			p.sendMessage(ChatColor.RED + "bm plugin restart (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Restarts a Plugin");
			p.sendMessage(ChatColor.RED + "bm plugin update [plugin|" + ChatColor.YELLOW + "all" + ChatColor.RED + "]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
        }else if (args.length == 2 && args[1].equalsIgnoreCase("1")) {
        	p.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(1/2) " + ChatColor.YELLOW + "-------------------");
        	p.sendMessage(ChatColor.RED + "bm help [#|command|" + ChatColor.YELLOW + "caption" + ChatColor.RED + "]" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Helps you :D");
			p.sendMessage(ChatColor.RED + "bm plugin config (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Updates plugins");
			p.sendMessage(ChatColor.RED + "bm plugin disable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
			p.sendMessage(ChatColor.RED + "bm plugin enable (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
			p.sendMessage(ChatColor.RED + "bm plugin info [plugin]" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows some info about a plugin");
			p.sendMessage(ChatColor.RED + "bm plugin install (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Installs plugins");
			p.sendMessage(ChatColor.RED + "bm plugin list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
			p.sendMessage(ChatColor.RED + "bm plugin restart (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Restarts a Plugin");
			p.sendMessage(ChatColor.RED + "bm plugin update [plugin|" + ChatColor.YELLOW + "all" + ChatColor.RED + "]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
        }else  if (args.length == 2 && args[1].equalsIgnoreCase("2")) {
        	p.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(2/2) " + ChatColor.YELLOW + "-------------------");
			p.sendMessage(ChatColor.RED + "bm bukkit info" + ChatColor.WHITE + "                  : " + ChatColor.YELLOW + "Shows some info about bukkit");
			p.sendMessage(ChatColor.RED + "bm bukkit update [rc|stable|#]" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Updates bukkit");
			p.sendMessage(ChatColor.RED + "bm bukkit config (entry) [value]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Edit server.properties");
			p.sendMessage(ChatColor.RED + "bm automessage add (msg)" + ChatColor.WHITE + "        : " + ChatColor.YELLOW + "Add an Automessage");
			p.sendMessage(ChatColor.RED + "bm automessage get (index)" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Get an Automessage");
			p.sendMessage(ChatColor.RED + "bm automessage remove (index)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Remove an Automessage");
			p.sendMessage(ChatColor.RED + "bm automessage send (index)" + ChatColor.WHITE + "     : " + ChatColor.YELLOW + "Sends an Automessage");
			p.sendMessage(ChatColor.RED + "bm automessage list [#]" + ChatColor.WHITE + "         : " + ChatColor.YELLOW + "Lists all Automessages");
			p.sendMessage(ChatColor.RED + "bm config (entry) [value]" + ChatColor.WHITE + "       : " + ChatColor.YELLOW + "Modify the Bm Config");
       }else if (args.length == 2 && args[1].equalsIgnoreCase("caption")) {
       		p.sendMessage(ChatColor.YELLOW + "------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP LEGEND " + ChatColor.YELLOW + "------------------");
			p.sendMessage(ChatColor.RED + "#" + ChatColor.WHITE + "     : Number (Page or build)");
			p.sendMessage(ChatColor.RED + "value" + ChatColor.WHITE + " : place holder");
			p.sendMessage(ChatColor.YELLOW + "value" + ChatColor.WHITE + " : defined value");
			p.sendMessage(ChatColor.RED + "[]" + ChatColor.WHITE + "    : optional value");
			p.sendMessage(ChatColor.RED + "()" + ChatColor.WHITE + "    : required value");
			p.sendMessage(ChatColor.RED + "|" + ChatColor.WHITE + "     : (or) seperator");

       }
    }
	
	public static void console(ConsoleCommandSender c, String[] args) {
		if (args.length == 1) {
        	c.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(1/2) " + ChatColor.YELLOW + "-------------------");
			c.sendMessage(ChatColor.RED + "bm help [#|command|" + ChatColor.YELLOW + "caption" + ChatColor.RED + "]" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Helps you :D");
			c.sendMessage(ChatColor.RED + "bm plugin config (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Updates plugins");
			c.sendMessage(ChatColor.RED + "bm plugin disable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin enable (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin info [plugin]" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows some info about a plugin");
			c.sendMessage(ChatColor.RED + "bm plugin install (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Installs plugins");
			c.sendMessage(ChatColor.RED + "bm plugin list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
			c.sendMessage(ChatColor.RED + "bm plugin restart (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Restarts a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin update [plugin|" + ChatColor.YELLOW + "all" + ChatColor.RED + "]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
        }else if (args.length == 2 && args[1].equalsIgnoreCase("1")) {
        	c.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(1/2) " + ChatColor.YELLOW + "-------------------");
			c.sendMessage(ChatColor.RED + "bm help [#|command|" + ChatColor.YELLOW + "caption" + ChatColor.RED + "]" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Helps you :D");
			c.sendMessage(ChatColor.RED + "bm plugin config (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Updates plugins");
			c.sendMessage(ChatColor.RED + "bm plugin disable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin enable (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin info [plugin]" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows some info about a plugin");
			c.sendMessage(ChatColor.RED + "bm plugin install (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Installs plugins");
			c.sendMessage(ChatColor.RED + "bm plugin list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
			c.sendMessage(ChatColor.RED + "bm plugin restart (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Restarts a Plugin");
			c.sendMessage(ChatColor.RED + "bm plugin update [plugin|" + ChatColor.YELLOW + "all" + ChatColor.RED + "]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
        }else  if (args.length == 2 && args[1].equalsIgnoreCase("2")) {
        	c.sendMessage(ChatColor.YELLOW + "-------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP(2/2) " + ChatColor.YELLOW + "-------------------");
			c.sendMessage(ChatColor.RED + "bm bukkit info" + ChatColor.WHITE + "                  : " + ChatColor.YELLOW + "Shows a some info about bukkit");
			c.sendMessage(ChatColor.RED + "bm bukkit update [rc|stable|#]" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Updates bukkit");
			c.sendMessage(ChatColor.RED + "bm bukkit config (entry) [value]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Edit server.properties");
       }else if (args.length == 2 && args[1].equalsIgnoreCase("caption")) {
       	c.sendMessage(ChatColor.YELLOW + "------------------" + ChatColor.WHITE + " BUKKITMANAGER HELP LEGEND " + ChatColor.YELLOW + "------------------");
			c.sendMessage(ChatColor.RED + "#" + ChatColor.WHITE + "     : Number (Page or build)");
			c.sendMessage(ChatColor.RED + "value" + ChatColor.WHITE + " : place holder");
			c.sendMessage(ChatColor.YELLOW + "value" + ChatColor.WHITE + " : defined value");
			c.sendMessage(ChatColor.RED + "[]" + ChatColor.WHITE + "    : optional value");
			c.sendMessage(ChatColor.RED + "()" + ChatColor.WHITE + "    : required value");
			c.sendMessage(ChatColor.RED + "|" + ChatColor.WHITE + "     : (or) seperator");

       }
	}

	public static void pluginHelpConsole(ConsoleCommandSender c) {
    	c.sendMessage(ChatColor.YELLOW + "------------------" + ChatColor.WHITE + " BUKKITMANAGER PLUGIN HELP " + ChatColor.YELLOW + "------------------");
		c.sendMessage(ChatColor.RED + "bm plugin config (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Updates plugins");
		c.sendMessage(ChatColor.RED + "bm plugin disable (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Disables a Plugin");
		c.sendMessage(ChatColor.RED + "bm plugin enable (plugin)" + ChatColor.WHITE + "    : " + ChatColor.YELLOW + "Enables a Plugin");
		c.sendMessage(ChatColor.RED + "bm plugin info [plugin]" + ChatColor.WHITE + "      : " + ChatColor.YELLOW + "Shows some info about a plugin");
		c.sendMessage(ChatColor.RED + "bm plugin install (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Installs plugins");
		c.sendMessage(ChatColor.RED + "bm plugin list" + ChatColor.WHITE + "               : " + ChatColor.YELLOW + "Lists all plugins");
		c.sendMessage(ChatColor.RED + "bm plugin restart (plugin)" + ChatColor.WHITE + "   : " + ChatColor.YELLOW + "Restarts a Plugin");
		c.sendMessage(ChatColor.RED + "bm plugin update [plugin|" + ChatColor.YELLOW + "all" + ChatColor.RED + "]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Updates plugins");
	}

	public static void pluginHelpPlayer(Player p) {
		
	}
	
	public static void bukkitHelpConsole(ConsoleCommandSender c) {
    	c.sendMessage(ChatColor.YELLOW + "------------------" + ChatColor.WHITE + " BUKKITMANAGER BUKKIT HELP " + ChatColor.YELLOW + "------------------");
		c.sendMessage(ChatColor.RED + "bm bukkit info" + ChatColor.WHITE + "                  : " + ChatColor.YELLOW + "Shows a some info about bukkit");
		c.sendMessage(ChatColor.RED + "bm bukkit update [rc|stable|#]" + ChatColor.WHITE + "  : " + ChatColor.YELLOW + "Updates bukkit");
		c.sendMessage(ChatColor.RED + "bm bukkit config (entry) [value]" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Edit server.properties");
	}

	public static void bukkitHelpPlayer(Player p) {
		
	}

}
