package org.efreak.bukkitmanager.language;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.efreak.bukkitmanager.Bukkitmanager;

public class en extends Language {
	
	@Override
	public void createLanguageFile() {
		langFile = new File(Bukkitmanager.getInstance().getDataFolder(), "lang/en.lang");
		if (!langFile.exists()) {
			try {
				langFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateLanguage() {
		lang = new YamlConfiguration();
		try {
			lang.load(langFile);
			
			set("Plugin.Done", "&aDone!");
			set("Plugin.LoadingCommands", "&2Loading Commands...");
			set("Plugin.CommandsLoaded", "&aCommands successfully loaded!");
			set("Plugin.FirstRun", "This seems to be the first time you run Bukkitmanager. You may run /bm install to configure Bukkitmanager...");
			set("Plugin.Debug", "Bukkitmanager runs in Debug Mode");
			
			set("Plugin.Load.Error.UnknownDependency", "Plugin couldn't be loaded: Unknown Dependency");
			set("Plugin.Load.Error.InvalidPlugin", "Plugin couldn't be loaded: Invalid Plugin");
			set("Plugin.Load.Error.InvalidDescription", "Plugin couldn't be loaded: Invalid Plugin Description");
			
			set("PluginUpdater.FetchingPlugins", "Fetching plugins...");
			set("PluginUpdater.CheckingUpdates", "Checking for updates...");
			set("PluginUpdater.UpdatesAvailable", "There are updates for some of your plugins available. Please run /bm plugin update");
			
			set("AutoUpdater.NewVersion", "There is a new Version available: %name%");
			set("AutoUpdater.Running", "You are running %name%");
			set("AutoUpdater.UpToDate", "No updates found!");
			
			set("Player.Login.Hidden", "You are hidden!");
			
			set("Permissions.ForceSuper", "&2Superperms forced!");
			set("Permissions.Found", "&a%perms% support enabled!");
			set("Permissions.NoPerms", "&eNo Permissions System Found!");
			set("Permissions.OP", "&aUsing OP-Rights for Commands!");
			
			set("Autobackup.BackupInProgress", "&eMultiple concurrent backup attempted! Autobackup interval is likely too short!");
			set("Autobackup.NoPlayer", "&eSkipping backup, no players online.");
			set("Autobackup.MultipleJars", "&cMultiple .jar Files in Root Folder detected. Please specify Filename in the config.yml!");
			set("Autobackup.NoJar", "&cNo .jar File in Root Folder detected! Please specify Filepath + Filename in the config.yml!");
			set("Autobackup.JarDoesntExists", "&c%file% does not exists. Please check your config.yml!");
			set("Autobackup.Compressing.Plugins", "&2Compressing plugins...");
			set("Autobackup.Compressing.World", "&2Compressing world: %world%...");
			set("Autobackup.Compressing.Bukkit", "&2Compressing %jar%...");
			set("Autobackup.Warn", "Autobackup in %timeleft% sec.");
			set("Autobackup.Notification.Start", "&aAutobackup started!");
			set("Autobackup.Notification.Finish", "&aAutobackup finished!");
			
			set("Autosave.Saving.Players", "&2Saving Players...");
			set("Autosave.Saved.Players", "&aSaved Players!");
			set("Autosave.Saving.World", "&2Saving world: %world%...");
			set("Autosave.Saved.Worlds", "&aSaved %worlds% Worlds!");
			set("Autosave.SaveInProgress", "&eMultiple concurrent saves attempted! Autosave interval is likely too short!");
			set("Autosave.NoPlayer", "&eSkipping save, no players online.");
			set("Autosave.Warn", "Autosave in %timeleft% sec.");
			set("Autosave.Notification.Start", "&aAutosave started!");
			set("Autosave.Notification.Finish", "&aAutosave finished!");
			
			set("Downloader.Downloading", "&2Downloading %file% (%filesize%)");
			set("Downloader.Downloaded", "&aDownload of %file% finished.");
			set("Downloader.Error", "&cError Downloading File: %file%.");
			
			set("Thread.Start", "&a%thread% started! Interval is %interval% seconds");
			set("Thread.Stopping", "&2Stopping %thread%...");
			set("Thread.Stop", "&aSuccessfully stopped %thread%!");
			set("Thread.Error", "&cCould not stop %thread%");
			
			set("Command.FewArgs", "&cToo few Arguments");
			set("Command.ManyArgs", "&cToo many Arguments");
			set("Command.Usage", "Usage: %usage%");
			set("Command.NoPerm.Player", "&cYou dont have Permission to do that!");
			set("Command.NoPerm.Console", "&e%player% has tried to perform command &8%cmd%!");
			set("Command.Language.Get", "The Language is %lang%.");
			set("Command.Language.Set", "&aThe Language was set to %lang%!");
			set("Command.Language.Error", "&cThe Language %lang% doesn't exists!");
			set("Command.Autobackup.Interval.Get", "The current Autobackupinterval is: %interval%");
			set("Command.Autobackup.Interval.Set", "Set Autobackupinterval from: %interval_old% to: %interval_new%!");
			set("Command.Autobackup.Restart", "Restarted Autobackupthread!");
			set("Command.Autobackup.Start", "Started Autobackupthread!");
			set("Command.Autobackup.Stop", "Stopped Autobackupthread!");
			set("Command.Autosave.Interval.Get", "The current Autosaveinterval is: %interval%");
			set("Command.Autosave.Interval.Set", "Set Autosaveinterval from: %interval_old% to: %interval_new%!");
			set("Command.Autosave.Restart", "Restarted Autosavethread!");
			set("Command.Autosave.Start", "Started Autosavethread!");
			set("Command.Autosave.Stop", "Stopped Autosavethread!");
			set("Command.Automessage.Interval.Get", "The current Automessageinterval is: %interval%");
			set("Command.Automessage.Interval.Set", "Set Automessageinterval from: %interval_old% to: %interval_new%!");
			set("Command.Automessage.Restart", "Restarted Automessagethread!");
			set("Command.Automessage.Start", "Started Automessagethread!");
			set("Command.Automessage.Stop", "Stopped Automessagethread!");
			set("Command.Automessage.Add", "&aA new Automessage was added at index %index%.");
			set("Command.Automessage.Remove", "&aThe Automessage with Index %index% was removed.");
			set("Command.Automessage.Get", "The Automessage with Index %index% contains:");
			set("Command.Bukkit.Config.NotFound", "&cThe entry %entry% was not found.");
			set("Command.Bukkit.Config.Get", "The entry %entry% has the value: &8%value%.");
			set("Command.Bukkit.Config.Set", "&aThe entry %entry% was set to: &8%value%.");
			set("Command.Bukkit.Config.List", "The server.properties contains: %items%");
			set("Command.Player.UnknownPlayer", "&cUnknown Player!");
			set("Command.Player.SpecifyPlayer", "&cPlease specify a Player!");
			set("Command.Player.Cmd.Send", "&aSuccessfully send Command!");
			set("Command.Player.Cmd.Error", "&cError sending Command!");
			set("Command.Player.Displayname.Get.Your", "Your Displayname is: %displayname%.");
			set("Command.Player.Displayname.Get.Other", "The Displayname of %player% is: %displayname%.");
			set("Command.Player.Displayname.Set.Your", "Your Displayname was set to: %displayname%.");
			set("Command.Player.Displayname.Set.Other", "The Displayname of %player% was set to: %displayname%.");
			set("Command.Player.Displayname.Reset.Your", "Your Displayname was recessed to: %displayname%.");
			set("Command.Player.Displayname.Reset.Other", "The Displayname of %player% was recessed to: %displayname%.");
			set("Command.Player.Listname.Get.Your", "Your Listname is: %listname%.");
			set("Command.Player.Listname.Get.Other", "The Listname of %player% is: %listname%.");
			set("Command.Player.Listname.Set.Your", "Your Listname was set to: %listname%.");
			set("Command.Player.Listname.Set.Other", "The Listname of %player% was set to: %listname%.");
			set("Command.Player.Listname.Reset.Your", "Your Listname was recessed to: %listname%.");
			set("Command.Player.Listname.Reset.Other", "The Listname of %player% was recessed to: %listname%.");
			set("Command.Player.Exp.Error", "&cThe Expvalue has to be an Integer!");
			set("Command.Player.Exp.Get.Your", "Your Expvalue is: %exp%.");
			set("Command.Player.Exp.Get.Other", "The Expvalue of %player% is: %exp%.");
			set("Command.Player.Exp.Set.Your", "Your Expvalue was set to: %exp%.");
			set("Command.Player.Exp.Set.Other", "The Expvalue of %player% was set to: %exp%.");
			set("Command.Player.Exp.Add.Your", "%exp%Exp was added to your Expvalue.");
			set("Command.Player.Exp.Add.Other", "%exp%Exp was added to the Expvalue of %player%.");
			set("Command.Player.Health.Error", "&cThe Healthvalue has to be an Integer!");
			set("Command.Player.Health.TooMuch", "&cThis Helpvalue is to big! Choose a smaller one.");
			set("Command.Player.Health.Get.Your", "Your Healthvalue is: %health%.");
			set("Command.Player.Health.Get.Other", "The Healthvalue of %player% is: %health%.");
			set("Command.Player.Health.Set.Your", "Your Health was set to: %health%.");
			set("Command.Player.Health.Set.Other", "The Health of %player% was set to: %health%.");
			set("Command.Player.Health.Add.Your", "%health% was added to your Health.");
			set("Command.Player.Health.Add.Other", "%health% was added to the Health of %player%.");
			set("Command.Player.Health.Remove.Your", "%health% was removed from your Health.");
			set("Command.Player.Health.Remove.Other", "%health% was removed from the Health of %player%.");
			set("Command.Player.Food.Error", "&cThe Foodlevel has to be an Integer!");
			set("Command.Player.Food.TooMuch", "&cThis Foodlevel is to big! Choose a smaller one.");
			set("Command.Player.Food.Get.Your", "Your Foodlevel is: %food%.");
			set("Command.Player.Food.Get.Other", "The Foodlevel of %player% is: %food%.");
			set("Command.Player.Food.Set.Your", "Your Foodlevel was set to: %food%.");
			set("Command.Player.Food.Set.Other", "The Foodlevel of %player% was set to: %food%.");
			set("Command.Player.Food.Add.Your", "%food% was added to your Foodlevel.");
			set("Command.Player.Food.Add.Other", "%food% was added to the Foodlevel of %player%.");
			set("Command.Player.Food.Remove.Your", "%food% was removed from your Foodlevel.");
			set("Command.Player.Food.Remove.Other", "%food% was removed from the Foodlevel of %player%.");
			set("Command.Player.Hide.You", "You were successfully hidden!");
			set("Command.Player.Hide.Other", "%player% were successfully hidden!");
			set("Command.Player.Hide.ByOther", "You were hidden by %player%!");
			set("Command.Player.Hide.Already.You", "&eYou are already hidden!");
			set("Command.Player.Hide.Already.Other", "&e%player% is already hidden!");
			set("Command.Player.Hide.Console.You", "%player% has hidden itself!");
			set("Command.Player.Hide.Console.Other", "%causer% has hidden %player%!");
			set("Command.Player.Gamemode.Error", "&cGamemode has to be a Integer!");
			set("Command.Player.Gamemode.Unknown", "&cUnknown Gamemode!");
			set("Command.Player.Lastseen.Your", "You were lastseen on %lastseen_date% at %lastseen_time%");
			set("Command.Player.Lastseen.Other", "%player% was lastseen on %lastseen_date% at %lastseen_time%");
			set("Command.Player.Firstseen.Your", "You were firstseen on %firstseen%");
			set("Command.Player.Firstseen.Other", "%player% was firstseen on %firstseen%");
			set("Command.Player.Level.Error", "&cThe Level has to be an Integer!");
			set("Command.Player.Level.Get.Your", "Your Level is: %level%.");
			set("Command.Player.Level.Get.Other", "The Level of %player% is: %level%.");
			set("Command.Player.Level.Set.Your", "Your Level was set to: %level%.");
			set("Command.Player.Level.Set.Other", "The Level of %player% was set to: %level%.");
			set("Command.Player.Load", "&a%player%.dat loaded!");
			set("Command.Player.Save", "&a%player%.dat saved!");
			set("Command.Player.Show.You", "You were successfully shown!");
			set("Command.Player.Show.Other", "%player% were successfully shown!");
			set("Command.Player.Show.ByOther", "You were shown by %player%!");
			set("Command.Player.Show.Already.You", "&eYou are already visible!");
			set("Command.Player.Show.Already.Other", "&e%player% is already visible!");
			set("Command.Player.Show.Console.You", "%player% has shown itself!");
			set("Command.Player.Show.Console.Other", "%causer% has shown %player%!");
			set("Command.Player.Time.Get.Your", "Your Time is: %time%.");
			set("Command.Player.Time.Get.Other", "The Time of %player% is: %time%.");
			set("Command.Player.Time.Set.Your", "Your Time was set to: %time%.");
			set("Command.Player.Time.Set.Other", "The Time of %player% was set to: %time%.");
			set("Command.Player.Time.Reset.Your", "Your Time was recessed to: %time%.");
			set("Command.Player.Time.Reset.Other", "The Time of %player% was recessed to: %time%.");
			set("Command.Plugin.DoesntExists", "&cThis Plugin doesnt exists.");
			set("Command.Plugin.Available", "Available Plugins: %pluginlist%");
			set("Command.Plugin.Config.NoConfig", "&cThis Plugin doesnt hava a Config!");
			set("Command.Plugin.Config.NoEntry", "&cThis Entry doesnt exists!");
			set("Command.Plugin.Config.Get", "The Entry %entry% has the value: &8%value%&f.");
			set("Command.Plugin.Config.Set", "&aThe Entry %entry% was set from: &8%value_old% &fto: &8%value_new%&f.");
			set("Command.Plugin.Config.List", "The config.yml of %plugin% contains: %items%");
			set("Command.Plugin.Disable.Already", "&cThis Plugin is already disabled.");
			set("Command.Plugin.Disable.Spout", "&eYou shouldnt disable Spout, while Bukkit is running!");
			set("Command.Plugin.Disable.Success", "&aPlugin %plugin% succesfully disabled.");
			set("Command.Plugin.Enable.Already", "&cThis Plugin is already enabled.");
			set("Command.Plugin.Enable.Success", "&aPlugin %plugin% succesfully enabled.");
			set("Command.Plugin.Restart.Spout", "&eYou shouldnt restart Spout, while Bukkit is running!");
			set("Command.Plugin.Restart.Success", "&aPlugin %plugin% succesfully restarted.");
			set("Command.Plugin.Load.Dir.Success", "&a%plugins% were successfully loaded from %dir%!");
			set("Command.Plugin.Load.IsNoDir", "&c%dir% is no Folder!");
			set("Command.Plugin.Load.DirDoesntExists", "&cThe Folder %dir% doesn't exists!");
			set("Command.Plugin.Load.File.Success", "&a%plugin% was successfully loaded by File %file%!");
			set("Command.Plugin.Load.IsNoFile", "&c%file% is no File!");
			set("Command.Plugin.Load.FileDoesntExists", "&c%file% doesn't exists!");
			set("Command.Plugin.Update.UpdatesAvailable", "There are %count% updates available: %names%");
			
			set("CustomMessages.Loading", "&2Loading Custom-Messages...");
			set("CustomMessages.Loaded", "&a%msgCount% Custom-Messages loaded!");
			
			set("Logger.Loading", "&2Loading Logger...");
			set("Logger.Loaded", "&aLogger loaded!");
			//set("Logger.Block.BlockBreak", "");
			//set("Logger.Block.BlockBurn", "");
			
			save();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
