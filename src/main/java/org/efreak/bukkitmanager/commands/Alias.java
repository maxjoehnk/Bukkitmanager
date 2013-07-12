package org.efreak.bukkitmanager.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.help.HelpManager;

public class Alias extends org.bukkit.command.Command implements PluginIdentifiableCommand {

	private static IOManager io;
	private static Configuration config;
	
	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
	}
	
	public Alias(Command cmd) {
		super(cmd.label());
		description = HelpManager.getHelp(cmd.helpNode());
		usageMessage = cmd.usage();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		try {
			CommandHandler handler = (CommandHandler) CommandManager.getCommand(label).getDeclaringClass().newInstance();
			CommandManager.getCommand(label).invoke(handler, sender, args).toString();
		}catch(Exception e) {
			io.sendError(sender, "Error executing command: " + e.getLocalizedMessage());
			if (config.getDebug()) e.printStackTrace();
		}
		return true;
	}

	@Override
	public Plugin getPlugin() {
		return Bukkitmanager.getInstance();
	}
}
