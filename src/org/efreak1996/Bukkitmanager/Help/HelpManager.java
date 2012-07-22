package org.efreak1996.Bukkitmanager.Help;

import java.util.ArrayList;
import java.util.List;

import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class HelpManager {

	private static List<HelpTopic> helpTopics;
	private static HelpFile helpFile;

	public void initialize() {
		helpTopics = new ArrayList<HelpTopic>();
		helpFile = new HelpFile();
		helpFile.initialize();
	}
	
	public static void registerCommand(Command arg1Cmd) {
		String cmd = "/bm ";
		if (arg1Cmd.getCategory().equals(CommandCategory.GENERAL)) cmd += arg1Cmd.getLabel();
		else cmd += arg1Cmd.getCategory().toString().toLowerCase() + " " + arg1Cmd.getLabel();
		String args;
		if (arg1Cmd.getArgs().size() != 0) {
			args = arg1Cmd.getArgs().get(0);
			for (int i = 1; i < arg1Cmd.getArgs().size(); i++) args += " " + arg1Cmd.getArgs().get(i);
		}else args = "";
		registerTopic(new HelpTopic(cmd, args, helpFile.getHelp(arg1Cmd.getHelpNode()), arg1Cmd.getHelpPermission()));
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
