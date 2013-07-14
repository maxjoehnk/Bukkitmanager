package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.util.TimeParser;

public class PlayerCmd extends CommandHandler {

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

	@Command(label = "player", helpNode = "Player", hideHelp = true, usage = "/player")
	public boolean playerCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@SubCommand(label = "chat", helpNode = "Player.Chat", permission = "bm.player.chat", usage = "player chat (&oplayer) (&omsg)")
	public boolean chatCommand(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player chat (player) (msg)");
		else {
			if (Bukkit.getPlayer(args[0]) != null) {
				Player player = Bukkit.getPlayer(args[0]);
				StringBuilder msg = new StringBuilder();
				for (int i = 1; i < args.length; i++) msg.append(args[i] + " ");
				if (msg != null) player.chat(msg.substring(0, msg.lastIndexOf(" ")));
			}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
		}
		return true;
	}
	
	@SubCommand(label = "cmd", helpNode = "Player.Cmd", permission = "bm.player.cmd", usage = "player cmd (&oplayer) (&ocmd)")
	public boolean cmdCommand(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player cmd (player) (cmd)");
		else {
			if (Bukkit.getPlayer(args[0]) != null) {
				Player player = Bukkit.getPlayer(args[1]);
				String cmd = null;
				for (int i = 1; i < args.length; i++) {
					if (cmd != null) cmd = cmd + " " + args[i];
					else cmd = args[i];
				}
				if (cmd != null) {
					if (player.performCommand(cmd)) io.sendTranslation(sender, "Command.Player.Cmd.Send");
					else io.sendError(sender, io.translate("Command.Player.Cmd.Error"));
				}
			}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
		}
		return true;
	}
	
	@SubCommand(label = "displayname", helpNode = "Player.Displayname", permission = "bm.player.displayname", usage = "player displayname (get|set|reset) [displayname] [player]")
	public boolean displaynameCommand(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
		else if (args.length > 4) io.sendManyArgs(sender, "/bm player displayname (get|set|reset) [displayname] [player]");
		else {
			//get
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.displayname.get.your")) io.send(sender, io.translate("Command.Player.Displayname.Get.Your").replaceAll("%displayname%", new BmPlayer((OfflinePlayer) sender).getDisplayName()));
				}else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.displayname.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Displayname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getDisplayName()));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			//set 
			}else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.displayname.set.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						player.setDisplayName(args[1]);
						io.send(sender, io.translate("Command.Player.Displayname.Set.Your").replaceAll("%displayname%", player.getDisplayName()));
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.displayname.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							player.setDisplayName(args[1]);
							io.send(sender, io.translate("Command.Player.Displayname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", args[1]));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			//reset
			}else if (args[0].equalsIgnoreCase("reset")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.displayname.reset.your")) {
						BmPlayer player = new BmPlayer((Player) sender);
						player.resetDisplayName();
						io.send(sender, io.translate("Command.Player.Displayname.Reset.Your").replaceAll("%displayname%", player.getDisplayName()));
					}
				}else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.displayname.reset.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							player.resetDisplayName();
							io.send(sender, io.translate("Command.Player.Displayname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%displayname%", player.getDisplayName()));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));

					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
	
	@SubCommand(label = "exp", helpNode = "Player.Exp", permission = "bm.player.exp", usage = "player exp (get|set|add) [&oexp] [&oplayer]")
	public boolean expCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player exp (get|set|add) [exp] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm player exp (get|set|add) [exp] [player]");
		else {
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					if (Permissions.has(sender, "bm.player.exp.get.your")) io.send(sender, io.translate("Command.Player.Exp.Get.Your").replaceAll("%exp%", String.valueOf(player.getExp())));
				}
				else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.exp.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Exp.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", String.valueOf(player.getExp())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.exp.set.your")) {
						try {
							BmPlayer player = new BmPlayer((OfflinePlayer) sender);
							player.setExp(new Integer(args[1]));
							io.send(sender, io.translate("Command.Player.Exp.Set.Your").replaceAll("%exp%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Exp.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.exp.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							try {
								BmPlayer player = new BmPlayer(offPlayer);
								player.setExp(new Integer(args[1]));
								io.send(sender, io.translate("Command.Player.Exp.Set.Other").replaceAll("%player%", args[2]).replaceAll("%exp%", args[1]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("add")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.exp.add.your")) {
						try {
							new BmPlayer((OfflinePlayer) sender).giveExp(new Integer(args[1]));
							io.send(sender, io.translate("Command.Player.Exp.Add.Your").replaceAll("%exp%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Exp.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.exp.add.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							try {
								BmPlayer player = new BmPlayer(offPlayer);
								io.send(sender, io.translate("Command.Player.Exp.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%exp%", args[1]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Exp.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}
	
	@SubCommand(label = "firstseen", helpNode = "Player.Firstseen", permission = "bm.player.firstseen", usage = "player firstseen [&oplayer]")
	public boolean firstseenCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/player firstseen [player]");
		else if (args.length > 1) io.sendManyArgs(sender, "/player firstseen [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.firstseen.your")) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(((OfflinePlayer) sender).getFirstPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR_OF_DAY) + ":";
					if (calendar.get(Calendar.MINUTE) < 10) time += "0";
					time += calendar.get(Calendar.MINUTE);
					io.send(sender, io.translate("Command.Player.Firstseen.Your").replaceAll("%firstseen_date%", date).replaceAll("%firstseen_time%", time));
				}
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.player.firstseen.other")) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(player.getFirstPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR_OF_DAY) + ":";
					if (calendar.get(Calendar.MINUTE) < 10) time += "0";
					time += calendar.get(Calendar.MINUTE);
					if (player != null) io.send(sender, io.translate("Command.Player.Firstseen.Other").replaceAll("%player%", player.getName()).replaceAll("%firstseen_date%", date).replaceAll("%firstseen_time%", time));
					else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}
	
	@SubCommand(label = "food", helpNode = "Player.Food", permission = "bm.player.food", usage = "player food (get|set|add|remove) [&ofood] [&oplayer]")
	public boolean foodCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player food (get|set|add|remove) [food] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm player food (get|set|add|remove) [food] [player]");
		else {
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.food.get.your")) io.send(sender, io.translate("Command.Player.Food.Get.Your").replaceAll("%food%", String.valueOf(new BmPlayer((OfflinePlayer) sender).getFoodLevel())));
				}
				else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.food.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Food.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(player.getFoodLevel())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[1].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.food.set.your")) {
						try {
							new BmPlayer((OfflinePlayer) sender).setFoodLevel(new Integer(args[1]));
							io.send(sender, io.translate("Command.Player.Food.Set.Your").replaceAll("%food%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Food.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.food.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								player.setFoodLevel(new Integer(args[1]));
								io.send(sender, io.translate("Command.Player.Food.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", args[1]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("add")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.food.add.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						try {
							int newFood = player.getFoodLevel() + new Integer(args[1]);
							if (newFood > 20) {
								io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
								return true;
							}
							player.setFoodLevel(newFood);
							io.send(sender, io.translate("Command.Player.Food.Add.Your").replaceAll("%food%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Food.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.food.add.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								int newFood = player.getFoodLevel() + new Integer(args[1]);
								if (newFood > 20) {
									io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
									return true;
								}
								player.setFoodLevel(newFood);
								io.send(sender, io.translate("Command.Player.Food.Add.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(newFood)));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.food.remove.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						try {
							int newFood = player.getFoodLevel() - new Integer(args[1]);
							if (newFood < 0) {
								io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
								return true;
							}
							player.setFoodLevel(newFood);
							io.send(sender, io.translate("Command.Player.Food.Remove.Your").replaceAll("%food%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Food.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.food.remove.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								int newFood = player.getFoodLevel() - new Integer(args[1]);
								if (newFood < 0) {
									io.sendError(sender, io.translate("Command.Player.Food.TooMuch"));
									return true;
								}
								player.setFoodLevel(newFood);
								io.send(sender, io.translate("Command.Player.Food.Remove.Other").replaceAll("%player%", player.getName()).replaceAll("%food%", String.valueOf(newFood)));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Food.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}

	@Command(label = "gm", helpNode = "Player.Gamemode", hideHelp = true, usage = "/gm (&ogamemode) [&oplayer]")
	@SubCommand(label = "gamemode", helpNode = "Player.Gamemode", permission = "bm.player.gamemode", usage = "player gamemode (&ogamemode) [&oplayer]")
	public boolean gamemodeCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player gamemode (gamemode) [player]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm player gamemode (gamemode) [player]");
		else {
			int gamemodeInt = -1;
			GameMode gamemode;
			try {
				gamemodeInt = new Integer(args[0]);
				if (GameMode.getByValue(gamemodeInt) != null) gamemode = GameMode.getByValue(gamemodeInt);
				else {
					io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
					return true;
				}
			}catch (NumberFormatException e) {
				if (GameMode.valueOf(args[0].toUpperCase()) == null) {
					io.sendError(sender, io.translate("Command.Player.Gamemode.Unknown"));
					return true;
				}else gamemode = GameMode.valueOf(args[0].toUpperCase());
			}
			if (args.length == 1 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.gamemode.your")) { 
					new BmPlayer((OfflinePlayer) sender).setGameMode(gamemode);
				}
			}else if (args.length == 2) {
				if (Permissions.has(sender, "bm.player.gamemode.other")) { 
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						player.setGameMode(gamemode);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}	

	@SubCommand(label = "has", helpNode = "Player.Has", permission = "bm.player.has", usage = "player has (&onode) [&oplayer]")
	public boolean hasCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player has (node) [player]");
		else if (args.length > 2) io.sendFewArgs(sender, "/bm player tp (node) [player]");
		else {
			if (args.length == 1 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.has.your")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					if (player.hasPerm(args[0])) io.send(sender, io.translate("Command.Player.Has.Your.Has").replaceAll("%node%", args[0]));
					else io.send(sender, io.translate("Command.Player.Has.Your.Hasnt").replaceAll("%node%", args[0]));
				}
			}else if (args.length == 2) {
				if (Permissions.has(sender, "bm.player.has.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						if (player.hasPerm(args[0])) io.send(sender, io.translate("Command.Player.Has.Other.Has").replaceAll("%player%", args[1]).replaceAll("%node%", args[0]));
						else io.send(sender, io.translate("Command.Player.Has.Other.Hasnt").replaceAll("%player%", args[1]).replaceAll("%node%", args[0]));
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer").replaceAll("%player%", args[1]));
				}
			}
		}
		return true;
	}
	
	@SubCommand(label = "health", helpNode = "Player.Health", permission = "bm.player.health", usage = "player health (get|set|add|remove) [&ohealth] [&oplayer]")
	public boolean healthCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player health (get|set|add|remove) [health] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm player health (get|set|add|remove) [health] [player]");
		else {
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.health.get.your")) io.send(sender, io.translate("Command.Player.Health.Get.Your").replaceAll("%health%", String.valueOf(((Player) sender).getHealth())));
				}
				else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.health.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Health.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", String.valueOf(player.getHealth())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.health.set.your")) {
						try {
							new BmPlayer((OfflinePlayer) sender).setHealth(new Double(args[1]));
							io.send(sender, io.translate("Command.Player.Health.Set.Your").replaceAll("%health%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Health.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.health.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								player.setHealth(new Double(args[1]));
								io.send(sender, io.translate("Command.Player.Health.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%health%", args[1]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Health.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("add")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.health.add.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						try {
							double newHealth = player.getHealth() + new Double(args[1]);
							if (newHealth > player.getMaxHealth()) {
								io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
								return true;
							}
							player.setHealth(newHealth);
							io.send(sender, io.translate("Command.Player.Health.Add.Your").replaceAll("%health%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Health.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.health.add.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								double newHealth = player.getHealth() + new Double(args[1]);
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
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender,  "bm.player.health.remove.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						try {
							double newHealth = player.getHealth() - new Double(args[1]);
							if (newHealth < 0) {
								io.sendError(sender, io.translate("Command.Player.Health.TooMuch"));
								return true;
							}
							player.setHealth(newHealth);
							io.send(sender, io.translate("Command.Player.Health.Remove.Your").replaceAll("%health%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Health.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender,  "bm.player.health.remove.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								double newHealth = player.getHealth() - new Double(args[1]);
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
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}

	@SubCommand(label = "hide", helpNode = "Player.Hide", permission = "bm.player.hide", usage = "player hide [&oplayer]")
	public boolean hideCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm player hide [player]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm player hide [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.hide.your")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					if (!player.isVisible()) io.sendError(sender, io.translate("Command.Player.Hide.Already.You"));
					else {
						player.hide();
						io.send(sender, io.translate("Command.Player.Hide.You"));
						io.sendConsole(io.translate("Command.Player.Hide.Console.You").replaceAll("%player%", player.getName()));
					}
				}
			}else if (args.length == 2) {
				if (Permissions.has(sender, "bm.player.hide.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[0]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						if (!player.isVisible()) io.sendError(sender, io.translate("Command.Player.Hide.Already.Other").replaceAll("%player%", player.getName()));
						else {
							player.hide();
							io.send(sender, io.translate("Command.Player.Hide.Other").replaceAll("%player%", player.getName()));
							io.send(player.getPlayer(), io.translate("Command.Player.Hide.ByOther").replaceAll("%player%", sender.getName()));
							io.sendConsole(io.translate("Command.Player.Hide.Console.Other").replaceAll("%player%", player.getName()).replaceAll("%causer%", sender.getName()));
						}
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return false;
	}

	@SubCommand(label = "info", helpNode = "Player.Info", permission = "bm.player.info", usage = "player info [&oplayer]")
	public boolean infoCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm player info [player]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm player info [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.info.own")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					io.sendHeader(sender, "PLAYER: " + sender.getName().toUpperCase());
					io.send(sender, "Health:     " + player.getHealth() + "/" + player.getMaxHealth(), false);
					io.send(sender, "Foodlevel: " + player.getFoodLevel() + "/20", false);
					io.send(sender, "Gamemode: " + player.getGameMode().toString(), false);
					io.send(sender, "Level:      " + player.getLevel(), false);
					io.send(sender, "World:      " + player.getWorld().getName(), false);
					io.send(sender, "Position:   {x=" + player.getX() + "|y=" + player.getY() + "|z=" + player.getZ() + "}", false);
				}
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.player.info.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[0]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						io.sendHeader(sender, "PLAYER: " + player.getName().toUpperCase());
						io.send(sender, "Health:    " + player.getHealth() + "/" + player.getMaxHealth(), false);
						io.send(sender, "Foodlevel: " + player.getFoodLevel() + "/20", false);
						io.send(sender, "Gamemode:  " + player.getGameMode().toString(), false);
						io.send(sender, "Level:     " + player.getLevel(), false);
						io.send(sender, "World:     " + player.getWorld().getName(), false);
						io.send(sender, "Position:  {x=" + player.getX() + "|y=" + player.getY() + "|z=" + player.getZ() + "}", false);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));

		}
		return true;
	}
	
	@SubCommand(label = "lastseen", helpNode = "Player.Lastseen", permission = "bm.player.lastseen", usage = "player lastseen [&oplayer]")
	public boolean lastseenCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm player lastseen [player]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm player lastseen [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.lastseen.your")) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(((OfflinePlayer) sender).getLastPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR_OF_DAY) + ":";
					if (calendar.get(Calendar.MINUTE) < 10) time += "0";
					time += calendar.get(Calendar.MINUTE);
					io.send(sender, io.translate("Command.Player.Lastseen.Your").replaceAll("%lastseen_date%", date).replaceAll("%lastseen_time%", time));
				}
			}
			else if (args.length == 1) {
				if (Permissions.has(sender, "bm.player.lastseen.other")) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
					Calendar calendar = new GregorianCalendar();
					calendar.setTimeInMillis(player.getLastPlayed());
					String date = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
					String time = calendar.get(Calendar.HOUR_OF_DAY) + ":";
					if (calendar.get(Calendar.MINUTE) < 10) time += "0";
					time += calendar.get(Calendar.MINUTE);
					if (player != null) io.send(sender, io.translate("Command.Player.Lastseen.Other").replaceAll("%player%", player.getName()).replaceAll("%lastseen_date%", date).replaceAll("%lastseen_time%", time));
					else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}else io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
		}
		return true;
	}

	@SubCommand(label = "level", helpNode = "Player.Level", permission = "bm.player.level", usage = "player level (get|set) [&olevel] [&oplayer]")
	public boolean levelCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player level (get|set) [level] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm player level (get|set) [level] [player]");
		else {
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.level.get.your")) io.send(sender, io.translate("Command.Player.Level.Get.Your").replaceAll("%level%", String.valueOf(new BmPlayer((OfflinePlayer) sender).getLevel())));
				}
				else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.level.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Level.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%level%", String.valueOf(player.getLevel())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.level.set.your")) {
						try {
							new BmPlayer((OfflinePlayer) sender).setLevel(new Integer(args[1]));
							io.send(sender, io.translate("Command.Player.Level.Set.Your").replaceAll("%level%", args[1]));
						}catch (NumberFormatException e) {
							io.sendError(sender, io.translate("Command.Player.Level.Error"));
							if (config.getDebug()) e.printStackTrace();
						}
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.level.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							try {
								player.setLevel(new Integer(args[1]));
								io.send(sender, io.translate("Command.Player.Level.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%level%", args[1]));
							}catch (NumberFormatException e) {
								io.sendError(sender, io.translate("Command.Player.Level.Error"));
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}

	@Command(label = "players", helpNode = "Player.List", hideHelp = true, usage = "/players")
	@SubCommand(label = "list", helpNode = "Player.List", permission = "bm.player.list", usage = "player list")
	public boolean listCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm player list");
		else if (args.length > 0) io.sendFewArgs(sender, "/bm player list");
		else {
			Player[] players = Bukkit.getOnlinePlayers();
			StringBuilder playerList = new StringBuilder();
			for (Player player : players) playerList.append(player.getName() + " ");
			io.send(sender, "Online Players (" + players.length + "): " + playerList);
		}
		return true;
	}

	@SubCommand(label = "listname", helpNode = "Player.Listname", permission = "bm.player.listname", usage = "player listname (get|set|reset) [&olistname] [&oplayer]")
	public boolean listnameCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player listname (get|set|reset) [listname] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm player listname (get|set|reset) [listname] [player]");
		else {
			//get
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.listname.get.your")) io.send(sender, io.translate("Command.Player.Listname.Get.Your").replaceAll("%listname%", new BmPlayer((OfflinePlayer) sender).getPlayerListName()));
				}
				else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.listname.get.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							io.send(sender, io.translate("Command.Player.Listname.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", player.getPlayerListName()));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.listname.set.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						player.setPlayerListName(args[1]);
						io.send(sender, io.translate("Command.Player.Listname.Set.Your").replaceAll("%listname%", player.getPlayerListName()));
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.listname.set.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[2]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							player.setPlayerListName(args[1]);
							io.send(sender, io.translate("Command.Player.Listname.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", player.getPlayerListName()));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("reset")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.listname.reset.your")) {
						BmPlayer player = new BmPlayer((OfflinePlayer) sender);
						player.resetPlayerListName();
						io.send(sender, io.translate("Command.Player.Listname.Reset.Your").replaceAll("%listname%", player.getName()));
					}
				}else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.listname.reset.other")) {
						OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer != null) {
							BmPlayer player = new BmPlayer(offPlayer);
							player.resetPlayerListName();
							io.send(sender, io.translate("Command.Player.Listname.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%listname%", player.getName()));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}

	@SubCommand(label = "load", helpNode = "Player.Load", permission = "bm.player.load", usage = "player load [&oplayer]")
	public boolean loadCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/player load [player]");
		else if (args.length > 1) io.sendManyArgs(sender, "/player load [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.load")) {
					((Player) sender).loadData();
					io.send(sender, io.translate("Command.Player.Load").replaceAll("%player%", sender.getName()));
				}
			}else if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
				if (Permissions.has(sender, "bm.player.load.other")) {
					Player player = Bukkit.getPlayer(args[0]);
					player.loadData();
					io.send(sender, io.translate("Command.Player.Load").replaceAll("%player%", sender.getName()));
				}
			}else if (args.length == 0) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
		}
		return false;
	}

	@SubCommand(label = "location", helpNode = "Player.Location", permission = "bm.player.location", usage = "player location [&oplayer]")
	public boolean locationCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm player location [player]");
		else if (args.length > 1) io.sendFewArgs(sender, "/bm player location [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.location.own")) {
					Location location = new BmPlayer((OfflinePlayer) sender).getLocation();
					io.sendHeader(sender, "LOCATION OF " + sender.getName().toUpperCase());
					io.send(sender, "World:    " + location.getWorld().getName(), false);
					io.send(sender, "Position: {x=" + location.getBlockX() + "|y=" + location.getBlockY() + "|z=" + location.getBlockZ() + "}", false);
					io.send(sender, "Chunk:    {x=" + location.getChunk().getX() + "|z=" + location.getChunk().getZ() + "}", false);
				}
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.player.location.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[0]);
					if (offPlayer != null) {
						Location location = new BmPlayer(offPlayer).getLocation();
						io.sendHeader(sender, "LOCATION OF " + offPlayer.getName().toUpperCase());
						io.send(sender, "World:    " + location.getWorld(), false);
						io.send(sender, "Position: {x=" + location.getBlockX() + "|y=" + location.getBlockY() + "|z=" + location.getBlockZ() + "}", false);
						io.send(sender, "Chunk:    {x=" + location.getChunk().getX() + "|z=" + location.getChunk().getZ() + "}", false);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}
		}
		return true;
	}

	@SubCommand(label = "save", helpNode = "Player.Save", permission = "bm.player.save", usage = "player save [&oplayer]")
	public boolean saveCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm player save [player]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm player save [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.save")) {
					((Player) sender).saveData();
					io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
				}
			}else if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
				if (Permissions.has(sender, "bm.player.save.other")) {
					Player player = Bukkit.getPlayer(args[0]);
					player.saveData();
					io.send(sender, io.translate("Command.Player.Save").replaceAll("%player%", sender.getName()));
				}
			}else if (args.length == 0) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
		}
		return false;
	}
	
	@SubCommand(label = "show", helpNode = "Player.Show", permission = "bm.player.show", usage = "player show [&oplayer]")
	public boolean showCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm player show [player]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm player show [player]");
		else {
			if (args.length == 0 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.show.your")) {
					BmPlayer player = new BmPlayer((OfflinePlayer) sender);
					if (player.isVisible()) io.sendError(sender, io.translate("Command.Player.Show.Already.You"));
					else {
						player.show();
						io.send(sender, io.translate("Command.Player.Show.You"));
						io.sendConsole(io.translate("Command.Player.Show.Console.You").replaceAll("%player%", player.getName()));
					}
				}
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.player.show.other")) {
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[0]);
					if (offPlayer != null) {
						BmPlayer player = new BmPlayer(offPlayer);
						if (player.isVisible()) io.sendError(sender, io.translate("Command.Player.Show.Already.Other").replaceAll("%player%", player.getName()));
						else {
							player.show();
							io.send(sender, io.translate("Command.Player.Show.Other").replaceAll("%player%", player.getName()));
							io.send(player.getPlayer(), io.translate("Command.Player.Show.ByOther").replaceAll("%player%", sender.getName()));
							io.sendConsole(io.translate("Command.Player.Show.Console.Other").replaceAll("%player%", player.getName()).replaceAll("%causer%", sender.getName()));
						}
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}
			}
		}
		return false;
	}
	
	@SubCommand(label = "time", helpNode = "Player.Time", permission = "bm.player.time", usage = "player time (get|set|reset) [&otime] [&oplayer]")
	public boolean timeCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player time (get|set|reset) [time] [player]");
		else if (args.length > 3) io.sendManyArgs(sender, "/player time (get|set|reset) [time] [player]");
		else {
			if (args[0].equalsIgnoreCase("get")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.time.get.your")) io.send(sender, io.translate("Command.Player.Time.Get.Your").replaceAll("%time%", String.valueOf(((Player) sender).getPlayerTime())));
				}
				else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.time.get.other")) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player != null) {
							io.send(sender, io.translate("Command.Player.Time.Get.Other").replaceAll("%player%", player.getName()).replaceAll("%time%", String.valueOf(player.getPlayerTime())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("set")) {
				if (args.length == 2 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.time.set.your")) {
						((Player) sender).setPlayerTime(Long.parseLong(String.valueOf(TimeParser.matchTime(args[1]))), false);
						io.send(sender, io.translate("Command.Player.Time.Set.Your").replaceAll("%time%", args[1]));
					}
				}else if (args.length == 3) {
					if (Permissions.has(sender, "bm.player.time.set.other")) {
						Player player = Bukkit.getPlayer(args[2]);
						if (player != null) {
							player.setPlayerTime(Long.parseLong(String.valueOf(TimeParser.matchTime(args[1]))), false);
							io.send(sender, io.translate("Command.Player.Time.Set.Other").replaceAll("%player%", player.getName()).replaceAll("%time%", args[1]));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 2) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}else if (args[0].equalsIgnoreCase("reset")) {
				if (args.length == 1 && sender instanceof Player) {
					if (Permissions.has(sender, "bm.player.time.reset.your")) {
						((Player) sender).resetPlayerTime();
						io.send(sender, io.translate("Command.Player.Time.Reset.Your").replaceAll("%time%", String.valueOf(((Player) sender).getWorld().getFullTime())));
					}
				}else if (args.length == 2) {
					if (Permissions.has(sender, "bm.player.time.reset.other")) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player != null) {
							player.resetPlayerTime();
							io.send(sender, io.translate("Command.Player.Time.Reset.Other").replaceAll("%player%", player.getName()).replaceAll("%time%", String.valueOf(player.getWorld().getFullTime())));
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
					}
				}else if (args.length == 1) io.sendError(sender, io.translate("Command.Player.SpecifyPlayer"));
			}
		}
		return true;
	}

	@SubCommand(label = "tp", helpNode = "Player.Tp", permission = "bm.player.tp", usage = "player tp (&oplayer1) [&oplayer2]")
	public boolean teleportCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm player tp (player1) [player2]");
		else if (args.length > 2) io.sendFewArgs(sender, "/bm player tp (player1) [player2]");
		else {
			if (args.length == 1 && sender instanceof Player) {
				if (Permissions.has(sender, "bm.player.tp.self")) {
					BmPlayer player1 = new BmPlayer((OfflinePlayer) sender);
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[0]);
					if (offPlayer != null) {
						BmPlayer player2 = new BmPlayer(offPlayer);
						player1.teleport(player2.getLocation(), TeleportCause.COMMAND);
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer"));
				}else io.sendError(sender, "You don't have permission to do that");
			}else if (args.length == 2) {
				if (Permissions.has(sender, "bm.player.tp.other")) {
					OfflinePlayer offPlayer1 = Bukkit.getOfflinePlayer(args[0]);
					if (offPlayer1 != null) {
						BmPlayer player1 = new BmPlayer(offPlayer1);
						OfflinePlayer offPlayer2 = Bukkit.getOfflinePlayer(args[1]);
						if (offPlayer2 != null) {
							BmPlayer player2 = new BmPlayer(offPlayer2);
							player1.teleport(player2.getLocation(), TeleportCause.COMMAND);
							io.send(player1.getPlayer(), "You were teleported to " + player2.getName() + " by " + sender.getName());
						}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer").replaceAll("%player%", args[0]));
					}else io.sendError(sender, io.translate("Command.Player.UnknownPlayer").replaceAll("%player%", args[1]));
				}else io.sendError(sender, "You don't have permission to do that");
			}

		}
		return true;
	}
}
