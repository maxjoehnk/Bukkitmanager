package org.efreak.bukkitmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.efreak.bukkitmanager.util.FileHelper;

/**
 * 
 * The AutomessageReader class reads the automessages.txt file, gets all the messages and allows you to send them, even in a random order, without repeating
 *
 */

public class AutomessageReader {
	
	private static final Configuration config;
	private static final IOManager io;
	private static String prefix;
	private static File messageFile;
	private static final List<String> messages = new ArrayList<String>();
	private static final List<Integer> displayed = new ArrayList<Integer>();
	private static int lastDisplayed = -1;
	private static boolean random;

	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
	}
	
	/**
	 * 
	 * Load the message File and the options
	 * 
	 */
	
	public void init() {
		prefix = config.getString("Automessage.Prefix") + " ";
		messageFile = new File(FileHelper.getPluginDir(), config.getString("Automessage.File"));
		loadMessageFile();
		random = config.getBoolean("Automessage.Random");
	}
	
	/**
	 * 
	 * Send the next message
	 * 
	 */
	
	public void sendMsg() {
		if (messages == null) return;
		if (messages.isEmpty()) return;
		if (random) {
			if (messages.size() > 1) {
				if (displayed.size() == messages.size()) displayed.clear();
				boolean found = false;
				while (!found) {
					Random rnd = new Random();
					lastDisplayed = rnd.nextInt(messages.size());
					if (!(displayed.contains(lastDisplayed))) {
						displayed.add(lastDisplayed);
						found = true;
					}
				}
				io.broadcast(prefix + messages.get(lastDisplayed), false);
			} else io.broadcast(prefix + messages.get(0), false);
			
		}else {
			if (!(messages.size() == lastDisplayed + 1)) {
				lastDisplayed++;
				io.broadcast( prefix + messages.get(lastDisplayed), false);
			}else {
				lastDisplayed = 0;
				io.broadcast( prefix + messages.get(lastDisplayed), false);
			}
		}
	}
	
	/**
	 * 
	 * Load the content of the message File into the List
	 * 
	 */
	
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
	
	/**
	 * 
	 * Add a Message to the Automessage List
	 * 
	 * @param content The Message
	 * @return The Index of this Message
	 */
	
	public int addMessage(String content) {
		messages.add(content);
		return messages.size() - 1;
	}
	
	/**
	 * 
	 * Add a Message to the Automessage List
	 * 
	 * @param content The Message divided into Array Entries (One Entry = One Word)
	 * @return The Index of this Message
	 */

	public int addMessage(String[] content) {
		String add = null;
		for (int i = 0; i < content.length; i++) {
			if (add != null) add = add + " " + content[i];
			else add = content[i];
		}
		messages.add(add);
		return messages.size() - 1;
	}
	
	/**
	 * 
	 * @param index The Index of the Message
	 * @return The Message
	 * 
	 */
	
	public String getMessage(int index) {
		if (index > messages.size()) return null;
		else return messages.get(index);
	}
	
	/**
	 * 
	 * @param index The Index of the Message
	 * @return The Content of the Message which was deleted
	 * 
	 */
	
	public String remMessage(int index) {
		if (index > messages.size()) return null;
		else return messages.remove(index);
	}
	
	/**
	 * 
	 * @return A List of all Messages
	 * 
	 */
	
	public List<String> listMessages() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < messages.size(); i++) {
			list.add(i + ": " + messages.get(i));
		}
		return list;
	}
	
	/**
	 * 
	 * Brodcast the Message which is at the specified index
	 * 
	 * @param index The Index of the Messsage
	 * @param perms Enable/Disable Broadcasting to people with the "bm.automessage" Permission
	 * 
	 */
	
	public void sendMessage(int index, boolean perms) {
		if (!(index > messages.size())) {
			if (perms) io.broadcast(prefix + messages.get(index), "bm.automessage", false);
			else io.broadcast(prefix + messages.get(index), false);
		}
	}
	
	public void setPrefix(String arg1Prefix) {
		prefix = arg1Prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
}
