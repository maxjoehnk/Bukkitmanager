package org.efreak.bukkitmanager.scripting.api;

import org.bukkit.World;

public class APIWorld {

    private World world;

    public APIWorld(World world) {
        this.world = world;
    }

    public String getName() {
        return world.getName();
    }
}
