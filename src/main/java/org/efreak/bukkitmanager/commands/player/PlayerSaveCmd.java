package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerSaveCmd extends Command {

	public PlayerSaveCmd() {
		super("save", "Player.Save", "bm.player.save", Arrays.asList("[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm player save [player]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm player save [player]");
		else {
			if (args.length == (1 + length) && sender instanceof Player) {
				if (has(sender, "bm.player.save")) {
					((Player) sender).saveData();
					io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
				}
			}else if (args.length == (2 + length) && Bukkit.getPlayer(args[1 + length]) != null) {
				if (has(sender, "bm.player.save.other")) {
					Player player = Bukkit.getPlayer(args[1 + length]);
					player.saveData();
					io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
				}
			}else if (args.length == (1 + length)) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
		}
		return false;
	}
}
