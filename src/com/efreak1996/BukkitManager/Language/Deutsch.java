package com.efreak1996.BukkitManager.Language;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.efreak1996.BukkitManager.BmPlugin;

public class Deutsch extends Language {

	private static YamlConfiguration lang;
	private static File langFile;
	
	@Override
	public void createLanguageFile() {
		langFile = new File(BmPlugin.getPlugin().getDataFolder() + File.separator + "lang" + File.separator + "de_DE.lang");
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
			
			lang.set("Plugin.Done", "&aFertig!");
			lang.set("Plugin.LoadingCommands", "&2Lade Befehle...");
			lang.set("Plugin.CommandsLoaded", "&aBefehle erfolgreich geladen!");
			
			lang.set("Permissions.ForceSuper", "&2Superperms !");
			lang.set("Permissions.Found", "&a%perms% support enabled!");
			lang.set("Permissions.NoPerms", "&eNo Permissions System Found!");
			lang.set("Permissions.OP", "&2Using OP-Rights for Commands!");
			
			lang.set("Autobackup.BackupInProgress", "&eMultiple concurrent backup attempted! Autobackup interval is likely too short!");
			lang.set("Autobackup.NoPlayer", "&eSkipping backup, no players online.");
			lang.set("Autobackup.MultipleJars", "&cMultiple .jar Files in Root Folder detected. Please specify Filename in the config.yml!");
			lang.set("Autobackup.NoJar", "&cNo .jar File in Root Folder detected! Please specify Filepath + Filename in the config.yml!");
			lang.set("Autobackup.JarDoesntExists", "&c%file% does not exists. Please check your config.yml!");
			lang.set("Autobackup.Compressing.Plugins", "&2Compressing plugins...");
			lang.set("Autobackup.Compressing.World", "&2Compressing world: %world%...");
			lang.set("Autobackup.Compressing.Bukkit", "&2Compressing %jar%...");
			
			lang.set("Autosave.Saving.Players", "&2Saving Players...");
			lang.set("Autosave.Saved.Players", "&aSaved Players!");
			lang.set("Autosave.Saving.World", "&2Saving world: %world%...");
			lang.set("Autosave.Saved.Worlds", "&aSaved %worlds% Worlds!");
			lang.set("Autosave.SaveInProgress", "&eMultiple concurrent saves attempted! Autosave interval is likely too short!");
			lang.set("Autosave.NoPlayer", "&eSkipping save, no players online.");
			
			lang.set("Downloader.Downloading", "&2Downloading %file% (%filesize%)");
			lang.set("Downloader.Downloaded", "&aDownload of %file% finished.");
			lang.set("Downloader.Error", "&cError Downloading File: %file%.");
			
			lang.set("Thread.Start", "&a%thread% started! Interval is %interval% seconds");
			lang.set("Thread.Stopping", "&2Stopping %thread%...");
			lang.set("Thread.Stop", "&aSuccessfully stopped %thread%!");
			lang.set("Thread.Error", "&cCould not stop %thread%");
			
			lang.set("Command.FewArgs", "Too few Arguments");
			lang.set("Command.ManyArgs", "Too many Arguments");
			lang.set("Command.Usage", "Usage: %usage%");
			lang.set("Command.NoPerm", "'&cYou dont have Permission to do that!");
			lang.set("Command.Language.Get", "The Language is %lang%.");
			lang.set("Command.Language.Set", "The Language was set to %lang%!");
			lang.set("Command.Language.Error", "The Language %lang% doesn't exists!");
			lang.set("Command.Autobackup.Interval.Get", "The current Autobackupinterval is: %interval%");
			lang.set("Command.Autobackup.Interval.Set", "Setting Autobackupinterval from: %interval_old% to: %interval_new%...");
			lang.set("Command.Autobackup.Restart", "Restarting Autobackupthread...");
			lang.set("Command.Autobackup.Start", "Starting Autobackupthread...");
			lang.set("Command.Autobackup.Stop", "Stopping Autobackupthread...");
			lang.set("Command.Autosave.Interval.Get", "The current Autosaveinterval is: %interval%");
			lang.set("Command.Autosave.Interval.Set", "Setting Autosaveinterval from: %interval_old% to: %interval_new%...");
			lang.set("Command.Autosave.Restart", "Restarting Autosavethread...");
			lang.set("Command.Autosave.Start", "Starting Autosavethread...");
			lang.set("Command.Autosave.Stop", "Stopping Autosavethread...");
			lang.set("Command.Automessage.Interval.Get", "The current Automessageinterval is: %interval%");
			lang.set("Command.Automessage.Interval.Set", "Setting Automessageinterval from: %interval_old% to: %interval_new%...");
			lang.set("Command.Automessage.Restart", "Restarting Automessagethread...");
			lang.set("Command.Automessage.Start", "Starting Automessagethread...");
			lang.set("Command.Automessage.Stop", "Stopping Automessagethread...");
			lang.set("Command.Automessage.Add", "A new Automessage was added at index %index%.");
			lang.set("Command.Automessage.Remove", "The Automessage with Index %index% was removed.");
			lang.set("Command.Automessage.Get", "The Automessage with Index %index% contains:");
			lang.set("Command.Bukkit.Config.NotFound", "The entry %entry% was not found.");
			lang.set("Command.Bukkit.Config.Get", "The entry %entry% has the value: &8%value%.");
			lang.set("Command.Bukkit.Config.Set", "The entry %entry% was set to: &8%value%.");
			lang.set("Command.Player.UnknownPlayer", "Unknown Player!");
			lang.set("Command.Player.SpecifyPlayer", "Please specify a Player!");
			lang.set("Command.Player.Cmd.Send", "Successfully send Command!");
			lang.set("Command.Player.Cmd.Error", "Error sending Command!");
			lang.set("Command.Player.Displayname.Get.Your", "Your Displayname is: %displayname%.");
			lang.set("Command.Player.Displayname.Get.Other", "The Displayname of %player% is: %displayname%.");
			lang.set("Command.Player.Displayname.Set.Your", "Your Displayname was set to: %displayname%.");
			lang.set("Command.Player.Displayname.Set.Other", "The Displayname of %player% was set to: %displayname%.");
			lang.set("Command.Player.Displayname.Reset.Your", "Your Displayname was recessed to: %displayname%.");
			lang.set("Command.Player.Displayname.Reset.Other", "The Displayname of %player% was recessed to: %displayname%.");
			lang.set("Command.Player.Listname.Get.Your", "Your Listname is: %listname%.");
			lang.set("Command.Player.Listname.Get.Other", "The Listname of %player% is: %listname%.");
			lang.set("Command.Player.Listname.Set.Your", "Your Listname was set to: %listname%.");
			lang.set("Command.Player.Listname.Set.Other", "The Listname of %player% was set to: %listname%.");
			lang.set("Command.Player.Listname.Reset.Your", "Your Listname was recessed to: %listname%.");
			lang.set("Command.Player.Listname.Reset.Other", "The Listname of %player% was recessed to: %listname%.");
			lang.set("Command.Player.Exp.Error", "The Expvalue has to be an Integer!");
			lang.set("Command.Player.Exp.Get.Your", "Your Expvalue is: %exp%.");
			lang.set("Command.Player.Exp.Get.Other", "The Expvalue of %player% is: %exp%.");
			lang.set("Command.Player.Exp.Set.Your", "Your Expvalue was set to: %exp%.");
			lang.set("Command.Player.Exp.Set.Other", "The Expvalue of %player% was set to: %exp%.");
			lang.set("Command.Player.Exp.Add.Your", "%exp%Exp was added to your Expvalue.");
			lang.set("Command.Player.Exp.Add.Other", "%exp%Exp was added to the Expvalue of %player%.");
			lang.set("Command.Player.Health.Error", "The Healthvalue has to be an Integer!");
			lang.set("Command.Player.Health.TooMuch", "This Helpvalue is to big! Choose a smaller one.");
			lang.set("Command.Player.Health.Get.Your", "Your Healthvalue is: %health%.");
			lang.set("Command.Player.Health.Get.Other", "The Healthvalue of %player% is: %health%.");
			lang.set("Command.Player.Health.Set.Your", "Your Health was set to: %health%.");
			lang.set("Command.Player.Health.Set.Other", "The Health of %player% was set to: %health%.");
			lang.set("Command.Player.Health.Add.Your", "%health% was added to your Health.");
			lang.set("Command.Player.Health.Add.Other", "%health% was added to the Health of %player%.");
			lang.set("Command.Player.Health.Remove.Your", "%health% was removed from your Health.");
			lang.set("Command.Player.Health.Remove.Other", "%health% was removed from the Health of %player%.");
			lang.set("Command.Player.Hide.Already.Your", "You are already hidden!");
			lang.set("Command.Player.Hide.Already.Other", "%player% is already hidden!");
			lang.set("Command.Player.Gamemode.Error", "Gamemode has to be a Integer!");
			lang.set("Command.Player.Gamemode.Unknown", "Unknown Gamemode!");
			lang.set("Command.Player.Lastseen.Your", "You were lastseen on %lastseen_date% at %lastseen_time%");
			lang.set("Command.Player.Lastseen.Other", "%player% was lastseen on %lastseen_date% at %lastseen_time%");
			lang.set("Command.Player.Firstseen.Your", "You were firstseen on %firstseen%");
			lang.set("Command.Player.Firstseen.Other", "%player% was firstseen on %firstseen%");
			lang.set("Command.Player.Level.Error", "The Level has to be an Integer!");
			lang.set("Command.Player.Level.Get.Your", "Your Level is: %level%.");
			lang.set("Command.Player.Level.Get.Other", "The Level of %player% is: %level%.");
			lang.set("Command.Player.Level.Set.Your", "Your Level was set to: %level%.");
			lang.set("Command.Player.Level.Set.Other", "The Level of %player% was set to: %level%.");
			lang.set("Command.Player.Load", "%player%.dat loaded!");
			lang.set("Command.Player.Save", "%player%.dat saved!");
			lang.set("Command.Player.Show.Already.Your", "You are already visible!");
			lang.set("Command.Player.Show.Already.Other", "%player% is already visible!");
			lang.set("Command.Player.Time.Get.Your", "Your Time is: %time%.");
			lang.set("Command.Player.Time.Get.Other", "The Time of %player% is: %time%.");
			lang.set("Command.Player.Time.Set.Your", "Your Time was set to: %time%.");
			lang.set("Command.Player.Time.Set.Other", "The Time of %player% was set to: %time%.");
			lang.set("Command.Player.Time.Reset.Your", "Your Time was recessed to: %time%.");
			lang.set("Command.Player.Time.Reset.Other", "The Time of %player% was recessed to: %time%.");
			lang.set("Command.Plugin.DoesntExists", "This Plugin doesnt exists.");
			lang.set("Command.Plugin.Available", "Available Plugins: %pluginlist%");
			lang.set("Command.Plugin.Config.NoConfig", "This Plugin doesnt hava a Config!");
			lang.set("Command.Plugin.Config.NoEntry", "This Entry doesnt exists!");
			lang.set("Command.Plugin.Config.Get", "The Entry %entry% has the value: &8%value%.");
			lang.set("Command.Plugin.Config.Set", "The Entry %entry% was set from: &8%value_old% &fto: &8%value_new%.");
			lang.set("Command.Plugin.Disable.Already", "This Plugin is already disabled.");
			lang.set("Command.Plugin.Disable.Spout", "You shouldnt disable Spout, while Bukkit is running!");
			lang.set("Command.Plugin.Disable.Success", "Plugin %plugin% succesfully disabled.");
			lang.set("Command.Plugin.Enable.Already", "This Plugin is already enabled.");
			lang.set("Command.Plugin.Enable.Success", "Plugin %plugin% succesfully enabled.");
			lang.set("Command.Plugin.Restart.Spout", "You shouldnt restart Spout, while Bukkit is running!");
			lang.set("Command.Plugin.Restart.Success", "Plugin %plugin% succesfully restarted.");
			
			lang.save(langFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String translate(String key) {
		return lang.getString(key);
	}

	@Override
	public File getFile() {
		return langFile;
	}

	@Override
	public YamlConfiguration getKeys() {
		return lang;
	}
	
	@Override
	public String getName() {
		return "de_DE";
	}

	@Override
	public void set(String key, String value) {
		if (lang.get(key) == null) lang.set(key, value);
	}

}
