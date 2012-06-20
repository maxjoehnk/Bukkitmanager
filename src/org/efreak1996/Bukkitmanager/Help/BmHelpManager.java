package org.efreak1996.Bukkitmanager.Help;

import java.util.ArrayList;
import java.util.List;

public class BmHelpManager {

	private static List<BmHelpTopic> helpTopics;
	
	public static void initialize() {
		helpTopics = new ArrayList<BmHelpTopic>();
	}
	
	public static void registerTopic(BmHelpTopic topic) {
		helpTopics.add(topic);
	}
	
	public static void registerTopics(List<BmHelpTopic> topics) {
		helpTopics.addAll(topics);
	}
	
	public static boolean unregisterTopic(BmHelpTopic topic) {
		for (int i = 0; i < helpTopics.size(); i++) {
			if (helpTopics.get(i).equals(topic)) {
				helpTopics.remove(i);
				return true;
			}
		}		
		return false;
	}
	
	public static BmHelpTopic getTopic(int i) {
		return helpTopics.get(i);
	}
	
	public static List<BmHelpTopic> getTopics() {
		return helpTopics;
	}	
}
