package org.efreak.bukkitmanager.commands.world;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class WorldListCmd extends Command {
	
	public WorldListCmd() {
		super("list", "World.List", "bm.world.list", Arrays.asList("[#]"), CommandCategory.WORLD);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm world list [#]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm world list [#]");
		else {
			if (has(sender, "bm.world.list")) {
				if (args.length == 1) showDetailedList(sender, 1);
				else showDetailedList(sender, Integer.parseInt(args[1]));
			}
		}
		return true;
	}
	
	private static void showDetailedList(CommandSender sender, int page) {
		StringBuilder worldList = new StringBuilder();
		List<World> worlds = Bukkit.getWorlds();
		worldList.append(ChatColor.YELLOW + "---------------------------" + ChatColor.WHITE + " WORLDS " + ChatColor.YELLOW + "---------------------------");
		for (World world : worlds) {
			worldList.append("\n");
			worldList.append(world.getName());
			worldList.append(": " + world.getPlayers().size() + " Player(s) online");
		}
		io.send(sender, worldList.toString(), false);
	}
}
