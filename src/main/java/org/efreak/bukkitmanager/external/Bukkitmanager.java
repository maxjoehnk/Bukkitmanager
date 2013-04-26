package org.efreak.bukkitmanager.external;

import java.util.Arrays;
import java.util.List;

public class Bukkitmanager {

	private static Configuration config;
	
	public static void main(String[] args) {
		List<String> argList = Arrays.asList(args);
		if (argList.contains("--external")) {
			if (argList.contains("--config") || argList.contains("-c")) {
				int index = argList.indexOf("--config");
				if (index == -1) index = argList.indexOf("-c");
				config = new Configuration(argList.get(index + 1));
			}else config = new Configuration();
			config.load();
			if (argList.contains("--timer") || argList.contains("-t")); //TODO: Implement Timer
			if (argList.contains("--start-server")); //TODO: Implement external server starting
			if (argList.contains("--stop-server")); //TODO: Implement external server stopping
			if (argList.contains("--restart-server")); //TODO: Implement external server restarting
			if (argList.contains("--start-remote")); //TODO: Implement remote server starting
			if (argList.contains("--stop-remote")); //TODO: Implement remote server stopping
			if (argList.contains("--restart-remote")); //TODO: Implement remote server restarting
		}else if (argList.contains("--help") || argList.contains("-h") || argList.contains("-?")) printHelp();
		else System.err.println("Argument Error");
	}

	private static void printHelp() {
		System.out.println("Usage: java -jar bukkitmanager.jar <option> ...");
		System.out.println();
		System.out.println("Commands:");
		System.out.println("  -c|--config <file>				Specify the config file to load.");
		System.out.println("  -t|--timer <delay>				Run the actions with a certain delay");
		System.out.println("  --start-server [<id>]				Start the Minecraft Server");
		System.out.println("  --stop-server	[<id>]				Stop the Minecraft Server");
		System.out.println("  --restart-server [<id>]			Restart the Minecraft Server");
		System.out.println("  --start-remote [<port> <id>]		Start the Remoteserver");
		System.out.println("  --stop-remote [<id>]				Stop the Remoteserver");
		System.out.println("  --restart-remote [<port> <id>]	Restart the Remoteserver");
	}
	
}
