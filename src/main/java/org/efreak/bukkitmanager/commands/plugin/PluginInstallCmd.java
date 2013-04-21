package org.efreak.bukkitmanager.commands.plugin;

import java.io.File;
import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.pluginmanager.updater.FilePage;
import org.efreak.bukkitmanager.pluginmanager.updater.PluginPage;

public class PluginInstallCmd extends Command {

	public PluginInstallCmd() {
		super("install", "Plugin.Install", "bm.plugin.install", Arrays.asList("(plugin)"), CommandCategory.PLUGIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm plugin install (plugin)");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm plugin install (plugin)");
		else {
			PluginPage pluginPage = new PluginPage(args[1 + length]);
			if (pluginPage.exists()) io.createConversation(sender, "PluginInstallRequest", new InstallPluginPrompt(args[1 + length]));
			else io.sendError(sender, "Can't find Plugin " + args[1 + length]);
		}
		return true;
	}

}

class InstallPluginPrompt extends BooleanPrompt {

	String pluginName;
	
	public InstallPluginPrompt(String pluginName) {
		super();
		this.pluginName = pluginName;
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return "Should the newest Version of " + pluginName + " downloaded? (y/n)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean value) {
		if (value) {
			PluginPage pluginPage = null;
			if (Bukkitmanager.getConfiguration().getString("PluginUpdater.System").equalsIgnoreCase("DevBukkit")) {
				pluginPage = new PluginPage(pluginName);
			}
			FilePage filePage = pluginPage.getNewestFile();
			File file = filePage.download();
			return new LoadPluginPrompt(this.pluginName, file);
		}
		return Prompt.END_OF_CONVERSATION;
	}
	
}

class LoadPluginPrompt extends BooleanPrompt {

	String pluginName;
	File file;
	Configuration config;
	IOManager io;
	
	public LoadPluginPrompt(String pluginName, File file) {
		super();
		this.pluginName = pluginName;
		this.file = file;
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return "Should " + pluginName + " get loaded? (y/n)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean value) {
		if (value) {
			Plugin plugin;
			try {
				plugin = PluginManager.loadPlugin(this.file);
				PluginManager.enablePlugin(plugin);
			} catch (UnknownDependencyException e) {
				io.sendConversable(context.getForWhom(), "Plugin.Load.Error.UnknownDependency");
				if (config.getDebug()) e.printStackTrace();
			} catch (InvalidPluginException e) {
				io.sendConversable(context.getForWhom(), "Plugin.Load.Error.InvalidPlugin");
				if (config.getDebug()) e.printStackTrace();
			} catch (InvalidDescriptionException e) {
				io.sendConversable(context.getForWhom(), "Plugin.Load.Error.InvalidDescription");
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return Prompt.END_OF_CONVERSATION;
	}
	
}