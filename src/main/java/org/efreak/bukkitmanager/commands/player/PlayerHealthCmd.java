package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerHealthCmd extends Command {
	
	public PlayerHealthCmd() {
		super("health", "Player.Health", "bm.player.health", Arrays.asList("(get|set|add|remove)", "[health]", "[player]"), CommandCategory.PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player health (get|set|add|remove) [health] [player]");
		else if (args.length > 4) io.sendManyArgs(sender, "/bm player health (get|set|add|remove) [health] [player]");
		else {
			if (args[1].equalsIgnoreCase("get")) {
				if (args.length == 2 && sender instanceof Player) {
					if (has(sender, "bm.player.health.get.your")) io.send(sender, io.translate("Command.Player.Health.Get.Your").replaceAll("%health%", String.valueOf(((Player) sender).getHealth())));
				}
				else if (args.length == 3) {
					if (has(sender, "bm.player.health.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Health.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(player.getHealth())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("set")) {
				if (args.length == 3 && sender instanceof Player) {
					if (has(sender, "bm.player.health.set.your")) {
						try {
							new BmPlayer((OfflinePlayer) sender).setHealth(new Double(args[2]));
							io.send(sender, io.translate("Command.Player.Health.Set.Your").replaceAll("%health%", args[2]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Health.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 4) {
					if (has(sender, "bm.player.health.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								player.setHealth(new Double(args[2]));
								io.send(sender, io.translate("Command.Player.Health.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("add")) {
				if (args.length == 3 && sender instanceof Player) {
					if (has(sender, "bm.player.health.add.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						try {
							double newHealth = player.getHealth() + new Double(args[2]);
							if (newHealth > player.getMaxHealth()) {
								io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
								return true;
							}
							player.setHealth(newHealth);
							io.send(sender, io.translate("Command.Player.Health.Add.Your").replaceAll("%health%", args[2]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Health.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 4) {
					if (has(sender, "bm.player.health.add.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								double newHealth = player.getHealth() + new Double(args[2]);
								if (newHealth > player.getMaxHealth()) {
									io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
									return true;
								}
								player.setHealth(newHealth);
								io.send(sender, io.translate("Command.Player.Health.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(newHealth)));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("remove")) {
				if (args.length == 3 && sender instanceof Player) {
					if (has(sender,  "bm.player.health.remove.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						try {
							double newHealth = player.getHealth() - new Double(args[2]);
							if (newHealth < 0) {
								io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
								return true;
							}
							player.setHealth(newHealth);
							io.send(sender, io.translate("Command.Player.Health.Remove.Your").replaceAll("%health%", args[2]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Health.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 4) {
					if (has(sender,  "bm.player.health.remove.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								double newHealth = player.getHealth() - new Double(args[2]);
								if (newHealth < 0) {
									io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
									return true;
								}
								player.setHealth(newHealth);
								io.send(sender, io.translate("Command.Player.Health.Remove.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(newHealth)));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
}
