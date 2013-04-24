package org.efreak.bukkitmanager.addons;

import org.bukkit.plugin.java.JavaPlugin;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

public class BukkitmanagerAddon extends JavaPlugin {

    protected IOManager io;
    protected Configuration config;
    protected BukkitmanagerAddon instance;
    protected String name = "";

    @Override
    public void onLoad() {
        instance = this;
        Bukkitmanager.addAddon(this);
    }

    @Override
    public void onEnable() {
        io = Bukkitmanager.getIOManager();
        config = Bukkitmanager.getConfiguration();
        io.sendConsole("Loading Addon: " + name);
    }

    public String getAddonName() {
        return name;
    }
}
