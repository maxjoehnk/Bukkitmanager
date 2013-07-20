package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCmd extends CommandHandler {

	public ItemCmd() {
		super();
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		if (args.length == 1) {
			for (String subCommand : subCommands.keySet()) {
				if (subCommand.startsWith(args[0])) tabs.add(subCommand);
			}
		}else if (subCommands.containsKey(args[0])) {
			try {
				CommandHandler handler = (CommandHandler) subCommands.get(args[0]).getDeclaringClass().newInstance();
				return handler.onTabComplete(sender, cmd, label, args);
			}catch(Exception e) {
				io.sendConsoleWarning("Error on TabCompletion: " + e.getLocalizedMessage());
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return tabs;
	}
	
	@Command(label = "item", helpNode = "Item", hideHelp = true, usage = "/item")
	public boolean itemCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@SubCommand(label = "give", helpNode = "Item.Give", usage = "/item give (id|name) [player]")
	public boolean giveCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm item give (id|name) [amount] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm item give (id|name) [amount] [player]");
		else {
			ItemStack item;
			try {
				item = new ItemStack(Material.getMaterial(Integer.valueOf(args[0])));
			}catch (NumberFormatException e) {
				item = new ItemStack(Material.getMaterial(args[0]));
			}
			if (args.length > 1) {
				try {
					item.setAmount(Integer.valueOf(args[1]));
				}catch (NumberFormatException e) {
					item.setAmount(1);
				}
			}
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.getInventory().addItem(item);
			}else if (args.length == 2) {
				Player player = Bukkit.getPlayer(args[1]);
				if (player != null) player.getInventory().addItem(item);
				else io.sendError(sender, "Can't find this player");
			}else if (args.length == 3) {
				Player player = Bukkit.getPlayer(args[2]);
				if (player != null) player.getInventory().addItem(item);
				else io.sendError(sender, "Can't find this player");				
			}else io.sendError(sender, "Please specify a Player");
		}
		return true;
	}
}
