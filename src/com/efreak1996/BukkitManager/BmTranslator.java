package com.efreak1996.BukkitManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.efreak1996.BukkitManager.Language.Language;

public class BmTranslator {

	private static HashMap<String, YamlConfiguration> languages;
	private static BmConfiguration config;
	private static String language;
	
	public void initialize() {
		languages = new HashMap<String, YamlConfiguration>();
		config = new BmConfiguration();
		language = config.getString("IO.Language", "en_US");
		File[] langFiles = new File(BmPlugin.getPlugin().getDataFolder() + File.separator + "lang").listFiles();
		List<Language> langs = Language.getLanguages();
		List<File> loadedFiles = new ArrayList<File>();
		for (int i = 0; i < langs.size(); i++) {
			langs.get(i).createLanguageFile();
			langs.get(i).updateLanguage();
			loadedFiles.add(langs.get(i).getFile());
			languages.put(langs.get(i).getName(), langs.get(i).getKeys());
		}
		for (int i = 0; i < langFiles.length; i++) {
			boolean isLoaded = false;
			for (int i2 = 0; i2 < loadedFiles.size(); i2++) {
				if (loadedFiles.get(i2).equals(langFiles[i])) isLoaded = true;
			}
			if (!isLoaded) loadLanguageFile(langFiles[i]);
		}			
	}
	
	private void loadLanguageFile(File languageFile) {
		YamlConfiguration lang = new YamlConfiguration();
		try {
			lang.load(languageFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		languages.put(languageFile.getName().split(".lang")[0], lang);
	}
	
	public String getLanguage() {
		return language;
	}
	
	public boolean setLanguage(String name) {
		if (languages.containsKey(name)) {
			language = name;
			config.set("IO.Language", name);
			return true;
		}else return false;
	}
	
	public HashMap<String, YamlConfiguration> getLanguages() {
		return languages;
	}
	
	public String getKey(String key) {
		return languages.get(language).getString(key);
	}
}
