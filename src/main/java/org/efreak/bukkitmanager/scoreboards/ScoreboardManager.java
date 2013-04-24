package org.efreak.bukkitmanager.scoreboards;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class ScoreboardManager {

    private static IOManager io;
    private static Configuration config;
    private static boolean enabled = false;
    private static HashMap<BmPlayer, CustomScoreboard> mainScoreboards;

    static {
        io = Bukkitmanager.getIOManager();
        config = Bukkitmanager.getConfiguration();
    }

    public void init() {
        if (config.getBoolean("Scoreboards.Enabled")) {
            try {
                Class.forName("net.minecraft.server.v1_5_R1.Scoreboard");
                mainScoreboards = new HashMap<BmPlayer, CustomScoreboard>();
                enabled = true;
            } catch (Exception e) {
                io.sendConsoleWarning("Couldn't enable Scoreboards. Can be fixed with a server update or an update of Bukkitmanager.");
            }
        }
    }

    public static void showMainScoreboard(BmPlayer player) {
        if (enabled) {
            mainScoreboards.put(player, new CustomScoreboard("Bukkitmanager",
                    player.getPlayer()));
            if (config.getBoolean("Scoreboards.Updates")
                    && Permissions.has(player.getPlayer(),
                            "bm.notifications.updates")
                    && PluginManager.getPluginUpdateCount() != 0) mainScoreboards
                    .get(player).sendItem("Updates",
                            PluginManager.getPluginUpdateCount());
            if (config.getBoolean("Scoreboards.PlayerCount")) mainScoreboards
                    .get(player).sendItem("Online Player",
                            Bukkit.getOnlinePlayers().length);
        }
    }

    public static void updateMainScoreboard(BmPlayer player) {
        if (enabled) {
            if (config.getBoolean("Scoreboards.Updates")
                    && Permissions.has(player.getPlayer(),
                            "bm.notifications.updates")) {
                if (PluginManager.getPluginUpdateCount() == 0) mainScoreboards
                        .get(player).removeItem("Updates");
                else mainScoreboards.get(player).sendItem("Updates",
                        PluginManager.getPluginUpdateCount());
            }
            if (config.getBoolean("Scoreboards.PlayerCount")) mainScoreboards
                    .get(player).sendItem("Online Player",
                            Bukkit.getOnlinePlayers().length);
        }
    }

    public static void hideMainScoreboard(BmPlayer player) {
        if (config.getBoolean("Scoreboards.Updates")
                && Permissions.has(player.getPlayer(),
                        "bm.notifications.updates")) mainScoreboards
                .get(player).removeItem("Updates");
        if (config.getBoolean("Scoreboards.PlayerCount")) mainScoreboards.get(
                player).removeItem("Online Player");
        mainScoreboards.remove(player);

    }
}
