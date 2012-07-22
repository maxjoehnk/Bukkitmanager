package org.efreak1996.Bukkitmanager.Commands.Bukkit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;

public class BukkitConfigCmd extends Command {
	
	public static FileInputStream streamIn = null;
	public static FileOutputStream streamOut = null;
	public static Properties propertie;
	
	public BukkitConfigCmd() {
		super("config", "Bukkit.Config", "bm.bukkit.config", Arrays.asList("(entry)","[value]"), CommandCategory.BUKKIT);
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

		if (args.length < (2 + length)) io.sendFewArgs(sender, "/bm bukkit config (entry) [value]");
		else if (args.length > (3 + length)) io.sendManyArgs(sender, "/bm bukkit config (entry) [value]");
		else {
			if (args.length == (2 + length)) {
				if (has(sender, "bm.bukkit.config.get")) {
					if (propertie.getProperty(args[1 + length]) != null) io.send(sender, io.translate("Command.Bukkit.Config.Get").replaceAll("%entry%", args[1 + length]).replaceAll("%value%", propertie.getProperty(args[1 + length])));
					else io.sendError(sender, io.translate("Command.Bukkit.Config.NotFound").replaceAll("%entry%", args[1 + length]));
				}
			}
			else if (args.length == (3 + length)) {
				if (has(sender, "bm.bukkit.config.set")) {
					if (propertie.getProperty(args[1 + length]) != null) {
						propertie.setProperty(args[1 + length], args[2 + length]);
						io.send(sender, io.translate("Command.Bukkit.Config.Set").replaceAll("%entry%", args[1 + length]).replaceAll("%value%", args[2 + length]));
						try {
							streamOut = new FileOutputStream("server.properties");
						} catch (FileNotFoundException e) {
							if (config.getDebug()) e.printStackTrace();
						}
						propertie.save(streamOut, null);
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
