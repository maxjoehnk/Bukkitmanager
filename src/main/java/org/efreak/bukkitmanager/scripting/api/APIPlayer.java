package org.efreak.bukkitmanager.scripting.api;

import org.efreak.bukkitmanager.BmPlayer;

public class APIPlayer {

    private BmPlayer player;

    public APIPlayer(BmPlayer player) {
        this.player = player;
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    // TODO: Add more APIPlayer Methods
}
