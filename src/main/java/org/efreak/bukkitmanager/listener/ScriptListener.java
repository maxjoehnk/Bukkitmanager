package org.efreak.bukkitmanager.listener;

import java.util.Arrays;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.efreak.bukkitmanager.scripting.ScriptManager;
import org.efreak.bukkitmanager.scripting.api.PlayerAPI;

public class ScriptListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ((PlayerAPI) ScriptManager.getAPI().getAPI("player")).addPlayer(event
                .getPlayer());
        ScriptManager.runFunction("onJoin", event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        ((PlayerAPI) ScriptManager.getAPI().getAPI("player")).remPlayer(event
                .getPlayer());
        ScriptManager.runFunction("onKick", event.getPlayer(),
                event.getReason());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ((PlayerAPI) ScriptManager.getAPI().getAPI("player")).remPlayer(event
                .getPlayer());
        ScriptManager.runFunction("onQuit", event.getPlayer());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        ScriptManager.runFunction("onCommand", event.getPlayer(),
                event.getMessage());
    }

    @EventHandler
    public void customCommand(PlayerCommandPreprocessEvent event) {
        String[] cmd = event.getMessage().split(" ");
        ScriptManager.getAPI().runCommand(event.getPlayer(), cmd[0],
                Arrays.copyOfRange(cmd, 1, cmd.length));
    }
}
