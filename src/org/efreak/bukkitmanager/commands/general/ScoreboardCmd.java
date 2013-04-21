package org.efreak.bukkitmanager.commands.general;

import java.util.Arrays;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.BmPlayer;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.scoreboards.ScoreboardManager;

public class ScoreboardCmd extends Command{
	
	public ScoreboardCmd() {
		super("scoreboard", "Scoreboard", "bm.scoreboard", Arrays.asList("(hide|show)", "[player]"), CommandCategory.GENERAL);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		if (args.length < (1 + length)) io.sendFewArgs(sender, "/bm scoreboard (hide|show) [player]");
		else if (args.length > (2 + length)) io.sendManyArgs(sender, "/bm lang (hide|show) [player]");
		else {
			if (args.length == (1 + length) && args[0 + length].equalsIgnoreCase("hide")) {
				ScoreboardManager.hideMainScoreboard(new BmPlayer((OfflinePlayer) sender));
			}else if (args.length == (1 + length) && args[0 + length].equalsIgnoreCase("show")) {
				if (has(sender, "bm.scoreboard.show")) ScoreboardManager.showMainScoreboard(new BmPlayer((OfflinePlayer) sender));
			}else if (args.length == (2 + length) && args[0 + length].equalsIgnoreCase("hide")) {
			}
		}
		return true;
	}
}
