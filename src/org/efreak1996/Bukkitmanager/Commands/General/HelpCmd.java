package org.efreak1996.Bukkitmanager.Commands.General;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.Commands.Command;
import org.efreak1996.Bukkitmanager.Commands.CommandCategory;
import org.efreak1996.Bukkitmanager.Help.*;

public class HelpCmd extends Command {
		
	public HelpCmd() {
		super("help", "Help", "bm.help", new ArrayList<String>(), CommandCategory.GENERAL);
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, Integer length) {
		List<HelpTopic> helpTopics = HelpManager.getTopics();
		List<String> topics = new ArrayList<String>();
		for (int i = 0; i < helpTopics.size(); i++) {
			if (helpTopics.get(i).hasPerm(sender)) topics.add(helpTopics.get(i).format());
		}
		int pages = 1;
		pages = (int)(((topics.size()) / 9F)+0.4F);
		if (args.length == 1) {
			io.send(sender, "&e--------------&f BUKKITMANAGER HELP(1/" + pages + ") &e--------------", false);
			for (int i = 0; i < 9; i++) io.send(sender, topics.get(i), false);
		}else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("caption")) {
	       		io.send(sender, "&e-------------&f BUKKITMANAGER HELP LEGEND &e-------------", false);
	       		io.send(sender, "&c#&f : Number", false);
	       		io.send(sender, "&cvalue&f : place holder", false);
	       		io.send(sender, "&evalue&f : defined value", false);
	       		io.send(sender, "&c[]&f : optional value", false);
	       		io.send(sender, "&c()&f : required value", false);
	       		io.send(sender, "&c|&f : (or) seperator", false);
			}else if (new Integer(args[1]) <= pages) {
				int page = new Integer(args[1]);
				io.send(sender, "&e--------------&f BUKKITMANAGER HELP(" + args[1] + "/" + pages + ") &e--------------", false);
				for (int i = (9*page-9); i < (9*page) && i < (topics.size()-1); i++) io.send(sender, topics.get(i), false);
			}
		}
		return true;
	}
}
