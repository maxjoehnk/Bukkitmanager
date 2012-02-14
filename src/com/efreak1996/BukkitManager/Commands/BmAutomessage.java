package com.efreak1996.BukkitManager.Commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.BmAutomessageThread;
import com.efreak1996.BukkitManager.BmOutput;

public class BmAutomessage {

	public static void initialize() {
		msgThread.loadMessageFile();
	}
	
	public static void shutdown() {}
	
	public static void player(Player p, String[] args) {
		if (args[1].equalsIgnoreCase("add")) {
			out.sendPlayer(p, "A new Automessage was added at index " + msgThread.addMessage(args[2]));
		}else if (args[1].equalsIgnoreCase("remove")) {
			if (msgThread.remMessage(new Integer(args[2])) != null)
					out.sendPlayer(p, "The Automessage with Index " + args[2] + " was removed.");
		}else if (args[1].equalsIgnoreCase("send")) {
			msgThread.send(new Integer(args[2]), true);
		}else if (args[1].equalsIgnoreCase("get")) {
			out.sendPlayer(p, "The Automessage with Index " + args[2] + " contains:");
			out.sendPlayer(p, msgThread.getMessage(new Integer(args[2])));
		}
	}
	public static void console(ConsoleCommandSender c, String[] args) {
		if (args[1].equalsIgnoreCase("add")) {
			out.sendConsole("A new Automessage was added at index " + msgThread.addMessage(args[2]));
		}else if (args[1].equalsIgnoreCase("remove")) {
			if (msgThread.remMessage(new Integer(args[2])) != null)
				out.sendConsole("The Automessage with Index " + args[2] + " was removed.");
		}else if (args[1].equalsIgnoreCase("send")) {
			msgThread.send(new Integer(args[2]), true);
		}else if (args[1].equalsIgnoreCase("get")) {
			out.sendConsole("The Automessage with Index " + args[2] + " contains:");
			out.sendConsole(msgThread.getMessage(new Integer(args[2])));
		}
	}
	
	private static BmOutput out = new BmOutput();
	private static BmAutomessageThread msgThread = new BmAutomessageThread();
}
