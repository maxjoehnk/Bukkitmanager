package org.efreak.bukkitmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.commands_old.CommandCategory;
import org.efreak.bukkitmanager.help.HelpManager;
import org.efreak.bukkitmanager.help.HelpTopic;

public class HelpCmd extends CommandHandler {

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		List<String> tabs = new ArrayList<String>();
		return tabs;
	}
	
	@Command(label = "help", alias = false, hideHelp = true, usage = "bm help [cmd|#|caption]")
	public boolean helpCommand(CommandSender sender, String[] args) {
		List<HelpTopic> helpTopics = HelpManager.getTopics();
		List<String> topics = new ArrayList<String>();
		for (int i = 0; i < helpTopics.size(); i++) {
			if (helpTopics.get(i).hasPerm(sender)) topics.add(helpTopics.get(i).format());
		}
		int pages = 1;
		if (topics.size() > 9) {
			pages = topics.size() / 9;
			if (topics.size() % 9 > 0) pages++;
		}
		if (args.length == 0) {
			io.sendHeader(sender, config.getString("IO.HelpHeader").replaceAll("%page%",  "1").replaceAll("%pages%", String.valueOf(pages)));
			for (int i = 0; i < 9 && i < topics.size(); i++) io.send(sender, topics.get(i), false);
		}else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("caption")) {
				io.sendHeader(sender, "BUKKITMANAGER HELP CAPTION");
	       		io.send(sender, "&e#&f: Number", false);
	       		io.send(sender, "&e&ovalue&f: place holder", false);
	       		io.send(sender, "&evalue&f: defined value", false);
	       		io.send(sender, "&e[]&f: optional value", false);
	       		io.send(sender, "&e<>&f: required value", false);
	       		io.send(sender, "&e|&f: (or) seperator", false);
			}else {
				try {
					if (new Integer(args[0]) <= pages) {
						int page = new Integer(args[0]);
						io.sendHeader(sender, config.getString("IO.HelpHeader").replaceAll("%page%",  args[0]).replaceAll("%pages%", String.valueOf(pages)));
						for (int i = 9 * (page - 1); i < 9 * page && i < topics.size(); i++) io.send(sender, topics.get(i), false);
					}else io.sendError(sender, "This Page doesn't exist.");
				}catch (NumberFormatException e) {
					if (CommandCategory.valueOf(args[0].toUpperCase()) != null) {
						io.send(sender, "Command..");
					}
				}
			}
		}
		return true;
	}

}
