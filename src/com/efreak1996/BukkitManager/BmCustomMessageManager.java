package com.efreak1996.BukkitManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.efreak1996.BukkitManager.Listener.BmCustomMessageListener;
import com.efreak1996.BukkitManager.Util.BmIOManager;

public class BmCustomMessageManager {

	private static BmConfiguration config;
	private static BmCustomMessageListener listener;
	private static BmIOManager io;
	private static List<String> playerJoin;
	private static int playerJoinIndex;
	private static List<Integer> playerJoinDisplayed;
	
	public void initialize() {
		config = new BmConfiguration();
		listener = new BmCustomMessageListener();
		io = new BmIOManager();
		if (config.getBoolean("CustomMessages.Enabled")) {
			io.sendConsole("Enabling Custom Messages...");
			BmPlugin.getPlugin().getServer().getPluginManager().registerEvents(listener, BmPlugin.getPlugin());
		}
		playerJoin = config.getStringList("CustomMessages.Join");
		playerJoinIndex = new Random().nextInt(playerJoin.size());
		playerJoinDisplayed = new ArrayList<Integer>();
	}
	
	public String getPlayerJoin() {
		String text = playerJoin.get(playerJoinIndex);
		playerJoinDisplayed.add(playerJoinIndex);
		while (playerJoinDisplayed.contains(playerJoinIndex)) {
			if (playerJoinDisplayed.size() == playerJoin.size()) playerJoinDisplayed.clear();
			playerJoinIndex = new Random().nextInt(playerJoin.size());
		}
		return text;
	}

}
