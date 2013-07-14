package org.efreak.bukkitmanager.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.UnknownDependencyException;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.Permissions;
import org.efreak.bukkitmanager.pluginmanager.PluginManager;
import org.efreak.bukkitmanager.pluginmanager.updater.FilePage;
import org.efreak.bukkitmanager.pluginmanager.updater.PluginPage;
import org.efreak.bukkitmanager.util.FileHelper;

public class PluginCmd extends CommandHandler {

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
		return tabs;
	}

	@Command(label = "plugin", helpNode = "Plugin", hideHelp = true, usage = "/plugin <config|disable|enable|info|install|list|load|reload|restart|unload|update>")
	public boolean pluginCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@SubCommand(label = "config", helpNode = "Plugin.Config", permission = "bm.plugin.config", usage = "plugin config (&oplugin) (&oentry|list) [&ovalue]")
	public boolean configCmd(CommandSender sender, String[] args) {
		if (args.length < 2) io.sendFewArgs(sender, "/bm plugin config (plugin) (entry|list) [value]");
		else if (args.length > 3) io.sendManyArgs(sender, "/bm plugin config (plugin) (entry|list) [value]");
		else {
			if (args.length == 2 && args[1].equalsIgnoreCase("list")) {
				if (Permissions.has(sender, "bm.plugin.config.list", "bm plugin config " + args[0] + " list")) {
					if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
						return true;
					}else {
						Plugin plugin = PluginManager.getPluginIgnoreCase(args[0]);
						File pluginFolder = plugin.getDataFolder();
						if (new File(pluginFolder + File.separator + "config.yml").exists()) {
							FileConfiguration pluginConfig = plugin.getConfig();
							try {
								pluginConfig.load(pluginFolder + File.separator + "config.yml");
							} catch (FileNotFoundException e) {
								if (config.getDebug()) e.printStackTrace();
							} catch (IOException e) {
								if (config.getDebug()) e.printStackTrace();
							} catch (InvalidConfigurationException e) {
								if (config.getDebug()) e.printStackTrace();
							}
							/*Object[] keys = pluginConfig.getKeys(true).toArray();
							for (int i = 0; i < keys.length; i++) {
								io.send(sender, keys[i].toString());
							}
							io.send(sender, pluginConfig.saveToString());*/
							Object[] keys = pluginConfig.getKeys(true).toArray();
							StringBuilder output = new StringBuilder();
							output.append(keys[1].toString() + "=" + pluginConfig.get(keys[1].toString()));
							for (int i = 2; i < keys.length; i++) {
								String value = pluginConfig.get(keys[i].toString()).toString();
								if (!value.startsWith("MemorySection")) output.append(", " + keys[i] + "=" + value);
							}
							io.send(sender, io.translate("Command.Plugin.Config.List").replaceAll("%plugin%", plugin.getName()).replaceAll("%items%", output.toString()));
						}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
					}
				}
			}else if (args.length == 2) {
				if (Permissions.has(sender, "bm.plugin.config.get", "bm plugin config " + args[0] + " " + args[1])) {
					if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
						return true;
					}else {
						Plugin plugin = PluginManager.getPluginIgnoreCase(args[0]);
						File pluginFolder = plugin.getDataFolder();
						if (new File(pluginFolder + File.separator + "config.yml").exists()) {
							FileConfiguration pluginConfig = plugin.getConfig();
							try {
								pluginConfig.load(pluginFolder + File.separator + "config.yml");
							} catch (FileNotFoundException e) {
								if (config.getDebug()) e.printStackTrace();
							} catch (IOException e) {
								if (config.getDebug()) e.printStackTrace();
							} catch (InvalidConfigurationException e) {
								if (config.getDebug()) e.printStackTrace();
							}
							if (pluginConfig.get(args[1]) != null) {
								io.send(sender, io.translate("Command.Plugin.Config.Get").replaceAll("%entry%", args[1]).replaceAll("%value%", String.valueOf(pluginConfig.get(args[1]))));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
						}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
					}
				}
			}else if (args.length == 3) {
				if (Permissions.has(sender, "bm.plugin.config.set", "bm plugin config " + args[0] + " " + args[1] + " " + args[2])) {
					if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
						io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
						io.sendError(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
						return true;
					}else {
						Plugin plugin = PluginManager.getPluginIgnoreCase(args[0]);
						File pluginFolder = plugin.getDataFolder();
						if (new File(pluginFolder + File.separator + "config.yml").exists()) {
							FileConfiguration pluginConfig = plugin.getConfig();
							try {
								pluginConfig.load(pluginFolder + File.separator + "config.yml");
							} catch (FileNotFoundException e) {
								if (config.getDebug()) e.printStackTrace();
							} catch (IOException e) {
								if (config.getDebug()) e.printStackTrace();
							} catch (InvalidConfigurationException e) {
								if (config.getDebug()) e.printStackTrace();
							}
							if (pluginConfig.get(args[1]) != null) {
								String old = pluginConfig.get(args[1]).toString();
								pluginConfig.set(args[1], args[2]);
								try {
									pluginConfig.save(pluginFolder + File.separator + "config.yml");
								} catch (IOException e) {
									if (config.getDebug()) e.printStackTrace();
								}
								io.send(sender, io.translate("Command.Plugin.Config.Set").replaceAll("%entry%", args[1]).replaceAll("%value_old%", old).replaceAll("%value_new%", args[2]));
							}else io.sendError(sender, io.translate("Command.Plugin.Config.NoEntry"));
						}else io.sendError(sender, io.translate("Command.Plugin.Config.NoConfig"));
					}
				}
			}
		}
		return true;
	}
	
	@SubCommand(label = "disable", helpNode = "Plugin.Disable", permission = "bm.plugin.disable", usage = "plugin disable (&oplugin|all)")
	public boolean disableCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin disable (&oplugin|all)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin disable (&oplugin|all)");
		else {
			if (args[0].equalsIgnoreCase("all")) {
				PluginManager.disablePlugins();
				io.send(sender, io.translate("Command.Plugin.Disable.Success.All"));
			}else {
				if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
					io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
					io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
				}else {
					if (!PluginManager.getPluginIgnoreCase(args[0]).isEnabled()) io.sendError(sender, io.translate("Command.Plugin.Disable.Already"));
					else {
						if (args[0] == "Spout") {
							io.sendWarning(sender, io.translate("Command.Plugin.Disable.Spout"));
							return true;
						}else {
							PluginManager.disablePlugin(PluginManager.getPluginIgnoreCase(args[0]));
							io.send(sender, io.translate("Command.Plugin.Disable.Success").replaceAll("%plugin%", args[1]));
						}
					}
				}
			}
		}
		return true;
	}
	
	@SubCommand(label = "enable", helpNode = "Plugin.Enable", permission = "bm.plugin.enable", usage = "plugin enable [&oplugin|all]")
	public boolean enableCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin enable (plugin|all)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin enable (plugin|all)");
		else {
			if (args[0].equalsIgnoreCase("all")) {
				PluginManager.enablePlugins();
				io.send(sender, io.translate("Command.Plugin.Enable.Success.All"));
			}else {
				if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
					io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
					io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
				}else {
					if (PluginManager.getPluginIgnoreCase(args[0]).isEnabled()) io.sendError(sender, io.translate("Command.Plugin.Enable.Already"));
					else {
						PluginManager.enablePlugin(PluginManager.getPluginIgnoreCase(args[0]));
						io.send(sender, io.translate("Command.Plugin.Enable.Success").replaceAll("%plugin%", args[0]));
					}
				}
			}
		}
		return true;
	}
	
	@SubCommand(label = "info", helpNode = "Plugin.Info", permission = "bm.plugin.info", usage = "plugin info [&oplugin]")
	public boolean infoCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm plugin info [plugin]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin info [plugin]");
		else {
			if (args.length == 1) {
				if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
					io.sendError(sender, "This Plugin does not exists.");
					return true;
				}
				PluginDescriptionFile pdfFile = PluginManager.getPluginIgnoreCase(args[0]).getDescription();
				io.sendHeader(sender, " Plugin Info ");
				io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
				io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
				io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
				if (pdfFile.getDescription() != null) {
					io.send(sender, ChatColor.RED + "Description:", false);
					io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
				}
			}else if (args.length == 0) {
				PluginDescriptionFile pdfFile = Bukkitmanager.getInstance().getDescription();
				io.sendHeader(sender, " Plugin Info ");
				io.send(sender, ChatColor.RED + "Name:          " + ChatColor.DARK_RED + pdfFile.getName(), false);
				io.send(sender, ChatColor.RED + "Version:       " + ChatColor.DARK_RED + pdfFile.getVersion(), false);
				io.send(sender, ChatColor.RED + "Author(s):     " + ChatColor.DARK_RED + pdfFile.getAuthors(), false);
				io.send(sender, ChatColor.RED + "Description:", false);
				io.send(sender, ChatColor.DARK_RED + pdfFile.getDescription(), false);
			}
		}
		return true;
	}
	
	@SubCommand(label = "install", helpNode = "Plugin.Install", permission = "bm.plugin.install", usage = "plugin install (&oplugin)")
	public boolean installCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin install (plugin)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin install (plugin)");
		else {
			PluginPage pluginPage = new PluginPage(args[0]);
			if (pluginPage.exists()) io.createConversation(sender, "PluginInstallRequest", new InstallPluginPrompt(args[0]));
			else io.sendError(sender, "Can't find Plugin " + args[0]);
		}
		return true;
	}
	
	@SubCommand(label = "load", helpNode = "Plugin.Load", permission = "bm.plugin.load", usage = "plugin load (&opath) [&ofile]")
	public boolean loadCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin load (&opath) [&ofile]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm plugin load (&opath) [&ofile]");
		else {
			if (args.length == 1) {
				File dir = new File(FileHelper.getBukkitDir(), args[0]);
				if (dir.exists()) {
					if (dir.isDirectory()) {
						try {
							Plugin[] plugins = PluginManager.loadPlugins(dir);
							PluginManager.enablePlugins(plugins);
							io.send(sender, io.translate("Command.Plugin.Load.Dir.Success").replaceAll("%dir%", dir.getAbsolutePath()).replaceAll("%plugins%", plugins.toString()));
						}catch (UnknownDependencyException e) {
							io.send(sender, "Plugin.Load.Error.UnknownDependency");
							if (config.getDebug()) e.printStackTrace();
						}catch (InvalidPluginException e) {
							io.send(sender, "Plugin.Load.Error.InvalidPlugin");
							if (config.getDebug()) e.printStackTrace();
						}catch (InvalidDescriptionException e) {
							io.send(sender, "Plugin.Load.Error.InvalidDescription");
							if (config.getDebug()) e.printStackTrace();
						}
					}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
				}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));
			}else {
				File dir = new File(FileHelper.getBukkitDir(), args[0]);
				if (dir.exists()) {
					if (dir.isDirectory()) {
						File jarFile = new File(dir, args[1]);
						if (jarFile.isFile()) {
							try {
								Plugin plugin = PluginManager.loadPlugin(jarFile);
								PluginManager.enablePlugin(plugin);
								io.send(sender, io.translate("Command.Plugin.Load.File.Success").replaceAll("%file%", jarFile.getName()).replaceAll("%plugin%", plugin.getDescription().getFullName()));								
							} catch (UnknownDependencyException e) {
								io.send(sender, "Plugin.Load.Error.UnknownDependency");
								if (config.getDebug()) e.printStackTrace();
							} catch (InvalidPluginException e) {
								io.send(sender, "Plugin.Load.Error.InvalidPlugin");
								if (config.getDebug()) e.printStackTrace();
							} catch (InvalidDescriptionException e) {
								io.send(sender, "Plugin.Load.Error.InvalidDescription");
								if (config.getDebug()) e.printStackTrace();
							}
						}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoFile").replaceAll("%file%", jarFile.getName()));
					}else io.sendError(sender, io.translate("Command.Plugin.Load.IsNoDir").replaceAll("%dir%", dir.getAbsolutePath()));
				}else io.sendError(sender, io.translate("Command.Plugin.Load.DirDoesntExists").replaceAll("%dir%", dir.getAbsolutePath()));
			}
		}
		return true;
	}
	
	@Command(label = "plugins", helpNode = "Plugin.List", permission = "bm.plugin.list", alias = false, hideHelp = true, usage = "plugins")
	@SubCommand(label = "list", helpNode = "Plugin.List", permission = "bm.plugin.list", usage = "plugin list")
	public boolean listCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm plugin list");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm plugin list");
		else io.send(sender, "Plugins: " + PluginManager.getPluginList());
		return true;
	}
	
	@SubCommand(label = "reload", helpNode = "Plugin.Reload", permission = "bm.plugin.reload", usage = "plugin reload (&oplugin|all)")
	public boolean reloadCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin reload (&oplugin|all)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin reload (&oplugin|all)");
		else {
			if (args[0].equalsIgnoreCase("all")) {
				try {
					PluginManager.reloadPlugins();
					io.sendError(sender, io.translate("Command.Plugin.Reload.Success.All"));
				}catch (UnknownDependencyException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.UnknownDependency"));
					if (config.getDebug()) e.printStackTrace();
				}catch (InvalidPluginException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.InvalidPlugin"));
					if (config.getDebug()) e.printStackTrace();
				}catch (InvalidDescriptionException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.InvalidDescription"));
					if (config.getDebug()) e.printStackTrace();
				} catch (FileNotFoundException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.FileNotFound"));
					if (config.getDebug()) e.printStackTrace();
				}
			}else if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
				io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
				io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
			}else {
				try {
					PluginManager.reloadPlugin(PluginManager.getPluginIgnoreCase(args[0]));
					io.send(sender, io.translate("Command.Plugin.Reload.Success").replaceAll("%plugin%", args[0]));
				}catch (UnknownDependencyException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.UnknownDependency"));
					if (config.getDebug()) e.printStackTrace();
				}catch (InvalidPluginException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.InvalidPlugin"));
					if (config.getDebug()) e.printStackTrace();
				}catch (InvalidDescriptionException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.InvalidDescription"));
					if (config.getDebug()) e.printStackTrace();
				} catch (FileNotFoundException e) {
					io.sendError(sender, io.translate("Plugin.Load.Error.FileNotFound"));
					if (config.getDebug()) e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	@SubCommand(label = "restart", helpNode = "Plugin.Restart", permission = "bm.plugin.restart", usage = "plugin restart (&oplugin|all)")
	public boolean restartCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin restart (&oplugin|all)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin restart (&oplugin|all)");
		else {
			if (args[0].equalsIgnoreCase("all")) {
				PluginManager.restartPlugins();
				io.send(sender, io.translate("Command.Plugin.Restart.Success.All"));
			}else if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
				io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
				io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
			}else {
				if (args[0] == "Spout") io.sendWarning(sender, io.translate("Command.Plugin.Restart.Spout"));
				else {
					PluginManager.restartPlugin(PluginManager.getPluginIgnoreCase(args[0]));
					io.send(sender, io.translate("Command.Plugin.Restart.Success").replaceAll("%plugin%", args[1]));
				}
			}
		}
		return true;
	}
	
	@SubCommand(label = "unload", helpNode = "Plugin.Unload", permission = "bm.plugin.unload", usage = "plugin unload (&oplugin|all)")
	public boolean unloadCommand(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm plugin unload (&oplugin|all)");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin unload (&oplugin|all)");
		else {
			if (args[0].equalsIgnoreCase("all")) {
				PluginManager.unloadPlugins();
				io.send(sender, io.translate("Command.Plugin.Reload.Success.All"));
			}else if (PluginManager.getPluginIgnoreCase(args[0]) == null) {
				io.sendError(sender, io.translate("Command.Plugin.DoesntExists"));
				io.send(sender, io.translate("Command.Plugin.Available").replaceAll("%pluginlist%", PluginManager.getPluginList()));
			}else {
				if (PluginManager.unloadPlugin(PluginManager.getPluginIgnoreCase(args[0]))) 
					io.send(sender, io.translate("Command.Plugin.Unload.Success").replaceAll("%plugin%", args[0]));
				else io.sendError(sender, "Error unloading Plugin " + args[0]);
			}
		}
		return true;
	}
	
	@SubCommand(label = "update", helpNode = "Plugin.Update", permission = "bm.plugin.update", usage = "plugin update [&oplugin|all]")
	public boolean updateCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm plugin update [plugin|all]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm plugin update [plugin|all]");
		else {
			if (args.length == 0) {
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
			}else if (args[0].equalsIgnoreCase("all")) PluginManager.updatePlugins();
			else {
				Plugin plugin = PluginManager.getPluginIgnoreCase(args[0]);
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
		return "Should the newest Version of " + pluginName + " get downloaded? (yes/no)";
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
