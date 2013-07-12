package org.efreak.bukkitmanager.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Permissions;

public class BukkitCmd extends CommandHandler {

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

	@Command(label = "bukkit", helpNode = "Bukkit", hideHelp = true, usage = "/bukkit <config|info|restart> [args]")
	public boolean bukkitCommand(CommandSender sender, String[] args) {
		if (args.length >= 1) handleSubCommands(sender, args);
		else listSubCommands(sender);
		return true;
	}
	
	@SubCommand(label = "info", helpNode = "Bukkit.Info", permission = "bm.bukkit.info", usage = "bukkit info")
	public boolean infoCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm bukkit info");
		else if (args.length > 0) io.sendManyArgs(sender, "/bm bukkit info");
		else {
			io.sendHeader(sender, "BUKKIT INFO");
			io.send(sender, "Minecraftversion: " + Bukkit.getBukkitVersion().split("-")[0], false);
			io.send(sender, "API-Version: " + Bukkit.getBukkitVersion(), false);
			io.send(sender, "Bukkitbuild: " + Bukkit.getVersion().split("-b")[1].split("jnks")[0], false);
			if (!Bukkit.getIp().equalsIgnoreCase("")) io.send(sender, "Runs on: " + Bukkit.getIp() + ":" + Bukkit.getPort(), false);
			else io.send(sender, "Runs on all available IP's with port: " + Bukkit.getPort(), false);
		}
		return true;
	}
	
	//@SubCommand(label = "restart", helpNode = "Bukkit.Restart", permission = "bm.bukkit.restart", usage = "/bukkit restart [time]")
	public boolean restartCommand(CommandSender sender, String[] args) {
		if (args.length < 0) io.sendFewArgs(sender, "/bm bukkit restart [time]");
		else if (args.length > 1) io.sendManyArgs(sender, "/bm bukkit restart [time]");
		else {
			String cmd = "java -jar " + Bukkitmanager.getPluginFile().toString();
			if (args.length == 0) {
				io.broadcast(io.translate("Bukkit.Restart.Now"));
				cmd += "--external --task restart";
			}else {
				io.broadcast(io.translate("Bukkit.Restart.Timer").replaceAll("%time%", args[1]));
				cmd += "--external --task restart --timer " + args[0];
			}
			try {
				Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
		return true;
	}
	
	@SubCommand(label = "config", helpNode = "Bukkit.Config", permission = "bm.bukkit.config", usage = "bukkit config <entry|list> [value]")
	public boolean configCommand(CommandSender sender, String[] args) {
		FileInputStream streamIn = null;
		FileOutputStream streamOut = null;
		Properties propertie = new Properties();
		try {
			streamIn = new FileInputStream("server.properties");
			propertie.load(streamIn);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		if (args.length < 1) io.sendFewArgs(sender, "/bm bukkit config (entry|list) [value]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm bukkit config (entry|list) [value]");
		else {
			if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				if (Permissions.has(sender, "bm.bukkit.config.list", "/bukkit config list")) {
					Object[] properties = propertie.entrySet().toArray();
					StringBuilder output = new StringBuilder();
					output.append(properties[0]);
					for (int i = 1; i < properties.length; i++) {
						output.append(", ");
						output.append(properties[i]);
					}
					io.send(sender, io.translate("Command.Bukkit.Config.List").replaceAll("%items%", output.toString()));
				}
			}else if (args.length == 1) {
				if (Permissions.has(sender, "bm.bukkit.config.get", "/bukkit config " + args[0])) {
					if (propertie.getProperty(args[0]) != null) io.send(sender, io.translate("Command.Bukkit.Config.Get").replaceAll("%entry%", args[0]).replaceAll("%value%", propertie.getProperty(args[0])));
					else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[0]));
				}
			}else if (args.length == 2) {
				if (Permissions.has(sender, "bm.bukkit.config.set", "/bukkit config " + args[0] + " " + args[1])) {
					if (propertie.getProperty(args[0]) != null) {
						propertie.setProperty(args[0], args[1]);
						io.send(sender, io.translate("Command.Bukkit.Config.Set").replaceAll("%entry%", args[0]).replaceAll("%value%", args[1]));
						try {
							streamOut = new FileOutputStream("server.properties");
						} catch (FileNotFoundException e) {
							if (config.getDebug()) e.printStackTrace();
						}
						try {
							propertie.store(streamOut, null);
						} catch (IOException e) {
							if (config.getDebug()) e.printStackTrace();
							io.sendError(sender, "Couldn't save File");
						}
						try {
							streamOut.flush();
							streamOut.close();
						} catch (IOException e) {
							if (config.getDebug()) e.printStackTrace();
						}
					}else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[1]));
				}
			}
		}
		try {
			streamIn.close();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return true;
	}
}
