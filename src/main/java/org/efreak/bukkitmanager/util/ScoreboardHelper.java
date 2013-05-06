package org.efreak.bukkitmanager.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
public class ScoreboardHelper {
		
	private static HashMap<String, Scoreboard> scoreboards;
	
	static {
		scoreboards = new HashMap<String, Scoreboard>();
	}
	
	public Scoreboard registerScoreboard(String id) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		scoreboards.put(id, sb);
		return sb;
	}
	
	public Objective registerObjective(String sbId, String name, String criteria) {
		return registerObjective(scoreboards.get(sbId), name, criteria);
	}
	
	public Objective registerObjective(Scoreboard sb, String name, String criteria) {
		return sb.registerNewObjective(name, criteria);
	}
	
	public void setDisplaySlot(Scoreboard sb, DisplaySlot slot) {
		for (Objective objective : sb.getObjectives()) setDisplaySlot(objective, slot);
	}
	
	public void setDisplaySlot(String sbId, DisplaySlot slot) {
		setDisplaySlot(scoreboards.get(sbId), slot);
	}
	
	public void setDisplaySlot(Scoreboard sb, String objectiveName, DisplaySlot slot) {
		sb.getObjective(objectiveName).setDisplaySlot(slot);
	}
	
	public void setDisplaySlot(Objective objective, DisplaySlot slot) {
		objective.setDisplaySlot(slot);
	}
}
