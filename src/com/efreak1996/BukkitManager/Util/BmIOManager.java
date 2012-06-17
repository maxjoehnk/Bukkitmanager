package com.efreak1996.BukkitManager.Util;

import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;

import com.efreak1996.BukkitManager.BmConfiguration;

/**
 * 
 * The IOManager Handles all Input and Output
 * 
 * @author efreak1996
 * 
 */

public class BmIOManager {
	
	private static BmConfiguration config;
	public static Plugin plugin;
	private static ConversationFactory conversationFactory;
	private static Hashtable<String, Conversation> conversations;
	public static String prefix = ChatColor.DARK_RED + "[BukkitManager] " + ChatColor.WHITE;
	public static String error = ChatColor.RED + "[Error] " + ChatColor.WHITE;
	public static String warning = ChatColor.YELLOW + "[Warning] " + ChatColor.WHITE;
	private static BmTranslator translator;
	private static boolean color = true;
	
	public void initialize() {
		config = new BmConfiguration();
		color = config.getBoolean("IO.ColoredLogs");
		conversationFactory = new ConversationFactory(plugin);
		prefix = color(config.getString("IO.Prefix")) + " " + ChatColor.WHITE;
		error = color(config.getString("IO.Error")) + " " + ChatColor.WHITE;
		warning = color(config.getString("IO.Warning")) + " " + ChatColor.WHITE;
		translator = new BmTranslator();
		translator.initialize();
	}

	public void broadcast(String msg) {
		if (config.getBoolean("IO.Show-Prefix")) Bukkit.getServer().broadcastMessage(color(this.prefix + msg));
		else Bukkit.getServer().broadcastMessage(color(msg));
	}
	public void broadcast(Boolean prefix, String msg) {
		if (prefix) Bukkit.getServer().broadcastMessage(color(this.prefix + msg));
		else Bukkit.getServer().broadcastMessage(color(msg));
	}
	public void broadcast(String msg, String perm) {
		if (config.getBoolean("IO.Show-Prefix")) Bukkit.getServer().broadcast(color(this.prefix + msg), perm);
		else Bukkit.getServer().broadcast(color(msg), perm);
	}
	public void broadcast(Boolean prefix, String msg, String perm) {
		if (prefix) Bukkit.getServer().broadcast(color(this.prefix + msg), perm);
		else Bukkit.getServer().broadcast(color(msg), perm);
	}	
	
	public void sendConsole(String msg) {
		if (config.getBoolean("IO.Show-Prefix")) plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + msg));
		else plugin.getServer().getConsoleSender().sendMessage(color(msg));
	}
	public void sendConsole(String msg, boolean prefix) {
		if (prefix) plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + msg));
		else plugin.getServer().getConsoleSender().sendMessage(color(msg));
	}
	
	public void sendConsoleDev(String msg) {
		plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + "[Dev] " + msg));
	}
	public void sendConsoleDevError(String msg) {
		plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + "[Dev] " + this.error + ChatColor.RED + msg));
	}
	
	public void sendConsoleWarning(String msg) {
		if (config.getBoolean("IO.Show-Prefix")) plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + this.warning + ChatColor.YELLOW + msg));
		else plugin.getServer().getConsoleSender().sendMessage(color(this.warning + msg));
	}
	public void sendConsoleWarning(String msg, boolean prefix) {
		if (prefix) plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + this.warning + ChatColor.YELLOW + msg));
		else plugin.getServer().getConsoleSender().sendMessage(color(this.warning + msg));
	}
	
	public void sendConsoleError(String msg) {
		if (config.getBoolean("IO.Show-Prefix")) plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + this.error + ChatColor.RED + msg));
		else plugin.getServer().getConsoleSender().sendMessage(color(this.error + msg));
	}
	public void sendConsoleError(String msg, boolean prefix) {
		if (prefix) plugin.getServer().getConsoleSender().sendMessage(color(this.prefix + this.error + ChatColor.RED + msg));
		else plugin.getServer().getConsoleSender().sendMessage(color(this.error + msg));
	}
	
	public void send(CommandSender sender, String msg) {
		if (config.getBoolean("IO.Show-Prefix")) sender.sendMessage(color(this.prefix + msg));
		else sender.sendMessage(color(msg));
	}
	public void send(CommandSender sender, String msg, boolean prefix) {
		if (prefix) sender.sendMessage(color(this.prefix + msg));
		else sender.sendMessage(color(msg));
	}
	
	public void sendTranslation(CommandSender sender, String key) {
		if (config.getBoolean("IO.Show-Prefix")) sender.sendMessage(color(this.prefix + translate(key)));
		else sender.sendMessage(color(translate(key)));
	}
	public void sendTranslation(CommandSender sender, String key, boolean prefix) {
		if (prefix) sender.sendMessage(color(this.prefix + translate(key)));
		else sender.sendMessage(color(translate(key)));
	}
	
	public void sendWarning(CommandSender sender, String msg) {
		if (config.getBoolean("IO.Show-Prefix")) sender.sendMessage(color(this.prefix + this.warning + ChatColor.YELLOW + msg));
		else sender.sendMessage(color(this.warning + msg));
	}
	public void sendWarning(CommandSender sender, String msg, boolean prefix) {
		if (prefix) sender.sendMessage(color(this.prefix + this.warning + ChatColor.YELLOW + msg));
		else sender.sendMessage(color(this.warning + msg));
	}

	public void sendError(CommandSender sender, String msg) {
		if (config.getBoolean("IO.Show-Prefix")) sender.sendMessage(color(this.prefix + this.error + ChatColor.RED + msg));
		else sender.sendMessage(color(this.error + msg));
	}
	public void sendError(CommandSender sender, String msg, boolean prefix) {
		if (prefix) sender.sendMessage(color(this.prefix + this.error + ChatColor.RED + msg));
		else sender.sendMessage(color(this.warning + msg));
	}
	
	public void sendFewArgs(CommandSender sender, String usage) {
		if (config.getBoolean("IO.Show-Prefix")) {
			sender.sendMessage(color(this.prefix + translate("Command.FewArgs")));
			sender.sendMessage(color(this.prefix + translate("Command.Usage").replaceAll("%usage%", usage)));
		}else {
			sender.sendMessage(color(translate("Command.FewArgs")));
			sender.sendMessage(color(translate("Command.Usage").replaceAll("%usage%", usage)));
		}
	}
	public void sendFewArgs(CommandSender sender, String usage, boolean prefix) {
		if (prefix) {
			sender.sendMessage(color(this.prefix + translate("Command.FewArgs")));
			sender.sendMessage(color(this.prefix + translate("Command.Usage").replaceAll("%usage%", usage)));
		}else {
			sender.sendMessage(color(translate("Command.FewArgs")));
			sender.sendMessage(color(translate("Command.Usage").replaceAll("%usage%", usage)));
		}
	}

	public void sendManyArgs(CommandSender sender, String usage) {
		if (config.getBoolean("IO.Show-Prefix")) {
			sender.sendMessage(color(this.prefix + translate("Command.ManyArgs")));
			sender.sendMessage(color(this.prefix + translate("Command.Usage").replaceAll("%usage%", usage)));
		}else {
			sender.sendMessage(color(translate("Command.ManyArgs")));
			sender.sendMessage(color(translate("Command.Usage").replaceAll("%usage%", usage)));
		}
	}	
	public void sendManyArgs(CommandSender sender, String usage, boolean prefix) {
		if (prefix) {
			sender.sendMessage(color(this.prefix + translate("Command.ManyArgs")));
			sender.sendMessage(color(this.prefix + translate("Command.Usage").replaceAll("%usage%", usage)));
		}else {
			sender.sendMessage(color(translate("Command.ManyArgs")));
			sender.sendMessage(color(translate("Command.Usage").replaceAll("%usage%", usage)));
		}
	}
	
	public void createConversation(CommandSender sender, String name, Prompt prompt) {
		conversationFactory.withFirstPrompt(prompt);
		conversations.put(name, conversationFactory.buildConversation((Conversable) sender));
	}	
	public Conversation getConversation(String name) {
		return conversations.get(name);
	}
	
	public String translate(String key) {
		return translator.getKey(key);
	}
	
	public BmTranslator getTranslator() {
		return translator;
	}
	
	public String color(String msg) {
		if (color) return parseColor(msg);
		else return remColor(msg);
	}
	
	public String parseColor(String message) {
		message = message.replaceAll("&0", ChatColor.BLACK + "");
		message = message.replaceAll("&1", ChatColor.DARK_BLUE + "");
		message = message.replaceAll("&2", ChatColor.DARK_GREEN + "");
		message = message.replaceAll("&3", ChatColor.DARK_AQUA + "");
		message = message.replaceAll("&4", ChatColor.DARK_RED + "");
		message = message.replaceAll("&5", ChatColor.DARK_PURPLE + "");
		message = message.replaceAll("&6", ChatColor.GOLD + "");
		message = message.replaceAll("&7", ChatColor.GRAY + "");
		message = message.replaceAll("&8", ChatColor.DARK_GRAY + "");
		message = message.replaceAll("&9", ChatColor.BLUE + "");
		message = message.replaceAll("&a", ChatColor.GREEN + "");
		message = message.replaceAll("&b", ChatColor.AQUA + "");
		message = message.replaceAll("&c", ChatColor.RED + "");
		message = message.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
		message = message.replaceAll("&e", ChatColor.YELLOW + "");
		message = message.replaceAll("&f", ChatColor.WHITE + "");
		return message;
	}
	
	public String remColor(String message) {
		message = message.replaceAll("&0", "");
		message = message.replaceAll("&1", "");
		message = message.replaceAll("&2", "");
		message = message.replaceAll("&3", "");
		message = message.replaceAll("&4", "");
		message = message.replaceAll("&5", "");
		message = message.replaceAll("&6", "");
		message = message.replaceAll("&7", "");
		message = message.replaceAll("&8", "");
		message = message.replaceAll("&9", "");
		message = message.replaceAll("&a", "");
		message = message.replaceAll("&b", "");
		message = message.replaceAll("&c", "");
		message = message.replaceAll("&d", "");
		message = message.replaceAll("&e", "");
		message = message.replaceAll("&f", "");
		return message;
	}	
}
