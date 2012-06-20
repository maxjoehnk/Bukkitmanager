package org.efreak1996.Bukkitmanager.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.efreak1996.Bukkitmanager.IOManager;
import org.efreak1996.Bukkitmanager.Help.*;


public class BmHelp {
	
	private static IOManager io;
	private static HelpFile helpFile;
	
	public void initialize() {
		io = new IOManager();
		helpFile = new HelpFile();
		helpFile.initialize();
		HelpManager.initialize();
		List<HelpTopic> topics = new ArrayList<HelpTopic>();
		topics.add(new HelpTopic("/bm help", "[#|command|caption]", helpFile.getHelp("Help"), "bm.help"));
		addAutobackupTopics(topics);
		addAutomsgTopics(topics);
		addAutosaveTopics(topics);
		addBukkitTopics(topics);
		topics.add(new HelpTopic("/bm lang get", "", helpFile.getHelp("Language.Get"), "bm.language.get"));
		topics.add(new HelpTopic("/bm lang set", "(language)", helpFile.getHelp("Language.Set"), "bm.language.set"));
		addPlayerTopics(topics);
		addPluginTopics(topics);
		HelpManager.registerTopics(topics);
	}
	public void shutdown() {}

	private List<HelpTopic> addAutobackupTopics(List<HelpTopic> topics) {
		topics.add(new HelpTopic("/bm autobackup backup", "", helpFile.getHelp("Autobackup.Backup"), "bm.autobackup.backup"));
		topics.add(new HelpTopic("/bm autobackup start", "", helpFile.getHelp("Autobackup.Start"), "bm.autobackup.start"));
		topics.add(new HelpTopic("/bm autobackup stop", "", helpFile.getHelp("Autobackup.Stop"), "bm.autobackup.stop"));
		topics.add(new HelpTopic("/bm autobackup restart", "", helpFile.getHelp("Autobackup.Restart"), "bm.autobackup.restart"));
		topics.add(new HelpTopic("/bm autobackup interval", "[interval]", helpFile.getHelp("Autobackup.Interval"), "bm.autobackup.interval"));
		return topics;
	}
	
	private List<HelpTopic> addAutomsgTopics(List<HelpTopic> topics) {
		topics.add(new HelpTopic("/bm automessage add", "(msg)", helpFile.getHelp("Automessage.Add"), "bm.automessage.add"));
		topics.add(new HelpTopic("/bm automessage remove", "(index)", helpFile.getHelp("Automessage.Remove"), "bm.automessage.remove"));
		topics.add(new HelpTopic("/bm automessage get", "(index)", helpFile.getHelp("Automessage.Get"), "bm.automessage.get"));
		topics.add(new HelpTopic("/bm automessage list", "", helpFile.getHelp("Automessage.List"), "bm.automessage.list"));
		topics.add(new HelpTopic("/bm automessage send", "(index)", helpFile.getHelp("Automessage.Send"), "bm.automessage.send"));
		topics.add(new HelpTopic("/bm automessage start", "", helpFile.getHelp("Automessage.Start"), "bm.automessage.start"));
		topics.add(new HelpTopic("/bm automessage stop", "", helpFile.getHelp("Automessage.Stop"), "bm.automessage.stop"));
		topics.add(new HelpTopic("/bm automessage restart", "", helpFile.getHelp("Automessage.Restart"), "bm.automessage.restart"));
		topics.add(new HelpTopic("/bm automessage interval", "[interval]", helpFile.getHelp("Automessage.Interval"), "bm.automessage.interval"));
		return topics;
	}
	
	private List<HelpTopic> addAutosaveTopics(List<HelpTopic> topics) {
		topics.add(new HelpTopic("/bm autosave save", "", helpFile.getHelp("Autosave.Save"), "bm.autosave.save"));
		topics.add(new HelpTopic("/bm autosave start", "", helpFile.getHelp("Autosave.Start"), "bm.autosave.start"));
		topics.add(new HelpTopic("/bm autosave stop", "", helpFile.getHelp("Autosave.Stop"), "bm.autosave.stop"));
		topics.add(new HelpTopic("/bm autosave restart", "", helpFile.getHelp("Autosave.Restart"), "bm.autosave.restart"));
		topics.add(new HelpTopic("/bm autosave interval", "[interval]", helpFile.getHelp("Autosave.Interval"), "bm.autosave.interval"));
		return topics;
	}

	/*List<HelpTopic> topics = new ArrayList<HelpTopic>();
	topics.add(new HelpTopic("bm ", "", "", "bm."));
	HelpManager.registerTopics(topics);*/
	
	private List<HelpTopic> addBukkitTopics(List<HelpTopic> topics) {
		topics.add(new HelpTopic("/bm bukkit config", "(entry) [value]", helpFile.getHelp("Bukkit.Config"), "bm.bukkit.config"));
		topics.add(new HelpTopic("/bm bukkit info", "", helpFile.getHelp("Bukkit.Info"), "bm.bukkit.info"));
		return topics;
	}
	
	private List<HelpTopic> addPlayerTopics(List<HelpTopic> topics) {
		topics.add(new HelpTopic("/bm player chat", "(player) (msg)", helpFile.getHelp("Player.Chat"), "bm.player.chat"));
		topics.add(new HelpTopic("/bm player cmd", "(player) (cmd)", helpFile.getHelp("Player.Cmd"), "bm.player.cmd"));
		topics.add(new HelpTopic("/bm player displayname get", "[player]", helpFile.getHelp("Player.Displayname.Get"), "bm.player.displayname.get"));
		topics.add(new HelpTopic("/bm player displayname set", "(displayname) [player]", helpFile.getHelp("Player.Displayname.Set"), "bm.player.displayname.set"));
		topics.add(new HelpTopic("/bm player displayname reset", "[player]", helpFile.getHelp("Player.Displayname.Reset"), "bm.player.displayname.reset"));
		topics.add(new HelpTopic("/bm player exp get", "[player]", helpFile.getHelp("Player.Exp.Get"), "bm.player.exp.get"));
		topics.add(new HelpTopic("/bm player exp set", "(exp) [player]", helpFile.getHelp("Player.Food.Set"), "bm.player.exp.set"));
		topics.add(new HelpTopic("/bm player exp add", "(exp) [player]", helpFile.getHelp("Player.Food.Add"), "bm.player.exp.add"));
		topics.add(new HelpTopic("/bm player firstseen", "[player]", helpFile.getHelp("Player.Firstseen"), "bm.player.firstseen"));
		topics.add(new HelpTopic("/bm player food get", "[player]", helpFile.getHelp("Player.Food.Get"), "bm.player.food.get"));
		topics.add(new HelpTopic("/bm player food set", "(food) [player]", helpFile.getHelp("Player.Food.Set"), "bm.player.food.set"));
		topics.add(new HelpTopic("/bm player food add", "(food) [player]", helpFile.getHelp("Player.Food.Add"), "bm.player.food.add"));
		topics.add(new HelpTopic("/bm player food remove", "(food) [player]", helpFile.getHelp("Player.Food.Remove"), "bm.player.food.remove"));
		topics.add(new HelpTopic("/bm player gamemode", "(gamemode) [player]", helpFile.getHelp("Player.Gamemode"), "bm.player.gamemode"));
		topics.add(new HelpTopic("/bm player health get", "[player]", helpFile.getHelp("Player.Health.Get"), "bm.player.health.get"));
		topics.add(new HelpTopic("/bm player health set", "(health) [player]", helpFile.getHelp("Player.Health.Set"), "bm.player.health.set"));
		topics.add(new HelpTopic("/bm player health add", "(health) [player]", helpFile.getHelp("Player.Health.Add"), "bm.player.health.add"));
		topics.add(new HelpTopic("/bm player health remove", "(health) [player]", helpFile.getHelp("Player.Health.Remove"), "bm.player.health.remove"));
		topics.add(new HelpTopic("/bm player hide", "[player]", helpFile.getHelp("Player.Hide"), "bm.player.hide"));
		topics.add(new HelpTopic("/bm player lastseen", "[player]", helpFile.getHelp("Player.Lastseen"), "bm.player.lastseen"));
		topics.add(new HelpTopic("/bm player level get", "[player]", helpFile.getHelp("Player.Level.Get"), "bm.player.level.get"));
		topics.add(new HelpTopic("/bm player level set", "(level) [player]", helpFile.getHelp("Player.Level.Get"), "bm.player.level.set"));
		topics.add(new HelpTopic("/bm player listname get", "[player]", helpFile.getHelp("Player.Listname.Get"), "bm.player.listname.get"));
		topics.add(new HelpTopic("/bm player listname set", "(listname) [player]", helpFile.getHelp("Player.Listname.Set"), "bm.player.listname.set"));
		topics.add(new HelpTopic("/bm player listname reset", "[player]", helpFile.getHelp("Player.Listname.Reset"), "bm.player.listname.reset"));
		topics.add(new HelpTopic("/bm player load", "[player]", helpFile.getHelp("Player.Load"), "bm.player.load"));
		topics.add(new HelpTopic("/bm player save", "[player]", helpFile.getHelp("Player.Save"), "bm.player.save"));
		topics.add(new HelpTopic("/bm player show", "[player]", helpFile.getHelp("Player.Show"), "bm.player.show"));
		topics.add(new HelpTopic("/bm player time get", "[player]", helpFile.getHelp("Player.Time.Get"), "bm.player.time.get"));
		topics.add(new HelpTopic("/bm player time set", "(time) [player]", helpFile.getHelp("Player.Time.Set"), "bm.player.time.set"));
		topics.add(new HelpTopic("/bm player time reset", "[player]", helpFile.getHelp("Player.Time.Reset"), "bm.player.time.reset"));
		return topics;
	}
	
	private List<HelpTopic> addPluginTopics(List<HelpTopic> topics) {
		topics.add(new HelpTopic("/bm plugin config", "(plugin) (entry) [value]", helpFile.getHelp("Plugin.Config"), "bm.plugin.config"));
		topics.add(new HelpTopic("/bm plugin disable", "(plugin)", helpFile.getHelp("Plugin.Disable"), "bm.plugin.disable"));
		topics.add(new HelpTopic("/bm plugin enable", "(plugin)", helpFile.getHelp("Plugin.Enable"), "bm.plugin.enable"));
		topics.add(new HelpTopic("/bm plugin info", "[plugin]", helpFile.getHelp("Plugin.Info"), "bm.plugin.info"));
		topics.add(new HelpTopic("/bm plugin list", "[#]", helpFile.getHelp("Plugin.List"), "bm.plugin.list"));
		topics.add(new HelpTopic("/bm plugin load", "(path) [file]", helpFile.getHelp("Plugin.Load"), "bm.plugin.load"));
		topics.add(new HelpTopic("/bm plugin restart", "(plugin)", helpFile.getHelp("Plugin.Restart"), "bm.plugin.restart"));
		return topics;
	}
	
	public void cmd(CommandSender sender, String[] args) {
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
		
	}
}
