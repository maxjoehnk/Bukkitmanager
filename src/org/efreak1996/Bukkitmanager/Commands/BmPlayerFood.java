package org.efreak1996.Bukkitmanager.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmDatabase;
import org.efreak1996.Bukkitmanager.BmPermissions;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public class BmPlayerFood {

	private static BmIOManager io;
	private static BmPermissions permHandler;
	private static BmConfiguration config;
	private static BmDatabase db;
	
	public void initialize() {
		io = new BmIOManager();
		permHandler = new BmPermissions();
		config = new BmConfiguration();
		db = new BmDatabase();
	}
	public void shutdown() {}

	public void cmd(CommandSender sender, String[] args, boolean prefixed) {
		if (prefixed) {
			if (args.length < 3) io.sendFewArgs(sender, "/bm player food (get|set|add|remove) [food] [player]");
			else if (args.length > 5) io.sendManyArgs(sender, "/bm player food (get|set|add|remove) [food] [player]");
			else {
				if (args[2].equalsIgnoreCase("get")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.get.your")) io.send(sender, io.translate("Command.Player.Food.Get.Your").replaceAll("%food%", String.valueOf(((Player) sender).getFoodLevel())));
					}
					else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.food.get.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[3]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								if (player != null) io.send(sender, io.translate("Command.Player.Food.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(player.getFoodLevel())));
								else io.send(sender, io.translate("Command.Player.Food.Get.Other").replaceAll("%player%", args[3]).replaceAll("%food%", String.valueOf(db.getPlayer(args[3], "foodlevel"))));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("set")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.set.your")) {
							try {
								((Player) sender).setFoodLevel(new Integer(args[3]));
								io.send(sender, io.translate("Command.Player.Food.Set.Your").replaceAll("%food%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.food.set.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								try {
									if (player != null) {
										player.setFoodLevel(new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Food.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", args[3]));
									}else {
										db.setPlayer(args[4], "foodlevel", new Integer(args[3]));
										io.send(sender, io.translate("Command.Player.Food.Set.Other").replaceAll("%player%", args[4]).replaceAll("%food%", args[3]));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Food.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("add")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.add.your")) {
							try {
								int newFood = ((Player) sender).getFoodLevel() + new Integer(args[3]);
								if (newFood > ((Player) sender).getMaxHealth()) {
									io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
									return;
								}
								((Player) sender).setFoodLevel(newFood);
								io.send(sender, io.translate("Command.Player.Food.Add.Your").replaceAll("%food%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.food.add.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								try {
									if (player != null) {
										int newFood = player.getFoodLevel() + new Integer(args[3]);
										if (newFood > player.getHealth()) {
											io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
											return;
										}
										player.setFoodLevel(newFood);
										io.send(sender, io.translate("Command.Player.Food.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(newFood)));
									}else {
										int newFood = new Integer(db.getPlayer(args[4], "foodlevel").toString()) + new Integer(args[3]);
										if (newFood > ((Player) sender).getMaxHealth()) {
											io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
											return;
										}
										db.setPlayer(args[4], "foodlevel", newFood);
										io.send(sender, io.translate("Command.Player.Food.Add.Other").replaceAll("%player%", args[4]).replaceAll("%food%", String.valueOf(newFood)));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Food.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[2].equalsIgnoreCase("remove")) {
					if (args.length == 4 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.remove.your")) {
							try {
								int newFood = ((Player) sender).getFoodLevel() - new Integer(args[3]);
								if (newFood < 0) {
									io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
									return;
								}
								((Player) sender).setFoodLevel(newFood);
								io.send(sender, io.translate("Command.Player.Food.Remove.Your").replaceAll("%food%", args[3]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 5) {
						if (permHandler.has(sender, "bm.player.food.remove.other")) {
							OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[4]);
							if (offPlayer != null) {
								Player player = offPlayer.getPlayer();
								try {
									if (player != null) {
										int newFood = player.getFoodLevel() - new Integer(args[3]);
										if (newFood < 0) {
											io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
											return;
										}
										player.setFoodLevel(newFood);
										io.send(sender, io.translate("Command.Player.Food.Remove.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(newFood)));
									}else {
										int newFood = new Integer(db.getPlayer(args[4], "foodlevel").toString()) - new Integer(args[3]);
										if (newFood < 0) {
											io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
											return;
										}
										db.setPlayer(args[4], "foodlevel", newFood);
										io.send(sender, io.translate("Command.Player.Food.Remove.Other").replaceAll("%player%", args[4]).replaceAll("%food%", String.valueOf(newFood)));
									}
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Food.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 4) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}
			}
		}else {
			if (args.length < 2) io.sendFewArgs(sender, "/player food (get|set|add|remove) [food] [player]");
			else if (args.length > 4) io.sendManyArgs(sender, "/player food (get|set|add|remove) [food] [player]");
			else {
				if (args[1].equalsIgnoreCase("get")) {
					if (args.length == 2 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.get.your")) io.send(sender, io.translate("Command.Player.Food.Get.Your").replaceAll("%food%", String.valueOf(((Player) sender).getFoodLevel())));
					}
					else if (args.length == 3) {
						if (permHandler.has(sender, "bm.player.food.get.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								io.send(sender, io.translate("Command.Player.Food.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(player.getFoodLevel())));
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("set")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.set.your")) {
							try {
								((Player) sender).setFoodLevel(new Integer(args[2]));
								io.send(sender, io.translate("Command.Player.Food.Set.Your").replaceAll("%food%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.food.set.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								try {
									player.setFoodLevel(new Integer(args[2]));
									io.send(sender, io.translate("Command.Player.Food.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", args[2]));
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Food.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("add")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.add.your")) {
							try {
								int newFood = ((Player) sender).getFoodLevel() + new Integer(args[2]);
								if (newFood > ((Player) sender).getMaxHealth()) {
									io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
									return;
								}
								((Player) sender).setFoodLevel(newFood);
								io.send(sender, io.translate("Command.Player.Food.Add.Your").replaceAll("%food%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.food.add.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								try {
									int newFood = player.getFoodLevel() + new Integer(args[2]);
									if (newFood > player.getHealth()) {
										io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
										return;
									}
									player.setFoodLevel(newFood);
									io.send(sender, io.translate("Command.Player.Food.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(newFood)));
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Food.Error"));
									if (config.getDebug()) e.printStackTrace();
								}
							}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
						}
					}else if (args.length == 3) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
				}else if (args[1].equalsIgnoreCase("remove")) {
					if (args.length == 3 && sender instanceof Player) {
						if (permHandler.has(sender, "bm.player.food.remove.your")) {
							try {
								int newFood = ((Player) sender).getFoodLevel() - new Integer(args[2]);
								if (newFood < 0) {
									io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
									return;
								}
								((Player) sender).setFoodLevel(newFood);
								io.send(sender, io.translate("Command.Player.Food.Remove.Your").replaceAll("%food%", args[2]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}
					}else if (args.length == 4) {
						if (permHandler.has(sender, "bm.player.food.remove.other")) {
							Player player = Bukkit.getPlayer(args[3]);
							if (player != null) {
								try {
									int newFood = player.getFoodLevel() - new Integer(args[2]);
									if (newFood < 0) {
										io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
										return;
									}
									player.setFoodLevel(newFood);
									io.send(sender, io.translate("Command.Player.Food.Remove.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(newFood)));
								}catch (NumberFormatException e) {
									io.sendError(sender, io.translate("Command.Player.Food.Error"));
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
