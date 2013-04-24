package org.efreak.bukkitmanager.pluginmanager.updater;

import java.io.File;
import java.io.IOException;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.util.Downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class FilePage {

    public static Configuration config;
    protected FileType type;
    protected String url;
    protected Element body;
    protected String fileUrl;
    protected String fileName;
    protected String name;
    protected String gameVersion;

    static {
        config = Bukkitmanager.getConfiguration();
    }

    public FilePage(String arg2Url) {
        url = arg2Url;
        try {
            body = Jsoup.connect(url).get().body();
            fileUrl = body.select("li.user-action-download").first()
                    .select("a[href]").first().attributes().get("href");
            fileName = fileUrl.split("/")[fileUrl.split("/").length - 1];
            name = body.select("div.main-body-inner").first()
                    .select("section.main").first().select("div.size2of3")
                    .first().children().first().text();
            type = FileType.valueOf(body.select("span.file-type").first()
                    .text().toUpperCase());
            gameVersion = body.select("ul.comma-separated-list").first()
                    .select("li").first().text();
        } catch (IOException e) {
            if (config.getDebug()) e.printStackTrace();
        }
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public File download() {
        return Downloader.fetch(false, fileUrl, fileName,
                PluginManager.getUpdateFolder());
    }

    public String getName() {
        return name;
    }

    public FileType getType() {
        return type;
    }

    public String getGameVersion() {
        return gameVersion;
    }
}
