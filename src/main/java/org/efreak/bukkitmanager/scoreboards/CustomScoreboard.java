package org.efreak.bukkitmanager.scoreboards;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_5_R2.Packet206SetScoreboardObjective;
import net.minecraft.server.v1_5_R2.Packet207SetScoreboardScore;
import net.minecraft.server.v1_5_R2.Packet208SetScoreboardDisplayObjective;
import net.minecraft.server.v1_5_R2.Scoreboard;
import net.minecraft.server.v1_5_R2.ScoreboardObjective;
import net.minecraft.server.v1_5_R2.ScoreboardScore;

public class CustomScoreboard {

	private Scoreboard scoreboard;
	private Player player;
	private String name;
	
	public CustomScoreboard(String name, Player player) {
		this.name = name;
		this.player = player;
		scoreboard = new Scoreboard();
		scoreboard.b(name, new ScoreboardObjective(scoreboard, name, null));
		Packet206SetScoreboardObjective packet = new Packet206SetScoreboardObjective(scoreboard.b(name), 0);
		Packet208SetScoreboardDisplayObjective display = new Packet208SetScoreboardDisplayObjective(1, scoreboard.b(name));
		ScoreboardManager.sendPacket(player, packet);
		ScoreboardManager.sendPacket(player, display);
	}
		
	public void sendItem(String itemName, int value) {
		ScoreboardScore scoreItem = scoreboard.a(itemName, scoreboard.b(name));
		scoreItem.c(value);
		Packet207SetScoreboardScore packet = new Packet207SetScoreboardScore(scoreItem, 0);
		ScoreboardManager.sendPacket(player, packet);
	}
	
	public void removeItem(String itemName) {
		ScoreboardScore scoreItem = scoreboard.a(itemName, scoreboard.b(name));
		Packet207SetScoreboardScore packet = new Packet207SetScoreboardScore(scoreItem, 0);
		ScoreboardManager.sendPacket(player, packet);
	}
}
