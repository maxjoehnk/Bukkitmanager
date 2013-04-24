package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerFoodCmd extends Command {

    public PlayerFoodCmd() {
        super("food", "Player.Food", "bm.player.food", Arrays.asList(
                "(get|set|add|remove)", "[food]", "[player]"),
                CommandCategory.PLAYER);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) io.sendFewArgs(sender,
                "/bm player food (get|set|add|remove) [food] [player]");
        else if (args.length > 4) io.sendManyArgs(sender,
                "/bm player food (get|set|add|remove) [food] [player]");
        else {
            if (args[1].equalsIgnoreCase("get")) {
                if (args.length == 2 && sender instanceof Player) {
                    if (has(sender, "bm.player.food.get.your")) io.send(
                            sender,
                            io.translate("Command.Player.Food.Get.Your")
                                    .replaceAll(
                                            "%food%",
                                            String.valueOf(new BmPlayer(
                                                    (OfflinePlayer) sender)
                                                    .getFoodLevel())));
                } else if (args.length == 3) {
                    if (has(sender, "bm.player.food.get.other")) {
                        OfflinePlayer offPlayer = Bukkit
                                .getOfflinePlayer(args[2]);
                        if (offPlayer != null) {
                            BmPlayer player = new BmPlayer(offPlayer);
                            io.send(sender,
                                    io.translate(
                                            "Command.Player.Food.Get.Other")
                                            .replaceAll("%player%",
                                                    player.getName())
                                            .replaceAll(
                                                    "%food%",
                                                    String.valueOf(player
                                                            .getFoodLevel())));
                        } else io.sendError(sender,
                                io.translate("Command.Player.UnknownPlayer"));
                    }
                } else if (args.length == 2) io.sendError(sender,
                        io.translate("Command.Player.SpecifyPlayer"));
            } else if (args[1].equalsIgnoreCase("set")) {
                if (args.length == 3 && sender instanceof Player) {
                    if (has(sender, "bm.player.food.set.your")) {
                        try {
                            new BmPlayer((OfflinePlayer) sender)
                                    .setFoodLevel(new Integer(args[2]));
                            io.send(sender,
                                    io.translate("Command.Player.Food.Set.Your")
                                            .replaceAll("%food%", args[2]));
                        } catch (NumberFormatException e) {
                            io.sendError(sender,
                                    io.translate("Command.Player.Food.Error"));
                            if (config.getDebug()) e.printStackTrace();
                        }
                    }
                } else if (args.length == 4) {
                    if (has(sender, "bm.player.food.set.other")) {
                        OfflinePlayer offPlayer = Bukkit
                                .getOfflinePlayer(args[3]);
                        if (offPlayer != null) {
                            BmPlayer player = new BmPlayer(offPlayer);
                            try {
                                player.setFoodLevel(new Integer(args[2]));
                                io.send(sender,
                                        io.translate(
                                                "Command.Player.Food.Set.Other")
                                                .replaceAll("%player%",
                                                        player.getName())
                                                .replaceAll("%food%", args[2]));
                            } catch (NumberFormatException e) {
                                io.sendError(sender, io
                                        .translate("Command.Player.Food.Error"));
                                if (config.getDebug()) e.printStackTrace();
                            }
                        } else io.sendError(sender,
                                io.translate("Command.Player.UnknownPlayer"));
                    }
                } else if (args.length == 3) io.sendError(sender,
                        io.translate("Command.Player.SpecifyPlayer"));
            } else if (args[1].equalsIgnoreCase("add")) {
                if (args.length == 3 && sender instanceof Player) {
                    if (has(sender, "bm.player.food.add.your")) {
                        BmPlayer player = new BmPlayer((OfflinePlayer) sender);
                        try {
                            int newFood = player.getFoodLevel()
                                    + new Integer(args[2]);
                            if (newFood > player.getMaxHealth()) {
                                io.sendError(
                                        sender,
                                        io.translate("Command.Player.Food.TooMuch"));
                                return true;
                            }
                            player.setFoodLevel(newFood);
                            io.send(sender,
                                    io.translate("Command.Player.Food.Add.Your")
                                            .replaceAll("%food%", args[2]));
                        } catch (NumberFormatException e) {
                            io.sendError(sender,
                                    io.translate("Command.Player.Food.Error"));
                            if (config.getDebug()) e.printStackTrace();
                        }
                    }
                } else if (args.length == 4) {
                    if (has(sender, "bm.player.food.add.other")) {
                        OfflinePlayer offPlayer = Bukkit
                                .getOfflinePlayer(args[3]);
                        if (offPlayer != null) {
                            BmPlayer player = new BmPlayer(offPlayer);
                            try {
                                int newFood = player.getFoodLevel()
                                        + new Integer(args[2]);
                                if (newFood > 20) {
                                    io.sendError(
                                            sender,
                                            io.translate("Command.Player.Food.TooMuch"));
                                    return true;
                                }
                                player.setFoodLevel(newFood);
                                io.send(sender,
                                        io.translate(
                                                "Command.Player.Food.Add.Other")
                                                .replaceAll("%player%",
                                                        player.getName())
                                                .replaceAll("%food%",
                                                        String.valueOf(newFood)));
                            } catch (NumberFormatException e) {
                                io.sendError(sender, io
                                        .translate("Command.Player.Food.Error"));
                                if (config.getDebug()) e.printStackTrace();
                            }
                        } else io.sendError(sender,
                                io.translate("Command.Player.UnknownPlayer"));
                    }
                } else if (args.length == 3) io.sendError(sender,
                        io.translate("Command.Player.SpecifyPlayer"));
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length == 3 && sender instanceof Player) {
                    if (has(sender, "bm.player.food.remove.your")) {
                        BmPlayer player = new BmPlayer((OfflinePlayer) sender);
                        try {
                            int newFood = player.getFoodLevel()
                                    - new Integer(args[2]);
                            if (newFood < 0) {
                                io.sendError(
                                        sender,
                                        io.translate("Command.Player.Food.TooMuch"));
                                return true;
                            }
                            player.setFoodLevel(newFood);
                            io.send(sender,
                                    io.translate(
                                            "Command.Player.Food.Remove.Your")
                                            .replaceAll("%food%", args[2]));
                        } catch (NumberFormatException e) {
                            io.sendError(sender,
                                    io.translate("Command.Player.Food.Error"));
                            if (config.getDebug()) e.printStackTrace();
                        }
                    }
                } else if (args.length == 4) {
                    if (has(sender, "bm.player.food.remove.other")) {
                        OfflinePlayer offPlayer = Bukkit
                                .getOfflinePlayer(args[3]);
                        if (offPlayer != null) {
                            BmPlayer player = new BmPlayer(offPlayer);
                            try {
                                int newFood = player.getFoodLevel()
                                        - new Integer(args[2]);
                                if (newFood < 0) {
                                    io.sendError(
                                            sender,
                                            io.translate("Command.Player.Food.TooMuch"));
                                    return true;
                                }
                                player.setFoodLevel(newFood);
                                io.send(sender,
                                        io.translate(
                                                "Command.Player.Food.Remove.Other")
                                                .replaceAll("%player%",
                                                        player.getName())
                                                .replaceAll("%food%",
                                                        String.valueOf(newFood)));
                            } catch (NumberFormatException e) {
                                io.sendError(sender, io
                                        .translate("Command.Player.Food.Error"));
                                if (config.getDebug()) e.printStackTrace();
                            }
                        } else io.sendError(sender,
                                io.translate("Command.Player.UnknownPlayer"));
                    }
                } else if (args.length == 3) io.sendError(sender,
                        io.translate("Command.Player.SpecifyPlayer"));
            }
        }
        return true;
    }
}
