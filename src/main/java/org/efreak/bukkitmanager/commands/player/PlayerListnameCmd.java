package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerListnameCmd extends Command {

    public PlayerListnameCmd() {
        super("listname", "Player.Listname", "bm.player.listname", Arrays
                .asList("(get|set|reset)", "[listname]", "[player]"),
                CommandCategory.PLAYER);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) io.sendFewArgs(sender,
                "/bm player listname (get|set|reset) [listname] [player]");
        else if (args.length > 4) io.sendManyArgs(sender,
                "/bm player listname (get|set|reset) [listname] [player]");
        else {
            // get
            if (args[1].equalsIgnoreCase("get")) {
                if (args.length == 2 && sender instanceof Player) {
                    if (has(sender, "bm.player.listname.get.your")) io
                            .send(sender,
                                    io.translate(
                                            "Command.Player.Listname.Get.Your")
                                            .replaceAll(
                                                    "%listname%",
                                                    new BmPlayer(
                                                            (OfflinePlayer) sender)
                                                            .getPlayerListName()));
                } else if (args.length == 3) {
                    if (has(sender, "bm.player.listname.get.other")) {
                        OfflinePlayer offPlayer = Bukkit
                                .getOfflinePlayer(args[2]);
                        if (offPlayer != null) {
                            BmPlayer player = new BmPlayer(offPlayer);
                            io.send(sender,
                                    io.translate(
                                            "Command.Player.Listname.Get.Other")
                                            .replaceAll("%player%",
                                                    player.getName())
                                            .replaceAll("%listname%",
                                                    player.getPlayerListName()));
                        } else io.sendError(sender,
                                io.translate("Command.Player.UnknownPlayer"));
                    }
                } else if (args.length == 2) io.sendError(sender,
                        io.translate("Command.Player.SpecifyPlayer"));
            } else if (args[1].equalsIgnoreCase("set")) {
                if (args.length == 3 && sender instanceof Player) {
                    if (has(sender, "bm.player.listname.set.your")) {
                        BmPlayer player = new BmPlayer((OfflinePlayer) sender);
                        player.setPlayerListName(args[2]);
                        io.send(sender,
                                io.translate("Command.Player.Listname.Set.Your")
                                        .replaceAll("%listname%",
                                                player.getPlayerListName()));
                    }
                } else if (args.length == 4) {
                    if (has(sender, "bm.player.listname.set.other")) {
                        OfflinePlayer offPlayer = Bukkit
                                .getOfflinePlayer(args[3]);
                        if (offPlayer != null) {
                            BmPlayer player = new BmPlayer(offPlayer);
                            player.setPlayerListName(args[2]);
                            io.send(sender,
                                    io.translate(
                                            "Command.Player.Listname.Set.Other")
                                            .replaceAll("%player%",
                                                    player.getName())
                                            .replaceAll("%listname%",
                                                    player.getPlayerListName()));
                        } else io.sendError(sender,
                                io.translate("Command.Player.UnknownPlayer"));
                    }
                } else if (args.length == 3) io.sendError(sender,
                        io.translate("Command.Player.SpecifyPlayer"));
            } else if (args[1].equalsIgnoreCase("reset")) {
                if (args.length == 2 && sender instanceof Player) {
                    if (has(sender, "bm.player.listname.reset.your")) {
                        BmPlayer player = new BmPlayer((OfflinePlayer) sender);
                        player.resetPlayerListName();
                        io.send(sender,
                                io.translate(
                                        "Command.Player.Listname.Reset.Your")
                                        .replaceAll("%listname%",
                                                player.getName()));
                    }
                } else if (args.length == 3) {
                    if (has(sender, "bm.player.listname.reset.other")) {
                        OfflinePlayer offPlayer = Bukkit
                                .getOfflinePlayer(args[2]);
                        if (offPlayer != null) {
                            BmPlayer player = new BmPlayer(offPlayer);
                            player.resetPlayerListName();
                            io.send(sender,
                                    io.translate(
                                            "Command.Player.Listname.Reset.Other")
                                            .replaceAll("%player%",
                                                    player.getName())
                                            .replaceAll("%listname%",
                                                    player.getName()));
                        } else io.sendError(sender,
                                io.translate("Command.Player.UnknownPlayer"));
                    }
                } else if (args.length == 2) io.sendError(sender,
                        io.translate("Command.Player.SpecifyPlayer"));
            }
        }
        return true;
    }
}
