package org.efreak.bukkitmanager.pluginmanager.updater;

import org.bukkit.plugin.Plugin;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FilesPage {

    public static Configuration config;
    protected String pluginName;
    protected String url;
    protected Plugin plugin;
    protected Document doc;
    protected Element body;

    static {
        config = Bukkitmanager.getConfiguration();
    }
}
