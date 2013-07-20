package org.efreak.bukkitmanager.external;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static Properties properties;
	private static File propertiesFile;
	
	public Configuration() {
		propertiesFile = new File("bukkitmanager.properties");
		if (!propertiesFile.exists())
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public Configuration(String fileName) {
		propertiesFile = new File(fileName);
		if (!propertiesFile.exists())
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}
	
	public void load() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
			update();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void update() {
		update("Plugin.Directory", "../");
	}
	
	private void update(String key, String value) {
		if (!properties.contains(key)) properties.setProperty(key, value);
	}
}
