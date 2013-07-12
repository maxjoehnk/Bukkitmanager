package org.efreak.bukkitmanager.help;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.SubCommand;

public class HelpManager {

	private static List<HelpTopic> helpTopics;
	private static HelpFile helpFile;

	private static IOManager io;
	
	static {
		io = Bukkitmanager.getIOManager();
		helpTopics = new ArrayList<HelpTopic>();
		helpFile = new HelpFile();
		helpFile.initialize();
	}
	
	public static void registerCommand(Method method) {
		if (method.getAnnotation(Command.class) != null) {
			if (!method.getAnnotation(Command.class).hideHelp())
				registerTopic(new HelpTopic(method.getAnnotation(Command.class).usage(), helpFile.getHelp(method.getAnnotation(Command.class).helpNode()), method.getAnnotation(Command.class).permission()));
		}else {
			if (!method.getAnnotation(SubCommand.class).hideHelp())
				registerTopic(new HelpTopic(method.getAnnotation(SubCommand.class).usage(), helpFile.getHelp(method.getAnnotation(SubCommand.class).helpNode()), method.getAnnotation(SubCommand.class).permission()));
		}
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
	
	public static String getHelp(String key) {
		return helpFile.getHelp(key);
	}
}
