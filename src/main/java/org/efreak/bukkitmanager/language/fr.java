package org.efreak.bukkitmanager.language;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.efreak.bukkitmanager.Bukkitmanager;

public class fr extends Language {

	private static YamlConfiguration lang;
	private static File langFile;
	
	@Override
	public void createLanguageFile() {
		langFile = new File(Bukkitmanager.getInstance().getDataFolder(), "lang" + File.separator + "fr.lang");
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
			
			set("Plugin.Done", "&aFait!");
			set("Plugin.LoadingCommands", "&2Chargement commandes...");
			set("Plugin.CommandsLoaded", "&aCommandes chargé avec succès!");
			set("Plugin.FirstRun", "Cela semble être la première fois que vous exécutez Bukkitmanager. Vous pouvez exécuter /bm install pour configurer Bukkitmanager...");
			set("Plugin.Debug", "Bukkitmanager runs in Debug Mode");
			
			set("Plugin.Load.Error.UnknownDependency", "Plugin couldn't be loaded: Unknown Dependency");
			set("Plugin.Load.Error.InvalidPlugin", "Plugin couldn't be loaded: Invalid Plugin");
			set("Plugin.Load.Error.InvalidDescription", "Plugin couldn't be loaded: Invalid Plugin Description");
			
			set("PluginUpdater.FetchingPlugins", "Obtention de plugins...");
			set("PluginUpdater.CheckingUpdates", "Vérification des mises à jour...");
			set("PluginUpdater.UpdatesAvailable", "Des mises à jour pour certains de vos plugins disponibles. S'il vous plaît exécuter /bm plugin update");
			
			set("AutoUpdater.NewVersion", "Il ya une nouvelle version disponible: %name%");
			set("AutoUpdater.Running", "Vous exécutez %name%");
			set("AutoUpdater.UpToDate", "Aucune mise à jour trouvée!!");
			
			set("Player.Login.Hidden", "Vous êtes caché!");
			
			set("Permissions.ForceSuper", "&2Superperms forcé!");
			set("Permissions.Found", "&a%perms% soutenir enabled!");
			set("Permissions.NoPerms", "&ePas de système de permissions trouvé!");
			set("Permissions.OP", "&aUtilisation OP-droits pour les commandes!");
			
			set("Autobackup.BackupInProgress", "&ePlusieurs sauvegarde simultanée tenté! Autobackup intervalle est probablement trop court!");
			set("Autobackup.NoPlayer", "&eSaut de sauvegarde, pas de joueurs en ligne.");
			set("Autobackup.MultipleJars", "&cPlusieurs fichiers JAR. Dans le dossier racine détectée. S'il vous plaît indiquer Nom dans le config.yml!");
			set("Autobackup.NoJar", "&cNon. Fichier jar dans le dossier racine détecté! S'il vous plaît indiquer Filepath + Nom dans le config.yml!");
			set("Autobackup.JarDoesntExists", "&c%file% n'existe pas. S'il vous plaît vérifier votre config.yml!");
			set("Autobackup.Compressing.Plugins", "&2Compression de plugins...");
			set("Autobackup.Compressing.World", "&2Compression monde: %world%...");
			set("Autobackup.Compressing.Bukkit", "&2Compression %jar%...");
			set("Autobackup.Warn", "Sauvegarde automatique dans %timeleft% seconde.");
			set("Autobackup.Notification.Start", "&aAutobackup a commencé!");
			set("Autobackup.Notification.Finish", "&aAutobackup fini!");
			
			set("Autosave.Saving.Players", "&2Les joueurs d'épargne...");
			set("Autosave.Saved.Players", "&aLes joueurs sauvé!");
			set("Autosave.Saving.World", "&2sauver le monde: %world%...");
			set("Autosave.Saved.Worlds", "&aSauvé %worlds% mondes!");
			set("Autosave.SaveInProgress", "&eSimultanée de plusieurs tenté sauve! Intervalle de sauvegarde automatique est probable trop court!");
			set("Autosave.NoPlayer", "&eSaut d'enregistrement, pas de joueurs en ligne.");
			set("Autosave.Warn", "Autosave dans %timeleft% seconde.");
			set("Autosave.Notification.Start", "&aAutosave commencé!");
			set("Autosave.Notification.Finish", "&aAutosave fini!");
			
			set("Downloader.Downloading", "&2téléchargement %file% (%filesize%)");
			set("Downloader.Downloaded", "&aTéléchargez des %file% fini.");
			set("Downloader.Error", "&cErreur de fichier téléchargement: %file%.");
			
			set("Thread.Start", "&a%thread% commencé! intervalle est %interval% seconde");
			set("Thread.Stopping", "&2arrêt %thread%...");
			set("Thread.Stop", "&aréussi à arrêter %thread%!");
			set("Thread.Error", "&cImpossible d'arrêter %thread%");
			
			set("Command.FewArgs", "&cArguments trop peu");
			set("Command.ManyArgs", "&cArguments trop nombreux");
			set("Command.Usage", "Usage: %usage%");
			
			set("Command.NoPerm.Player", "&cVous n'avez pas la permission de faire cela!");
			set("Command.NoPerm.Console", "&e%player% a essay� d'ex�cuter la commande &8%cmd%!");
			
			set("Command.Language.Get", "La langue est %lang%.");
			set("Command.Language.Set", "&aLe langage a �t� fix� � %lang%!");
			set("Command.Language.Error", "&cLa langue %lang% ne pas exister!");
			
			set("Command.Autobackup.Interval.Get", "L'intervalle courant Autobackup est: %interval%");
			set("Command.Autobackup.Interval.Set", "D�finir l'intervalle de sauvegarde automatique: %interval_old% �: %interval_new%!");
			set("Command.Autobackup.Restart", "Red�marr� fil Autobackup!");
			set("Command.Autobackup.Start", "Commenc� fil Autobackup!");
			set("Command.Autobackup.Stop", "Arr�t� fil Autobackup!");
			
			set("Command.Autosave.Interval.Get", "L'intervalle de sauvegarde automatique est en cours: %interval%");
			set("Command.Autosave.Interval.Set", "D�finir l'intervalle de sauvegarde automatique � partir: %interval_old% �: %interval_new%!");
			set("Command.Autosave.Restart", "Autosave fil red�marr�!");
			set("Command.Autosave.Start", "Autosave commenc� fil!");
			set("Command.Autosave.Stop", "Fil Autosave arr�t�!");
			
			set("Command.Automessage.Interval.Get", "L'intervalle courant est Automessage: %interval%");
			set("Command.Automessage.Interval.Set", "D�finir l'intervalle de Automessage: %interval_old% �: %interval_new%!");
			set("Command.Automessage.Restart", "Red�marr� fil Automessage!");
			set("Command.Automessage.Start", "Commenc� fil Automessage!");
			set("Command.Automessage.Stop", "Arr�t� fil Automessage!");
			set("Command.Automessage.Add", "&aUne nouvelle Automessage a �t� ajout� � l'index %index%.");
			set("Command.Automessage.Remove", "&aLe Automessage avec index %index% a �t� �limin�.");
			set("Command.Automessage.Get", "L'indice de Automessage %index% contient:");
			
			set("Command.Bukkit.Config.NotFound", "&cl'entr�e %entry% n'a pas �t� trouv�.");
			set("Command.Bukkit.Config.Get", "l'entr�e %entry% a la valeur: &8%value%.");
			set("Command.Bukkit.Config.Set", "&al'entr�e %entry% a �t� fix� �: &8%value%.");
			set("Command.Bukkit.Config.List", "Le server.properties contient: %items%");
			
			set("Command.Player.UnknownPlayer", "&cLecteur inconnu!");
			set("Command.Player.SpecifyPlayer", "&cS'il vous pla�t sp�cifier un lecteur!");
			
			set("Command.Player.Cmd.Send", "&aCommande envoy� avec succ�s!");
			set("Command.Player.Cmd.Error", "&cErreur d'envoi de commande!");
			
			set("Command.Player.Displayname.Get.Your", "Votre nom d'affichage est: %displayname%.");
			set("Command.Player.Displayname.Get.Other", "Le nom d'affichage de %player% est: %displayname%.");
			set("Command.Player.Displayname.Set.Your", "Votre nom d'affichage a �t� fix� �: %displayname%.");
			set("Command.Player.Displayname.Set.Other", "Le nom d'affichage de %player% a �t� fix� �: %displayname%.");
			set("Command.Player.Displayname.Reset.Your", "Votre nom d'affichage a �t� suspendue pour: %displayname%.");
			set("Command.Player.Displayname.Reset.Other", "Le nom d'affichage de %player% a �t� �vid�e pour: %displayname%.");
			
			set("Command.Player.Listname.Get.Your", "Votre nom de liste est: %listname%.");
			set("Command.Player.Listname.Get.Other", "Le nom de liste de %player% est: %listname%.");
			set("Command.Player.Listname.Set.Your", "Votre nom de liste a �t� fix�e �: %listname%.");
			set("Command.Player.Listname.Set.Other", "Le nom de liste de %player% a �t� fix� �: %listname%.");
			set("Command.Player.Listname.Reset.Your", "Votre nom de liste a �t� suspendue pour: %listname%.");
			set("Command.Player.Listname.Reset.Other", "Le nom de liste de %player% a �t� �vid�e pour: %listname%.");
			
			set("Command.Player.Exp.Error", "&cLa valeur exp doit �tre un entier!");
			set("Command.Player.Exp.Get.Your", "Votre valeur exp est: %exp%.");
			set("Command.Player.Exp.Get.Other", "La valeur de exp %player% est: %exp%.");
			set("Command.Player.Exp.Set.Your", "Votre valeur exp a �t� fix� �: %exp%.");
			set("Command.Player.Exp.Set.Other", "La valeur de exp %player% a �t� fix� �: %exp%.");
			set("Command.Player.Exp.Add.Your", "%exp%Exp a �t� ajout� � votre valeur de exp.");
			set("Command.Player.Exp.Add.Other", "%exp%Exp a �t� ajout� � la valeur de exp %player%.");
			
			set("Command.Player.Health.Error", "&cLa valeur de la sant� doit �tre un nombre entier!");
			set("Command.Player.Health.TooMuch", "&cCette valeur est trop grande aide! Choisissez une plus petite.");
			set("Command.Player.Health.Get.Your", "Votre sant� est la valeur: %health%.");
			set("Command.Player.Health.Get.Other", "La valeur de la sant� %player% est: %health%.");
			set("Command.Player.Health.Set.Your", "Votre �tat de sant� a �t� fix� �: %health%.");
			set("Command.Player.Health.Set.Other", "La sant� des %player% a �t� fix� �: %health%.");
			set("Command.Player.Health.Add.Your", "%health% a �t� ajout� � votre sant�.");
			set("Command.Player.Health.Add.Other", "%health% a �t� ajout� � la sant� de %player%.");
			set("Command.Player.Health.Remove.Your", "%health% a �t� supprim� de votre sant�.");
			set("Command.Player.Health.Remove.Other", "%health% a �t� retir� de la sant� de %player%.");
			
			set("Command.Player.Hide.You", "Vous avez �t� cach� avec succ�s!");
			set("Command.Player.Hide.Other", "%player% a pu �tre cach�!");
			set("Command.Player.Hide.ByOther", "Vous avez �t� cach� par %player%!");
			set("Command.Player.Hide.Already.You", "&eVous �tes d�j� cach�!");
			set("Command.Player.Hide.Already.Other", "&e%player% est d�j� cach�!");
			set("Command.Player.Hide.Console.You", "%player% s'est cach�!");
			set("Command.Player.Hide.Console.Other", "%causer% a cach� %player%!");
			
			set("Command.Player.Gamemode.Error", "&cMode de jeu doit �tre un entier!");
			set("Command.Player.Gamemode.Unknown", "&cgamemode inconnu!");
			
			set("Command.Player.Lastseen.Your", "Vous avez �t� la derni�re fois sur %lastseen_date% at %lastseen_time%");
			set("Command.Player.Lastseen.Other", "%player% a �t� vu la derni�re fois sur %lastseen_date% at %lastseen_time%");
			
			set("Command.Player.Firstseen.Your", "Vous avez �t� la premi�re fois sur %firstseen%");
			set("Command.Player.Firstseen.Other", "%player% a d'abord �t� vu sur %firstseen%");
			
			set("Command.Player.Level.Error", "&cLe niveau doit �tre un entier!");
			set("Command.Player.Level.Get.Your", "Votre niveau est: %level%.");
			set("Command.Player.Level.Get.Other", "Le niveau de %player% est: %level%.");
			set("Command.Player.Level.Set.Your", "Votre niveau a �t� fix� �: %level%.");
			set("Command.Player.Level.Set.Other", "Le niveau de %player% a �t� fix� �: %level%.");
			
			set("Command.Player.Load", "&a%player%.dat charg�!");
			set("Command.Player.Save", "&a%player%.dat sauv�!");
			
			set("Command.Player.Show.You", "Vous avez �t� d�montr� avec succ�s!");
			set("Command.Player.Show.Other", "%player% a �t� d�montr� avec succ�s!");
			set("Command.Player.Show.ByOther", "On vous a montr� par %player%!");
			set("Command.Player.Show.Already.You", "&eVous �tes d�j� visibles!");
			set("Command.Player.Show.Already.Other", "&e%player% est d�j� visible!");
			set("Command.Player.Show.Console.You", "%player% s'est montr�e!");
			set("Command.Player.Show.Console.Other", "%causer% a montr� %player%!");
			
			set("Command.Player.Time.Get.Your", "Votre temps est: %time%.");
			set("Command.Player.Time.Get.Other", "Le temps de la %player% est: %time%.");
			set("Command.Player.Time.Set.Your", "Votre temps a �t� fix� �: %time%.");
			set("Command.Player.Time.Set.Other", "Le temps de la %player% a �t� fix� �: %time%.");
			set("Command.Player.Time.Reset.Your", "Votre temps est en retrait pour: %time%.");
			set("Command.Player.Time.Reset.Other", "Le temps de la %player% a �t� �vid�e pour: %time%.");
			
			set("Command.Plugin.DoesntExists", "&cCe plugin n'existe pas.");
			set("Command.Plugin.Available", "plugins disponibles: %pluginlist%");
			
			set("Command.Plugin.Config.NoConfig", "&cCe plugin n'a pas de config!");
			set("Command.Plugin.Config.NoEntry", "&cCette entr�e n'existe pas!");
			set("Command.Plugin.Config.Get", "l'entr�e %entry% a la valeur: &8%value%&f.");
			set("Command.Plugin.Config.Set", "&al'entr�e %entry% a �t� mis de: &8%value_old% &f�: &8%value_new%&f.");
			set("Command.Plugin.Config.List", "Le config.yml de of %plugin% contient: %items%");
			
			set("Command.Plugin.Disable.Already", "&cCe plugin est d�j� d�sactiv�.");
			set("Command.Plugin.Disable.Spout", "&eVous ne devez pas d�sactiver bec, tandis que Bukkit est en marche!");
			set("Command.Plugin.Disable.Success", "&aPlugin %plugin% d�sactiv� avec succ�s.");
			
			set("Command.Plugin.Enable.Already", "&cCe plugin est d�j� activ�.");
			set("Command.Plugin.Enable.Success", "&aPlugin %plugin% activ�e avec succ�s.");
			
			set("Command.Plugin.Restart.Spout", "&eVous ne devez pas red�marrer bec, tandis que Bukkit est en marche!");
			set("Command.Plugin.Restart.Success", "&aPlugin %plugin% red�marr� avec succ�s.");
			
			set("Command.Plugin.Load.Dir.Success", "&a%plugins% ont �t� charg�es avec succ�s � partir de %dir%!");
			set("Command.Plugin.Load.IsNoDir", "&c%dir% is no folder!");
			set("Command.Plugin.Load.DirDoesntExists", "&cle dossier %dir% ne pas exister!");
			set("Command.Plugin.Load.File.Success", "&a%plugin% a �t� charg� avec succ�s par fichier %file%!");
			set("Command.Plugin.Load.IsNoFile", "&c%file% n'est pas un fichier!");
			set("Command.Plugin.Load.FileDoesntExists", "&c%file% ne pas exister!");
			
			set("Command.Plugin.Update.UpdatesAvailable", "il est %count% mises � jour disponibles: %names%");
			
			set("CustomMessages.Loading", "&2Chargement Custom-Messages...");
			set("CustomMessages.Loaded", "&a%msgCount% Custom-Messages charg�e!");
			
			set("Logger.Loading", "&2Chargement Logger...");
			set("Logger.Loaded", "&aLogger charg�!");
			//set("Logger.Block.BlockBreak", "");
			//set("Logger.Block.BlockBurn", "");
			
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
		return "en";
	}

	@Override
	public void set(String key, String value) {
		if (lang.get(key) == null) lang.set(key, value);
	}
	
}
