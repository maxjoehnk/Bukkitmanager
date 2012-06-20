package org.efreak1996.Bukkitmanager.Language;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class Language {

	public abstract void createLanguageFile();
	
	public abstract void updateLanguage();
	
	public abstract String translate(String key);
	
	public abstract File getFile();
	
	public abstract YamlConfiguration getKeys();
	
	public abstract String getName();
	
	public static List<Language> getLanguages() {
		List<Language> languages = new ArrayList<Language>();
		languages.add(new English());
		//languages.add(new Deutsch());
		return languages;
	}
	
	public abstract void set(String key, String value);
}
