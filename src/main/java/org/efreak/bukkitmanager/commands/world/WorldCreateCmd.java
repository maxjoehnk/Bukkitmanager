package org.efreak.bukkitmanager.commands.world;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class WorldCreateCmd extends Command {
	
	public WorldCreateCmd() {
		super("create", "World.Create", "bm.world.create", Arrays.asList("(name)", "[-e env]", "[-t type]", "[-s seed]"), CommandCategory.WORLD);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm world create (name) [-e environment] [-t type] [-s seed]");
		else if (args.length > 8) io.sendManyArgs(sender, "/bm world create (name) [-e env] [-t type] [-s seed]");
		else {
			WorldCreator creator = new WorldCreator(args[1]);
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
			io.send(sender, "World " + args[1] + " successfully created");
		}
		return true;
	}
}
