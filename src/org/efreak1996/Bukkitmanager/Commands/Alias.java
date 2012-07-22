package org.efreak1996.Bukkitmanager.Commands;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.plugin.Plugin;

import org.efreak1996.Bukkitmanager.Bukkitmanager;
import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.IOManager;

public class Alias extends org.bukkit.command.Command implements PluginIdentifiableCommand {

	protected static IOManager io;
	protected static Plugin plugin;
	protected static Configuration config;
	protected HashMap<String, Command> commands;
	
	protected Alias(String name, String desc) {
		super(name);
		io = new IOManager();
		plugin = Bukkitmanager.getInstance();
		config = new Configuration();
		commands = new HashMap<String, Command>();
		config.addAlias(name);
		config.save();
        description = desc;
        usageMessage = "Use /bm help for more Help";
		if (config.getAlias(name)) ((CraftServer) plugin.getServer()).getCommandMap().register(name, this);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) return false;
		else if (commands.containsKey(args[0])) return commands.get(args[0]).execute(sender, args, 0);
		else return false;
	}

	public void registerCommand(Command cmd) {
		commands.put(cmd.getLabel(), cmd);
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}
}
