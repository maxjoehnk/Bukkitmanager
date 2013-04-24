package org.efreak.bukkitmanager.commands.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class PlayerLoadCmd extends Command {

    public PlayerLoadCmd() {
        super("load", "Player.Load", "bm.player.load", Arrays
                .asList("[player]"), CommandCategory.PLAYER);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) io.sendFewArgs(sender, "/player load [player]");
        else if (args.length > 2) io.sendManyArgs(sender,
                "/player load [player]");
        else {
            if (args.length == 1 && sender instanceof Player) {
                if (has(sender, "bm.player.load")) {
                    ((Player) sender).loadData();
                    io.send(sender, io.translate("Command.Player.Load")
                            .replaceAll("%player%", sender.getName()));
                }
            } else if (args.length == 2 && Bukkit.getPlayer(args[1]) != null) {
                if (has(sender, "bm.player.load.other")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    player.loadData();
                    io.send(sender, io.translate("Command.Player.Load")
                            .replaceAll("%player%", sender.getName()));
                }
            } else if (args.length == 1) io.sendError(sender,
                    io.translate("Command.Player.SpecifyPlayer"));
            else io.sendError(sender,
                    io.translate("Command.Player.UnknownPlayer"));
        }
        return false;
    }
}
