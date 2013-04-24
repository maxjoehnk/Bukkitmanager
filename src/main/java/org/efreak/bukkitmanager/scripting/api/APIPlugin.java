package org.efreak.bukkitmanager.scripting.api;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class APIPlugin {

    private Plugin plugin;

    private static Configuration config;

    static {
        config = Bukkitmanager.getConfiguration();
    }

    public APIPlugin(Plugin arg1Plugin) {
        plugin = arg1Plugin;
    }

    public void disable() {
        PluginManager.disablePlugin(plugin);
    }

    public void enable() {
        PluginManager.enablePlugin(plugin);
    }

    public void restart() {
        PluginManager.restartPlugin(plugin);
    }

    public void reload() {
        try {
            PluginManager.reloadPlugin(plugin);
        } catch (UnknownDependencyException e) {
            if (config.getDebug()) e.printStackTrace();
        } catch (InvalidPluginException e) {
            if (config.getDebug()) e.printStackTrace();
        } catch (InvalidDescriptionException e) {
            if (config.getDebug()) e.printStackTrace();
        }
    }

    public boolean isEnabled() {
        return PluginManager.isPluginEnabled(plugin);
    }

    // TODO: Add more APIPlugin Methods
}
