package org.efreak.bukkitmanager.commands.player;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerListCmd extends Command {

	public PlayerListCmd() {
		super("list", "Player.List", "bm.player.list", new ArrayList<String>(), CommandCategory.PLAYER);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm player list");
		else if (args.length > (1 + length)) io.sendFewArgs(sender, "/bm player list");
		else {
			if (has(sender, "bm.player.list")) {
				
			}
		}
		return true;
	}
}
