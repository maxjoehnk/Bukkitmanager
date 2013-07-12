package org.efreak.bukkitmanager.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Database;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.help.HelpManager;
import org.efreak.bukkitmanager.util.JaroWinklerDistance;

public abstract class CommandHandler {
	
	protected HashMap<String, Method> subCommands;
	protected static IOManager io;
	protected static Configuration config;
	protected static Database db;
	
	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
		db = Bukkitmanager.getDb();
	}
	
	public CommandHandler() {
		subCommands = new HashMap<String, Method>();
		for (final Method method : this.getClass().getMethods()) {
			if (!method.isAnnotationPresent(SubCommand.class)) continue;
			final SubCommand variable = method.getAnnotation(SubCommand.class);
			subCommands.put(variable.label(), method);
			HelpManager.registerCommand(method);
		}
	}
	
	public abstract List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args);
	
	protected void handleSubCommands(CommandSender sender, String[] args) {
		if (subCommands.containsKey(args[0])) {
			if (Permissions.has(sender, subCommands.get(args[0]).getAnnotation(SubCommand.class).permission(), args.toString())) {
				try {
					CommandHandler handler = (CommandHandler) subCommands.get(args[0]).getDeclaringClass().newInstance();
					subCommands.get(args[0]).invoke(handler, sender, args).toString();
				} catch (IllegalAccessException e) {
					io.sendError(sender, "Error executing command: " + e.getLocalizedMessage());
					if (config.getDebug()) e.printStackTrace();
				} catch (IllegalArgumentException e) {
					io.sendError(sender, "Error executing command: " + e.getLocalizedMessage());
					if (config.getDebug()) e.printStackTrace();
				} catch (InvocationTargetException e) {
					io.sendError(sender, "Error executing command: " + e.getLocalizedMessage());
					if (config.getDebug()) e.printStackTrace();
				} catch (InstantiationException e) {
					io.sendError(sender, "Error executing command: " + e.getLocalizedMessage());
					if (config.getDebug()) e.printStackTrace();
				}
			}
		}else {
			Entry<Double, String> bestMatch = null;
			try {
				for (String cmdLabel : subCommands.keySet()) {
					double similarity = JaroWinklerDistance.getSimilarity(cmdLabel, args[0]);
					if (bestMatch == null || similarity > bestMatch.getKey()) bestMatch = new AbstractMap.SimpleEntry<Double, String>(similarity, cmdLabel);
				}
			}catch(Exception e) {}
			if (bestMatch != null) io.send(sender, "Did you mean: " + bestMatch.getValue());
			else io.send(sender, "Unknown Command");
		}
	}
	
	protected void listSubCommands(CommandSender sender) {
		
	}
}
