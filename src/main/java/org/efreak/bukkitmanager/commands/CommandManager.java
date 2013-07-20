package org.efreak.bukkitmanager.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.help.HelpManager;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.util.JaroWinklerDistance;

public class CommandManager implements TabExecutor {

	private static HashMap<String, Method> commands;
	private static IOManager io;
	private static Configuration config;
	
	static {
		commands = new HashMap<String, Method>();
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
	}
	
	public CommandManager() {
		if (Bukkitmanager.firstRun) registerHandler(new InstallCmd()); 
		registerHandler(new AutobackupCmd());
		registerHandler(new AutomessageCmd());
		registerHandler(new AutosaveCmd());
		registerHandler(new BukkitCmd());
		registerHandler(new FilebrowserCmd());
		registerHandler(new ItemCmd());
		registerHandler(new LanguageCmd());
		registerHandler(new PasswordCmd());
		registerHandler(new PlayerCmd());
		registerHandler(new PluginCmd());
		registerHandler(new ServerCmd());
		registerHandler(new WorldCmd());
		registerHandler(new HelpCmd());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		Player p = null;
		ConsoleCommandSender c = null;
		if (sender instanceof Player) p = (Player) sender;
		if (sender instanceof ConsoleCommandSender) c = (ConsoleCommandSender) sender;
		if (p == c) return false;
		if (args.length == 0) new HelpCmd().helpCommand(sender, new String[0]);
		else {
			if (commands.containsKey(args[0])) {
				if (Permissions.has(sender, commands.get(args[0]).getAnnotation(Command.class).permission(), args.toString())) {
					try {
						CommandHandler handler = (CommandHandler) commands.get(args[0]).getDeclaringClass().newInstance();
						commands.get(args[0]).invoke(handler, sender, Arrays.copyOfRange(args, 1, args.length)).toString();
					} catch (IllegalAccessException e) {
						io.sendError(sender, "Error executing command: " + e.getMessage());
						if (config.getDebug()) e.printStackTrace();
					} catch (IllegalArgumentException e) {
						io.sendError(sender, "Error executing command: " + e.getMessage());
						if (config.getDebug()) e.printStackTrace();
					} catch (InvocationTargetException e) {
						io.sendError(sender, "Error executing command: " + e.getMessage());
						if (config.getDebug()) e.printStackTrace();
					} catch (InstantiationException e) {
						io.sendError(sender, "Error executing command: " + e.getMessage());
						if (config.getDebug()) e.printStackTrace();
					}
				}
			}else {
				Entry<Double, String> bestMatch = null;
				try {
					for (String cmdLabel : commands.keySet()) {
						double similarity = JaroWinklerDistance.getSimilarity(cmdLabel, args[0]);
						if (bestMatch == null || similarity > bestMatch.getKey()) bestMatch = new AbstractMap.SimpleEntry<Double, String>(similarity, cmdLabel);
					}
				}catch(Exception e) {}
				if (bestMatch != null) io.send(sender, "Did you mean: " + bestMatch.getValue());
				else io.send(sender, "Unknown Command");
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		if (args.length == 1) {
			for (String command : commands.keySet()) {
				if (command.startsWith(args[0])) tabs.add(command);
			}
		}else if (commands.containsKey(args[0])) {
			try {
			CommandHandler handler = (CommandHandler) commands.get(args[0]).getDeclaringClass().newInstance();
			return handler.onTabComplete(sender, cmd, label, args);
			}catch(Exception e) {
				io.sendConsoleWarning("Error on TabCompletion: " + e.getLocalizedMessage());
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return tabs;
	}

	public static Method getCommand(String label) {
		return commands.get(label);
	}
	
	public static void registerHandler(CommandHandler handler/*final Class<?> class_*/) {
		io.debug("Registering CommandHandler: " + handler.getClass().getName());
		for (final Method method : handler.getClass().getMethods()) {
			if (!method.isAnnotationPresent(Command.class)) continue;
			final Command variable = method.getAnnotation(Command.class);
			commands.put(variable.label(), method);
			HelpManager.registerCommand(method);
			io.debug("Registered Command: " + variable.label());
			if (variable.alias()) {
				config.addAlias(variable.label());
				config.save();
				if (config.getAlias(variable.label())) {
					if (!PluginManager.registerCommand(variable.label(), new Alias(variable))) io.sendConsoleWarning("Error registering Alias " + variable.label());
				}
			}
		}
	}
}
