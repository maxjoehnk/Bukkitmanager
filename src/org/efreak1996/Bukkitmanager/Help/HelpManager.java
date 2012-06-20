package org.efreak1996.Bukkitmanager.Help;

import java.util.ArrayList;
import java.util.List;

public class HelpManager {

	private static List<HelpTopic> helpTopics;
	
	public static void initialize() {
		helpTopics = new ArrayList<HelpTopic>();
	}
	
	public static void registerTopic(HelpTopic topic) {
		helpTopics.add(topic);
	}
	
	public static void registerTopics(List<HelpTopic> topics) {
		helpTopics.addAll(topics);
	}
	
	public static boolean unregisterTopic(HelpTopic topic) {
		for (int i = 0; i < helpTopics.size(); i++) {
			if (helpTopics.get(i).equals(topic)) {
				helpTopics.remove(i);
				return true;
			}
		}		
		return false;
	}
	
	public static HelpTopic getTopic(int i) {
		return helpTopics.get(i);
	}
	
	public static List<HelpTopic> getTopics() {
		return helpTopics;
	}	
}
