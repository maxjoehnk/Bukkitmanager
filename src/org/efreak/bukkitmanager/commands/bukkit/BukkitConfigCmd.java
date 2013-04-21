package org.efreak.bukkitmanager.commands.bukkit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class BukkitConfigCmd extends Command {
	
	public static FileInputStream streamIn = null;
	public static FileOutputStream streamOut = null;
	public static Properties propertie;
	
	public BukkitConfigCmd() {
		super("config", "Bukkit.Config", "bm.bukkit.config", Arrays.asList("(entry|list)","[value]"), CommandCategory.BUKKIT);
		propertie = new Properties();
		try {
			streamIn = new FileInputStream("server.properties");
			propertie.load(streamIn);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		try {
			streamIn = new FileInputStream("server.properties");
			propertie.load(streamIn);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}

		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm bukkit config (entry|list) [value]");
		else if (args.length > (3 + length)) io.sendManyArgs(sender, "/bm bukkit config (entry|list) [value]");
		else {
			if (args.length == (2 + length) && args[1 + length].equalsIgnoreCase("list")) {
				if (has(sender, "bm.bukkit.config.list")) {
					Object[] properties = propertie.entrySet().toArray();
					StringBuilder output = new StringBuilder();
					output.append(properties[0]);
					for (int i = 1; i < properties.length; i++) {
						output.append(", ");
						output.append(properties[i]);
					}
					io.send(sender, io.translate("Command.Bukkit.Config.List").replaceAll("%items%", output.toString()));
				}
			}else if (args.length == (2 + length)) {
				if (has(sender, "bm.bukkit.config.get")) {
					if (propertie.getProperty(args[1 + length]) != null) io.send(sender, io.translate("Command.Bukkit.Config.Get").replaceAll("%entry%", args[1 + length]).replaceAll("%value%", propertie.getProperty(args[1 + length])));
					else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[1 + length]));
				}
			}else if (args.length == (3 + length)) {
				if (has(sender, "bm.bukkit.config.set")) {
					if (propertie.getProperty(args[1 + length]) != null) {
						propertie.setProperty(args[1 + length], args[2 + length]);
						io.send(sender, io.translate("Command.Bukkit.Config.Set").replaceAll("%entry%", args[1 + length]).replaceAll("%value%", args[2 + length]));
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
					}else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[1 + length]));
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
