package org.efreak.bukkitmanager.external;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ExternalConfiguration {

	private static Properties properties;
	
	public void load() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("bukkitmanager.properties")));
			update();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void update() {
		properties.setProperty("", "");
	}
	
}
