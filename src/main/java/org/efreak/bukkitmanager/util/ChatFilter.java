package org.efreak.bukkitmanager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

public class ChatFilter {

	private static List<String> filtered;
	private static File filterFile;
	private static Configuration config;
	private static IOManager io;
	
	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public void init() {
		io.sendConsole("Loading Chatfilter...");
		filtered = new ArrayList<String>();
		filterFile = new File(FileHelper.getPluginDir(), config.getString("Chatfilter.File"));
		if (!filterFile.exists()) {
			try {
				filterFile.createNewFile();
			} catch (IOException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new FileInputStream(filterFile));
			while (scanner.hasNext()) filtered.add(scanner.next());
		}catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		io.sendConsole("Chatfilter loaded! " + filtered.size() + " Entrie(s) found");
	}
	
	public static String filter(String msg) {
		String[] msgParts = msg.split(" ");
		StringBuilder output = new StringBuilder();
		for (String part : msgParts) {
			if (filtered.contains(part)) {
				for (int i = 0; i < part.length(); i++) output.append(config.getString("Chatfilter.Replacement"));
			}else output.append(part);
			output.append(" ");
		}
		return output.toString();
	}
}
