package org.efreak.bukkitmanager.language;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.efreak.bukkitmanager.Bukkitmanager;

public class de extends Language {

	private static YamlConfiguration lang;
	private static File langFile;
	
	@Override
	public void createLanguageFile() {
		langFile = new File(Bukkitmanager.getInstance().getDataFolder(), "lang/de.lang");
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
			
			set("Plugin.Done", "&aFertig!");
			set("Plugin.LoadingCommands", "&2Lade Befehle...");
			set("Plugin.CommandsLoaded", "&aBefehle wurden erfolgreich geladen!");
			set("Plugin.FirstRun", "Es sieht so aus als waere dies das erste Mal das Bukkitmanager ausgefuehrt wird. Um Bukkitmanager zu konfigurieren kannst du /bm install ausfuehren...");
			set("Plugin.Debug", "Bukkitmanager wird im Debug Mode ausgef�hrt.");

			set("Plugin.Load.Error.UnknownDependency", "Das Plugin konnte nicht geladen werden: Unbekannte Abhaengigkeit");
			set("Plugin.Load.Error.InvalidPlugin", "Das Plugin konnte nicht geladen werden: Fehlerhaftes Plugin");
			set("Plugin.Load.Error.InvalidDescription", "Das Plugin konnte nicht geladen werden: Fehlerhafte Plugin Beschreibung");
			
			set("PluginUpdater.FetchingPlugins", "Fetching plugins...");
			set("PluginUpdater.CheckingUpdates", "Suche nach Updates...");
			set("PluginUpdater.UpdatesAvailable", "Es gibt Updates f�r einige Plugins. Bitte f�hre /bm plugin update aus");
			
			set("AutoUpdater.NewVersion", "Es gibt eine neue Version: %name%");
			set("AutoUpdater.Running", "Installiert ist %name%");
			set("AutoUpdater.UpToDate", "Keine Updates gefunden!");
			
			set("Player.Login.Hidden", "Du bist unsichtbar!");
			
			set("Permissions.ForceSuper", "&2Superperms forced!");
			set("Permissions.Found", "&a%perms% unterstuetzung aktiviert!");
			set("Permissions.NoPerms", "&eKein Permissionssystem gefunden!");
			set("Permissions.OP", "&aNutze OP-Rechte fuer Befehle!");
			
			set("Autobackup.BackupInProgress", "&eMultiple concurrent backup attempted! Autobackup interval is likely too short!");
			set("Autobackup.NoPlayer", "&eUeberspringe Backup, da keine Spieler online sind.");
			set("Autobackup.MultipleJars", "&cEs wurden mehrere .jar Dateien im Rootordner gefunden. Bitte den Dateinamen in der config.yml angeben!");
			set("Autobackup.NoJar", "&cKeine .jar Datei im Rootordner gefunden! Bitte den Pfad und Dateinamen in der config.yml angeben!");
			set("Autobackup.JarDoesntExists", "&cDie Datei %file% existiert nicht. Bitte ueberpruefe die config.yml!");
			set("Autobackup.Compressing.Plugins", "&2Komprimiere Plugins...");
			set("Autobackup.Compressing.World", "&2Komprimiere Welt: %world%...");
			set("Autobackup.Compressing.Bukkit", "&2Komprimiere %jar%...");
			set("Autobackup.Warn", "Autobackup in %timeleft% sek.");
			set("Autobackup.Notification.Start", "&aAutobackup gestartet!");
			set("Autobackup.Notification.Finish", "&aAutobackup fertig gestellt!");
			
			set("Autosave.Saving.Players", "&2Speichere Spieler...");
			set("Autosave.Saved.Players", "&aSpieler gespeichert!");
			set("Autosave.Saving.World", "&2Speichere Welt: %world%...");
			set("Autosave.Saved.Worlds", "&a%worlds% Welten gespeichert!");
			set("Autosave.SaveInProgress", "&eMultiple concurrent saves attempted! Autosave interval is likely too short!");
			set("Autosave.NoPlayer", "&eSkipping save, no players online.");
			set("Autosave.Warn", "Autosave in %timeleft% sec.");
			set("Autosave.Notification.Start", "&aAutosave gestartet!");
			set("Autosave.Notification.Finish", "&aAutosave fertig gestellt!");
			
			set("Downloader.Downloading", "&2Downloade %file% (%filesize%)");
			set("Downloader.Downloaded", "&aDownload von %file% fertig gestellt.");
			set("Downloader.Error", "&cFehler beim download von %file%.");
			
			set("Thread.Start", "&a%thread% gestartet! Der Interval ist %interval% Sekunden.");
			set("Thread.Stopping", "&2Stoppe %thread%...");
			set("Thread.Stop", "&a%thread% erfolgreich gestoppt!");
			set("Thread.Error", "&cKonnte den %thread% nicht stoppen!");
			
			set("Command.FewArgs", "&cZu wenig Argumente");
			set("Command.ManyArgs", "&cZu viele Argumente");
			set("Command.Usage", "Usage: %usage%");
			set("Command.NoPerm.Player", "&cDu hast nicht die Rechte dies zu tun!");
			set("Command.NoPerm.Console", "&e%player% hat versucht den Befehl &8%cmd% &fauszufuehren!");
			set("Command.Language.Get", "Die aktuelle Sprache ist %lang%.");
			set("Command.Language.Set", "&aDie Sprache wurde geaendert zu %lang%!");
			set("Command.Language.Error", "&cDie Sprache %lang% existiert nicht!");
			set("Command.Autobackup.Interval.Get", "Der aktuelle Autobackupinterval ist: %interval%");
			set("Command.Autobackup.Interval.Set", "Der Autobackupinterval wurde geaendert von: %interval_old% zu: %interval_new%!");
			set("Command.Autobackup.Restart", "Der Autobackupthread wurde neugestartet!");
			set("Command.Autobackup.Start", "Der Autobackupthread wurde gestartet!");
			set("Command.Autobackup.Stop", "Der Autobackupthread wurde gestoppt!");
			set("Command.Autosave.Interval.Get", "Der aktuelle Autosaveinterval ist: %interval%");
			set("Command.Autosave.Interval.Set", "Der Autosaveinterval wurde geaendert von: %interval_old% zu: %interval_new%!");
			set("Command.Autosave.Restart", "Der Autosavethread wurde neugestartet!");
			set("Command.Autosave.Start", "Der Autosavethread wurde gestartet!");
			set("Command.Autosave.Stop", "Der Autosavethread wurde gestoppt!");
			set("Command.Automessage.Interval.Get", "Der aktuelle Automessageinterval ist: %interval%");
			set("Command.Automessage.Interval.Set", "Der Automessageinterval wurde geaendert von: %interval_old% zu: %interval_new%!");
			set("Command.Automessage.Restart", "Der Automessagethread wurde neugestartet!");
			set("Command.Automessage.Start", "Der Automessagethread wurde gestartet!");
			set("Command.Automessage.Stop", "Der Automessagethread wurde gestoppt!");
			set("Command.Automessage.Add", "&aEine neue Nachricht wurde unter dem Index %index% hinzugefuegt.");
			set("Command.Automessage.Remove", "&aDie Nachricht mit dem Index %index% wurde entfernt.");
			set("Command.Automessage.Get", "Die Nachricht mit dem Index %index% enthaelt:");
			set("Command.Bukkit.Config.NotFound", "&cDer Eintrag %entry% wurde nicht gefunden.");
			set("Command.Bukkit.Config.Get", "Der Eintrag %entry% hat den Wert: &8%value%.");
			set("Command.Bukkit.Config.Set", "&aDer Wert des Eintrages %entry% wurde geaendert zu: &8%value%.");
			set("Command.Bukkit.Config.List", "Die server.properties enthaelt: %items%");
			set("Command.Player.UnknownPlayer", "&cUnbekannter Spieler!");
			set("Command.Player.SpecifyPlayer", "&cBitte gib einen Spieler an!");
			set("Command.Player.Cmd.Send", "&aDer Befehl wurde erfolgreich gesendet!");
			set("Command.Player.Cmd.Error", "&cFehler beim senden des Befehls!");
			set("Command.Player.Displayname.Get.Your", "Dein Displayname ist: %displayname%.");
			set("Command.Player.Displayname.Get.Other", "Der Displayname von %player% ist: %displayname%.");
			set("Command.Player.Displayname.Set.Your", "Dein Displayname wurde geaendert zu: %displayname%.");
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
			set("Command.Player.Has.Your.Has", "You have the permissions node %node%");
			set("Command.Player.Has.Your.Hasnt", "You don't have the permissions node %node%");
			set("Command.Player.Has.Other.Has", "%player% has the permissions node %node");
			set("Command.Player.Has.Other.Hasnt", "%player% doesn't has the permissions node %node%");
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
			set("Command.Player.Firstseen.Your", "You were firstseen on %firstseen_date% at %firstseen_time%");
			set("Command.Player.Firstseen.Other", "%player% was firstseen on %firstseen_date% at %firstseen_time%");
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
			set("Command.Bukkit.Config.List", "Die config.yml von %plugin% enthält: %items%");
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
			
			set("CustomMessages.Loading", "&2Loading Custom-Messages...");
			set("CustomMessages.Loaded", "&a%msgCount% Custom-Messages loaded!");
			
			set("Logger.Loading", "&2Loading Logger...");
			set("Logger.Loaded", "&aLogger loaded!");
			set("Logger.Block.BlockBreak", "");
			set("Logger.Block.BlockBurn", "");
			
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
		return "de";
	}

	@Override
	public void set(String key, String value) {
		if (lang.get(key) == null) lang.set(key, value);
	}
	
}
