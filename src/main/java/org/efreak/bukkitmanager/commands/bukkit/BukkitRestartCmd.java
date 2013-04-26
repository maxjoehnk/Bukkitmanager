package org.efreak.bukkitmanager.commands.bukkit;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;

public class BukkitRestartCmd extends Command{
	
	public BukkitRestartCmd() {
		super("restart", "Bukkit.Restart", "bm.bukkit.restart", new ArrayList<String>(), CommandCategory.BUKKIT);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < 1) io.sendFewArgs(sender, "/bm bukkit restart [time]");
		else if (args.length > 2) io.sendManyArgs(sender, "/bm bukkit restart [time]");
		else {
			if (has(sender, "bm.bukkit.restart")) {
				String cmd = "java -jar " + Bukkitmanager.getPluginFile().toString();
				if (args.length == 1) {
					io.broadcast(io.translate("Bukkit.Restart.Now"));
					cmd += "--external --task restart";
				}else {
					io.broadcast(io.translate("Bukkit.Restart.Timer").replaceAll("%time%", args[1]));
					cmd += "--external --task restart --timer " + args[1];
				}
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					if (config.getDebug()) e.printStackTrace();
				}
			}
		}
		return true;
	}
}
