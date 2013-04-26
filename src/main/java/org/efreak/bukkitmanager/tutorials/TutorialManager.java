package org.efreak.bukkitmanager.tutorials;

import java.io.File;
import java.util.HashMap;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.util.FileFilter;
import org.efreak.bukkitmanager.util.FileHelper;

public class TutorialManager {
	
	private static Configuration config;
	private static IOManager io;
	private static HashMap<String, Tutorial> tutorials;
	
	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public void init() {
		if (config.getBoolean("Tutorials.Enabled")) {
			io.sendConsole("Loading Tutorials...");
			int i;
			File[] files = FileHelper.getTutorialsDir().listFiles(new FileFilter(".tutorial"));
			for (i = 0; i < files.length; i++) loadTutorial(files[i]);
			io.sendConsole("%count% Tutorials loaded!".replaceAll("%count%", String.valueOf(i)));
		}
	}
	
	public Tutorial loadTutorial(File file) {
		Tutorial tutorial = new Tutorial(file);
		tutorials.put(tutorial.getName(), tutorial);
		return tutorial;
	}
}