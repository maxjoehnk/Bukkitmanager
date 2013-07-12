package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;

public class WorldCmd extends CommandHandler {

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		if (args.length == 1) {
			for (String subCommand : subCommands.keySet()) {
				if (subCommand.startsWith(args[0])) tabs.add(subCommand);
			}
		}else if (subCommands.containsKey(args[0])) {
			try {
				CommandHandler handler = (CommandHandler) subCommands.get(args[0]).getDeclaringClass().newInstance();
				return handler.onTabComplete(sender, cmd, label, args);
			}catch(Exception e) {
				io.sendConsoleWarning("Error on TabCompletion: " + e.getLocalizedMessage());
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return tabs;	}

	@Command(label = "world", helpNode = "World", hideHelp = true, usage = "/world <create|list> [args]")
	public boolean worldCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@Command(label = "worlds", helpNode = "World.List", permission = "bm.world.list", alias = true, hideHelp = true, usage = "worlds [#]")
	@SubCommand(label = "list", helpNode = "World.List", permission = "bm.world.list", usage = "world list [#]")
	public boolean listCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm world list [#]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm world list [#]");
		else {
			List<World> worlds = Bukkit.getWorlds();
			int pages = worlds.size() / 9;
			if (worlds.size() % 9 > 0) pages++;
			if (args.length == 0) {
				io.sendHeader(sender, "WORLDS (1/" + pages + ")");
				for (int i = 0; i < 9 && i < worlds.size(); i++) io.send(sender, worlds.get(i).getName() + ": " + worlds.get(i).getPlayers().size() + " Player(s) online", false);
			}else {
				io.sendHeader(sender, "WORLDS (1/" + pages + ")");
				for (int i = (new Integer(args[0]) - 1) * 9; i < new Integer(args[0]) * 9 && i < worlds.size(); i++) io.send(sender, worlds.get(i).getName() + ": " + worlds.get(i).getPlayers().size() + " Player(s) online", false);
			}
		}
		return true;
	}
	
	@SubCommand(label = "create", helpNode = "World.Create", permission = "bm.world.create", usage = "world create <name> [-e env] [-t type] [-s seed]")
	public boolean createCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm world create (name) [-e environment] [-t type] [-s seed]");
		else if (args.length > 7) io.sendManyArgs(sender, "/bm world create (name) [-e env] [-t type] [-s seed]");
		else {
			WorldCreator creator = new WorldCreator(args[0]);
			List<String> argList = Arrays.asList(args);
			if (argList.contains("-e")) {
				String env = argList.get(argList.indexOf("-e") + 1);
				if (Environment.valueOf(env.toUpperCase()) != null) creator.environment(Environment.valueOf(env.toUpperCase()));
				else io.sendWarning(sender, "Unknown Environment " + env);
			}
			if (argList.contains("-s")) {
				try {
					long seed = Long.valueOf(argList.get(argList.indexOf("-s") + 1));
					creator.seed(seed);
				}catch (Exception e) {
					io.sendWarning(sender, "Unsupported Seed: " + argList.get(argList.indexOf("-s") + 1));
				}
			}
			if (argList.contains("-t")) {
				String type = argList.get(argList.indexOf("-t") + 1);
				if (WorldType.valueOf(type.toUpperCase()) != null) creator.type(WorldType.valueOf(type.toUpperCase()));
				else io.sendWarning(sender, "Unknown World Type " + type);				
			}
			Bukkit.createWorld(creator);
			io.send(sender, "World " + args[0] + " successfully created");
		}
		return true;
	}
}
