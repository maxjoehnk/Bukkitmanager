package org.efreak.bukkitmanager.commands.plugin;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;

import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;

public class PluginUpdateCmd extends Command{
	
	public PluginUpdateCmd() {
		super("update", "Plugin.Update", "bm.plugin.update", Arrays.asList("[plugin|all]"), CommandCategory.PLUGIN);
	}
		
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin update [plugin|all]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm plugin update [plugin|all]");
		else {
			if (args.length == 1) {
				Plugin[] plugins = PluginManager.getPlugins();
				StringBuilder names = new StringBuilder();
				int updateCount = 0;
				for (int i = 0; i < plugins.length; i++) {
					if (!PluginManager.checkPlugin(plugins[i])) {
						if (names.length() > 0) names.append(", ");
						names.append(plugins[i].getDescription().getName());
						updateCount++;
					}
				}
				io.send(sender, io.translate("Command.Plugin.Update.UpdatesAvailable").replaceAll("%count%", String.valueOf(updateCount)).replaceAll("%names%", names.toString()));
			}else if (args[1].equalsIgnoreCase("all")) {
				PluginManager.updatePlugins();
			}else {
				Plugin plugin = PluginManager.getPluginIgnoreCase(args[1]);
				if (plugin != null) {
					boolean uptodate = PluginManager.checkPlugin(plugin);
					if (uptodate) io.send(sender, io.translate("PluginUpdater.UpToDate").replaceAll("%plugin%", plugin.getName()));
					else  {
						io.send(sender, io.translate("PluginUpdater.NotUpToDate").replaceAll("%plugin%", plugin.getName()));
						io.createConversation(sender, "PluginUpdaterRequest", new UpdatePluginPrompt(plugin.getName()));
					}
				}
			}
		}
		return true;
	}
}

class UpdatePluginPrompt extends BooleanPrompt {

	String pluginName;
	
	public UpdatePluginPrompt(String pluginName) {
		super();
		this.pluginName = pluginName;
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return "Should the newest Version of " + pluginName + " downloaded? (yes/no)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, boolean value) {
		if (value) {
			PluginManager.updatePlugin(PluginManager.getPluginIgnoreCase(pluginName));
		}
		return Prompt.END_OF_CONVERSATION;
	}
	
}
