package org.efreak.bukkitmanager.external;

import java.util.Arrays;
import java.util.List;

public class ArgumentParser {

	public static void parse(String[] args) {
		List<String> argList = Arrays.asList(args);
		if (argList.contains("--external")) {
			if (argList.contains("--task")) {
				//if (argList.contains("--timer")) new Timer(Integer.valueOf(argList.get(argList.indexOf("--timer") + 1)));
				String task = argList.get(argList.indexOf("--task") + 1);
				ExternalConfiguration config = new ExternalConfiguration();
				config.load();
				if (task.equalsIgnoreCase("restart")) {
					//TODO: Implement restarting
				}
			}else System.err.println("Need a task...");
		}else if (argList.contains("--help") || argList.contains("-h") || argList.contains("-?")) {
			//TODO: Implement external help
		}else System.err.println("Argument Error");
	}
}
