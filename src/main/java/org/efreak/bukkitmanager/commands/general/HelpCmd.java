package org.efreak.bukkitmanager.commands.general;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak.bukkitmanager.commands.Command;
import org.efreak.bukkitmanager.commands.CommandCategory;
import org.efreak.bukkitmanager.help.*;

public class HelpCmd extends Command {
		
	public HelpCmd() {
		super("help", "Help", "bm.help", new ArrayList<String>(), CommandCategory.GENERAL);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		List<HelpTopic> helpTopics = HelpManager.getTopics();
		List<String> topics = new ArrayList<String>();
		for (int i = 0; i < helpTopics.size(); i++) {
			if (helpTopics.get(i).hasPerm(sender)) topics.add(helpTopics.get(i).format());
		}
		int pages = 1;
		if (topics.size() > 9) pages = (int)(topics.size() / 9F+0.4F);
		if (args.length == 0) {
			io.sendHeader(sender, config.getString("IO.HelpHeader").replaceAll("%page%",  "1").replaceAll("%pages%", String.valueOf(pages)));
			for (int i = 0; i < 9 && i < topics.size(); i++) io.send(sender, topics.get(i), false);
		}else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("caption")) {
				io.sendHeader(sender, "BUKKITMANAGER HELP LEGEND");
	       		io.send(sender, "&c#&f : Number", false);
	       		io.send(sender, "&cvalue&f : place holder", false);
	       		io.send(sender, "&evalue&f : defined value", false);
	       		io.send(sender, "&c[]&f : optional value", false);
	       		io.send(sender, "&c()&f : required value", false);
	       		io.send(sender, "&c|&f : (or) seperator", false);
			}else {
				try {
					if (new Integer(args[0]) <= pages) {
						int page = new Integer(args[0]);
						io.send(sender, config.getString("IO.HelpHeader").replaceAll("%page%",  args[0]).replaceAll("%pages%", String.valueOf(pages)), false);
						for (int i = 9*page-9; i < 9*page && i < topics.size()-1; i++) io.send(sender, topics.get(i), false);
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
