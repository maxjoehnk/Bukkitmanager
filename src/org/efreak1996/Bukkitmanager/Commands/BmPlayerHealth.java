package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.Database;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Permissions;


public class BmPlayerHealth {

	private static IOManager io;
	private static Permissions permHandler;
	private static Configuration config;
	private static Database db;
	
	public void initialize() {
		io = new IOManager();
		permHandler = new Permissions();
		config = new Configuration();
		db = new Database();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 3) io.sendFewArgs(sender, "/bm player health (get|set|add|remove) [health] [player]");
			else if (args.length > 5) io.sendManyArgs(sender, "/bm player health (get|set|add|remove) [health] [player]");
			else {
				if (args[2].equalsIgnoreCase("get")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.health.get.your")) io.send(sender, io.translate("Command.Player.Health.Get.Your").replaceAll("%health%", String.valueOf(((Player) sender).getHealth())));
					}
					else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.health.get.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) io.send(sender, io.translate("Command.Player.Health.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(player.getHealth())));
								else io.send(sender, io.translate("Command.Player.Health.Get.Other").replaceAll("%player%", args[3]).replaceAll("%health%", String.valueOf(db.getPlayer(args[3], "health"))));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("set")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.health.set.your")) {
							try {
								((Player) sender).setHealth(new Integer(args[3]));
								io.send(sender, io.translate("Command.Player.Health.Set.Your").replaceAll("%health%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.health.set.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								try {
									if (player != null) {
										player.setHealth(new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Health.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", args[3]));
									}else {
										db.setPlayer(args[4], "health", new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Health.Set.Other").replaceAll("%player%", args[4]).replaceAll("%health%", args[3]));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Health.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("add")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.health.add.your")) {
							try {
								int newHealth = ((Player) sender).getHealth() + new Integer(args[3]);
								if (newHealth > ((Player) sender).getMaxHealth()) {
									io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
									return;
								}
								((Player) sender).setHealth(newHealth);
								io.send(sender, io.translate("Command.Player.Health.Add.Your").replaceAll("%health%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.health.add.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								try {
									if (player != null) {
										int newHealth = player.getHealth() + new Integer(args[3]);
										if (newHealth > player.getMaxHealth()) {
											io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
											return;
										}
										player.setHealth(newHealth);
										io.send(sender, io.translate("Command.Player.Health.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(newHealth)));
									}else {
										int newHealth = new Integer(db.getPlayer(args[4], "health").toString()) + new Integer(args[3]);
										if (newHealth > ((Player) sender).getMaxHealth()) {
											io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
											return;
										}
										db.setPlayer(args[4], "health", newHealth);
										io.send(sender, io.translate("Command.Player.Health.Add.Other").replaceAll("%player%", args[4]).replaceAll("%health%", String.valueOf(newHealth)));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Health.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("remove")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender,  "bm.player.health.remove.your")) {
							try {
								int newHealth = ((Player) sender).getHealth() - new Integer(args[3]);
								if (newHealth < 0) {
									io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
									return;
								}
								((Player) sender).setHealth(newHealth);
								io.send(sender, io.translate("Command.Player.Health.Remove.Your").replaceAll("%health%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender,  "bm.player.health.remove.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								try {
									if (player != null) {
										int newHealth = player.getHealth() - new Integer(args[3]);
										if (newHealth < 0) {
											io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
											return;
										}
										player.setHealth(newHealth);
										io.send(sender, io.translate("Command.Player.Health.Remove.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(newHealth)));
									}else {
										int newHealth = new Integer(db.getPlayer(args[4], "health").toString()) - new Integer(args[3]);
										if (newHealth < 0) {
											io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
											return;
										}
										db.setPlayer(args[4], "health", newHealth);
										io.send(sender, io.translate("Command.Player.Health.Remove.Other").replaceAll("%player%", args[4]).replaceAll("%health%", String.valueOf(newHealth)));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Health.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/player health (get|set|add|remove) [health] [player]");
			else if (args.length > 4) io.sendManyArgs(sender, "/player health (get|set|add|remove) [health] [player]");
			else {
				if (args[1].equalsIgnoreCase("get")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.health.get.your")) io.send(sender, io.translate("Command.Player.Health.Get.Your").replaceAll("%health%", String.valueOf(((Player) sender).getHealth())));
					}
					else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.health.get.other")) {
							Player player = Bukkit.getPlayer(args[2]);
							if (player != null) {
								io.send(sender, io.translate("Command.Player.Health.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(player.getHealth())));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("set")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.health.set.your")) {
							try {
								((Player) sender).setHealth(new Integer(args[3]));
								io.send(sender, io.translate("Command.Player.Health.Set.Your").replaceAll("%health%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.health.set.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								try {
									player.setHealth(new Integer(args[2]));
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
						if (permHandler.has(sender, "bm.player.health.add.your")) {
							try {
								int newHealth = ((Player) sender).getHealth() + new Integer(args[2]);
								if (newHealth > ((Player) sender).getMaxHealth()) {
									io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
									return;
								}
								((Player) sender).setHealth(newHealth);
								io.send(sender, io.translate("Command.Player.Health.Add.Your").replaceAll("%health%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.health.add.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								try {
									int newHealth = player.getHealth() + new Integer(args[2]);
									if (newHealth > player.getMaxHealth()) {
										io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
										return;
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
						if (permHandler.has(sender,  "bm.player.health.remove.your")) {
							try {
								int newHealth = ((Player) sender).getHealth() - new Integer(args[2]);
								if (newHealth < 0) {
									io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
									return;
								}
								((Player) sender).setHealth(newHealth);
								io.send(sender, io.translate("Command.Player.Health.Remove.Your").replaceAll("%health%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender,  "bm.player.health.remove.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								try {
									int newHealth = player.getHealth() - new Integer(args[2]);
									if (newHealth < 0) {
										io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
										return;
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
		}
	}
}
