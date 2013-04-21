package org.efreak.bukkitmanager.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.language.Language;

public class Translator {

	private static HashMap<String, YamlConfiguration> languages;
	private static Configuration config;
	private static String language;
	private static IOManager io;
	
	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public void initialize() {
		languages = new HashMap<String, YamlConfiguration>();
		language = config.getString("IO.Language", "en");
		List<Language> langs = Language.getLanguages();
		List<File> loadedFiles = new ArrayList<File>();
		for (int i = 0; i < langs.size(); i++) {
			langs.get(i).createLanguageFile();
			langs.get(i).updateLanguage();
			loadedFiles.add(langs.get(i).getFile());
			languages.put(langs.get(i).getName(), langs.get(i).getKeys());
		}
		File[] langFiles = new File(Bukkitmanager.getInstance().getDataFolder(), "lang").listFiles();
		for (int i = 0; i < langFiles.length; i++) {
			if (loadedFiles.indexOf(langFiles[i]) == -1)  loadLanguageFile(langFiles[i]);
		}
		if (languages.get(language) == null) {
			io.sendConsoleError("Language " + language + " doesn't seem to exist. Forcing to en!");
			language = "en";
			config.set("IO.Language", "en");
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
		if (languages.get(language) == null) return "Language " + language + " doesn't seem to exist. Please change it in the config.yml!";
		if (languages.get(language).getString(key) != null) return languages.get(language).getString(key);
		else return "Key " + key + " couldn't be found. Please check your lang Files!";
	}
}
