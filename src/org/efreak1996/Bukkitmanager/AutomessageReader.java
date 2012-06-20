package org.efreak1996.Bukkitmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.plugin.Plugin;


public class AutomessageReader {
	
	private static Configuration config;
	private static IOManager io;
	private static Plugin plugin;
	public static String prefix;
	private static File messageFile;
	public static List<String> messages = new ArrayList<String>();
	public static int lastDisplayed = -1;
	private static boolean random;
	private static List<Integer> displayed = new ArrayList<Integer>();

	public void initialize() {
		plugin = BmPlugin.getPlugin();
		io = new IOManager();
		config = new Configuration();
		prefix = config.getString("Automessage.Prefix") + " ";
		messageFile = new File(plugin.getDataFolder() + File.separator + "automessage.txt");
		loadMessageFile();
		random = config.getBoolean("Automessage.Random");
	}
	
	public void sendMsg() {
		if (messages == null) return;
		if (messages.isEmpty()) return;
		if (random) {
			if (messages.size() > 1) {
				if (displayed.size() == messages.size()) displayed = new ArrayList<Integer>();
				boolean found = false;
				while (!found) {
					Random rnd = new Random();
					lastDisplayed = rnd.nextInt(messages.size());
					if (!(displayed.contains(lastDisplayed))) {
						displayed.add(lastDisplayed);
						found = true;
					}
				}
				io.broadcast(false, prefix + messages.get(lastDisplayed));
			} else io.broadcast(false, prefix + messages.get(0));
			
		}else {
			if (!(messages.size() == lastDisplayed + 1)) {
				lastDisplayed++;
				io.broadcast(false, prefix + messages.get(lastDisplayed));
			}else {
				lastDisplayed = 0;
				io.broadcast(false, prefix + messages.get(lastDisplayed));
			}
		}
	}
	
	public void loadMessageFile() {
		try {
			if (!messageFile.exists()) messageFile.createNewFile();
		    Scanner scanner = new Scanner(new FileInputStream(messageFile), "UTF-8");
		    try {
		      while (scanner.hasNextLine())
		          messages.add(scanner.nextLine());
		    }finally {
		      scanner.close();
		    }
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}	
	}
	
	public int addMessage(String content) {
		messages.add(content);
		return messages.size() - 1;
	}	

	public int addMessage(String[] content) {
		String add = null;
		for (int i = 0; i < content.length; i++) {
			if (add != null) add = add + " " + content[i];
			else add = content[i];
		}
		messages.add(add);
		return messages.size() - 1;
	}
	
	public String getMessage(int index) {
		if (index > messages.size()) return null;
		else return messages.get(index);
	}
	
	public String remMessage(int index) {
		if (index > messages.size()) return null;
		else return messages.remove(index);
	}
	
	public List<String> listMessages() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < messages.size(); i++) {
			list.add(i + ": " + messages.get(i));
		}
		return list;
	}
	
	public void sendMessage(int index, boolean perms) {
		if (!(index > messages.size())) {
			if (perms) io.broadcast(false, prefix + messages.get(index), "bm.automessage");
			else io.broadcast(false, prefix + messages.get(index));
		}
	}
}
