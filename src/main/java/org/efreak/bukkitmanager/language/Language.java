package org.efreak.bukkitmanager.language;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;

public abstract class Language {

    private static List<Language> languages = new ArrayList<Language>();
    protected YamlConfiguration lang;
    protected File langFile;
    protected static Configuration config;

    static {
        languages = new ArrayList<Language>();
        config = Bukkitmanager.getConfiguration();
    }

    public abstract void createLanguageFile();

    public abstract void updateLanguage();

    public String translate(String key) {
        return lang.getString(key);
    }

    public File getFile() {
        return langFile;
    }

    public YamlConfiguration getKeys() {
        return lang;
    }

    public String getName() {
        return "en";
    }

    public void set(String key, String value) {
        if (lang.get(key) == null) lang.set(key, value);
    }

    public static List<Language> getLanguages() {
        languages.add(new de());
        // languages.add(new fr());
        languages.add(new en());
        return languages;
    }

    public static void addKey(String langName, String key, String value) {
        Language lang = getLanguageByName(langName);
        if (lang != null) {
            lang.set(key, value);
            try {
                lang.getKeys().save(lang.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // lang.save();
            languages.set(languages.indexOf(lang), lang);
        }
    }

    public static Language getLanguageByName(String name) {
        for (Language lang : languages) {
            if (lang.getName().equalsIgnoreCase(name)) return lang;
        }
        return null;
    }

    public void save() {
        try {
            if (lang == null) System.out.println("yml = null");
            if (langFile == null) System.out.println("file = null");
            lang.save(langFile);
        } catch (IOException e) {
            if (config.getDebug()) e.printStackTrace();
        }
    }
}
