package org.efreak.bukkitmanager.commands;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class Alias extends org.bukkit.command.Command implements PluginIdentifiableCommand {

	protected static IOManager io;
	protected static Plugin plugin;
	protected static Configuration config;
	protected HashMap<String, Command> commands;
	
	static {
		io = Bukkitmanager.getIOManager();
		plugin = Bukkitmanager.getInstance();
		config = Bukkitmanager.getConfiguration();
	}
	
	protected Alias(String name, String desc) {
		super(name);
		commands = new HashMap<String, Command>();
		config.addAlias(name);
		config.save();
		description = desc;
		usageMessage = "Use /bm help for more help";
		if (config.getAlias(name)) {
			if (!PluginManager.registerCommand(name, this))	io.sendConsoleWarning("Error registering Alias " + name);
		}
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) return false;
		else if (commands.containsKey(args[0])) return commands.get(args[0]).execute(sender, args);
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
