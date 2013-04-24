package org.efreak.bukkitmanager.pluginmanager.updater;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PluginPage {

    public static Configuration config;
    public static IOManager io;
    protected Category category;
    protected DevelopmentStage stage;
    protected String url;
    protected String pluginName;
    protected Document doc;
    protected Element body;
    protected FilePage newestFile = null;
    protected List<FilePage> files;
    protected boolean exists = true;

    static {
        io = Bukkitmanager.getIOManager();
        config = Bukkitmanager.getConfiguration();
    }

    public PluginPage(String arg1PluginName) {
        files = new ArrayList<FilePage>();
        pluginName = arg1PluginName;
        url = "http://dev.bukkit.org/server-mods/" + arg1PluginName;
        try {
            doc = Jsoup.connect(url).get();
            body = doc.body();
            exists = true;
        } catch (SocketTimeoutException e) {
            io.sendConsoleError("Error connecting to BukkitDev.");
            if (config.getDebug()) e.printStackTrace();
            exists = false;
        } catch (IOException e) {
            if (config.getDebug()) e.printStackTrace();
            exists = false;
        }
    }

    public FilePage getNewestFile() {
        if (exists) {
            if (newestFile != null) return newestFile;
            else {
                newestFile = new FilePage("http://dev.bukkit.org"
                        + body.select("li.user-action-download").first()
                                .select("a[href]").first().attributes()
                                .get("href"));
                return newestFile;
            }
        } else return null;
    }

    public boolean exists() {
        return exists;
    }
}
