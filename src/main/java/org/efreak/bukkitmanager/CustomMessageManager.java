package org.efreak.bukkitmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.efreak.bukkitmanager.listener.CustomMessageListener;


public class CustomMessageManager {

	private static Configuration config;
	private static CustomMessageListener listener;
	private static IOManager io;
	
	private static List<String> playerJoin;
	private static int playerJoinIndex;
	private static List<Integer> playerJoinDisplayed;
	
	private static List<String> playerLeave;
	private static int playerLeaveIndex;
	private static List<Integer> playerLeaveDisplayed;
	
	private static List<String> playerKick;
	private static int playerKickIndex;
	private static List<Integer> playerKickDisplayed;

	private static List<String> unknownCommand;
	private static int unknownCommandIndex;
	private static List<Integer> unknownCommandDisplayed;

	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public void init() {
		listener = new CustomMessageListener();
		if (config.getBoolean("CustomMessages.Enabled")) {
			io.sendConsole(io.translate("CustomMessages.Loading"));
			Bukkit.getServer().getPluginManager().registerEvents(listener, Bukkitmanager.getInstance());
			
			playerJoin = config.getStringList("CustomMessages.Join");
			playerJoinIndex = new Random().nextInt(playerJoin.size());
			playerJoinDisplayed = new ArrayList<Integer>();
			
			playerLeave = config.getStringList("CustomMessages.Leave");
			playerLeaveIndex = new Random().nextInt(playerLeave.size());
			playerLeaveDisplayed = new ArrayList<Integer>();
			
			playerKick = config.getStringList("CustomMessages.Kick");
			playerKickIndex = new Random().nextInt(playerKick.size());
			playerKickDisplayed = new ArrayList<Integer>();

			unknownCommand = config.getStringList("CustomMessages.UnknownCommand");
			unknownCommandIndex = new Random().nextInt(playerKick.size());
			unknownCommandDisplayed = new ArrayList<Integer>();

			io.sendConsole(io.translate("CustomMessages.Loaded").replaceAll("%msgCount%", String.valueOf(playerJoin.size() + playerLeave.size() + playerKick.size() + unknownCommand.size())));
		}
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

	public String getPlayerLeave() {
		String text = playerLeave.get(playerLeaveIndex);
		playerLeaveDisplayed.add(playerLeaveIndex);
		while (playerLeaveDisplayed.contains(playerLeaveIndex)) {
			if (playerLeaveDisplayed.size() == playerLeave.size()) playerLeaveDisplayed.clear();
			playerLeaveIndex = new Random().nextInt(playerLeave.size());
		}
		return text;
	}

	public String getPlayerKick() {
		String text = playerKick.get(playerKickIndex);
		playerKickDisplayed.add(playerKickIndex);
		while (playerKickDisplayed.contains(playerKickIndex)) {
			if (playerKickDisplayed.size() == playerKick.size()) playerKickDisplayed.clear();
			playerKickIndex = new Random().nextInt(playerKick.size());
		}
		return text;
	}
	
	public String getUnknownCommand() {
		String text = unknownCommand.get(unknownCommandIndex);
		unknownCommandDisplayed.add(unknownCommandIndex);
		while (unknownCommandDisplayed.contains(unknownCommandIndex)) {
			if (unknownCommandDisplayed.size() == unknownCommand.size()) unknownCommandDisplayed.clear();
			unknownCommandIndex = new Random().nextInt(unknownCommand.size());
		}
		return text;
	}

}
