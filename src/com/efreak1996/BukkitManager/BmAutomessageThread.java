package com.efreak1996.BukkitManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BmAutomessageThread extends Thread {

	private boolean run = true;
	private BmConfiguration config = new BmConfiguration();
	private static BmOutput out = new BmOutput();
	private Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitManager");
	//private boolean random = config.Config.getBoolean("Automessage.Random");
	private String prefix = config.Config.getString("Automessage.Prefix") + " ";
	private File MessageFile = new File(Bukkit.getPluginManager().getPlugin("BukkitManager").getDataFolder() + File.separator + "automessage.txt");
	private ArrayList<String> messages = new ArrayList<String>();
	//private boolean[] displayed;
	private int lastDisplayed = -1;
	//private Random rnd = new Random();
	
	public void setRun(boolean run) {
		this.run = run;
	}
	
	public boolean getRun() {
		return run;
	}

	public void sendMsg() {
		out.sendConsole("Messages: " + messages.size());
		if (messages.size() < lastDisplayed + 1) {
			lastDisplayed++;
			out.sendConsole("Sending Broadcast with index: " + lastDisplayed);
			Bukkit.getServer().broadcastMessage(prefix + messages.get(lastDisplayed));
		}
	}
	
	public void loadMessageFile() {
		try {
			if (!MessageFile.exists()) {
				MessageFile.createNewFile();
			}
		    Scanner scanner = new Scanner(new FileInputStream(MessageFile), "UTF-8");
		    try {
		      while (scanner.hasNextLine())
		          messages.add(scanner.nextLine());
		    }finally {
		      scanner.close();
		    }
		} catch (FileNotFoundException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		} catch (IOException e) {
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
		}	
	}
	
	public int addMessage(String content) {
		messages.add(content);
		return messages.size() - 1;
	}	
	
	public String getMessage(int index) {
		out.sendConsole("Messages: " + messages.size());
		if (index > messages.size()) return null;
		else return messages.get(index);
	}
	
	public String remMessage(int index) {
		if (index > messages.size()) return null;
		else return messages.remove(index);
	}
	
	public void send(int index, boolean perms) {
		if (!(index > messages.size())) {
			if (perms) Bukkit.getServer().broadcast(prefix + messages.get(index), "bm.automessage");
			else Bukkit.getServer().broadcastMessage(prefix + messages.get(index));
		}
	}
	
	public void run() {
		out.sendConsole("AutoMessageThread Started! Interval is " + config.Config.getInt("Automessage.Interval") + " seconds");
		while (run) {
			if(config.Config.getInt("Automessage.Interval") == 0) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {
					if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
				}
				continue;
			}

			for (int i = 0; i < config.Config.getInt("Automessage.Interval"); i++) {
				try {
					if (!run) {
					return;
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
				}
			}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							sendMsg();
							out.sendConsole("Automessage");
						}
					});
		}
	}
}

