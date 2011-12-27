/*
 * MCDocs by Tazzernator 
 * (Andrew Tajsic ~ atajsicDesigns ~ http://atajsic.com)
 * 
 * THIS PLUGIN IS LICENSED UNDER THE WTFPL - (Do What The Fuck You Want To Public License)
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details.
 * 
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *   
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 *   
 * */

package me.efreak1996.BukkitManager.Docs;

import java.util.ArrayList;

public class BmDocsGroups {
	
	protected String groupName;
	protected String groupPrefix;
	protected String groupSuffix;
	protected String playersString;
	protected ArrayList<String> playersList = new ArrayList<String>();

	public BmDocsGroups(String groupName, String groupPrefix, String groupSuffix, String players){
		this.groupName = groupName;
		this.groupPrefix = groupPrefix;
		this.groupSuffix = groupSuffix;
		this.playersString = players;
		
		players = players.replace("[", "");
		players = players.replace("]", "");
		
		String[] split = players.split(", ");
		
		for(String s : split){
			playersList.add(s);
		}
	}
	
	public String getName(){
		return groupName;
	}
	
	public String getPrefix(){
		return groupPrefix;
	}
	
	public String getSuffix(){
		return groupSuffix;
	}
	
	public String getPlayersString(){
		return playersString;
	}
	
	public ArrayList<String> getPlayers(){
		return playersList;
	}
	
	
}
