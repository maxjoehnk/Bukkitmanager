//This is only a sample for a general Command Class

package me.efreak1996.BukkitManager.Commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BmExampleCommand {

	public static void initialize()
	{
		//here are the events for starting of bukkit
	}
	public static void shutdown()
	{
		//here are the events for stopping bukkit
	}
	public static void player(Player p, String[] args)
	{
		//here is the player command
	}
	public static void console(ConsoleCommandSender c, String[] args) {
		//here is the console command
	}
}
