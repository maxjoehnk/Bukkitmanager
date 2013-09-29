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
			set("Plugin.LoadingCommands", "&2Chargement des commandes...");
			set("Plugin.CommandsLoaded", "&aCommandes chargé avec succès!");
			set("Plugin.FirstRun", "Il semblerait que c'est la première fois que vous exécutez Bukkitmanager. Vous pouvez exécuter /bm install pour configurer Bukkitmanager...");
			set("Plugin.Debug", "Bukkitmanager runs in Debug Mode");
			
			set("Plugin.Load.Error.UnknownDependency", "Le plugin n'a pas pu être chargé : Dépendance inconnue");
			set("Plugin.Load.Error.InvalidPlugin", "Plugin couldn't be loaded: Invalid Plugin");
			set("Plugin.Load.Error.InvalidDescription", "Plugin couldn't be loaded: Invalid Plugin Description");
			
			set("PluginUpdater.FetchingPlugins", "Obtention de plugins...");
			set("PluginUpdater.CheckingUpdates", "Vérification des mises à jour...");
			set("PluginUpdater.UpdatesAvailable", "Des mises à jour pour certains de vos plugins sont disponibles. Veuillez exécuter /bm plugin update");
			
			set("AutoUpdater.NewVersion", "Nouvelle version disponible: %name%");
			set("AutoUpdater.Running", "Vous exécutez %name%");
			set("AutoUpdater.UpToDate", "Aucune mise à jour trouvée.");
			
			set("Player.Login.Hidden", "Vous êtes Invisible.");
			
			set("Permissions.ForceSuper", "&2Superperms forcées.");
			set("Permissions.Found", "&a%perms% soutenir activé.");
			set("Permissions.NoPerms", "&eAucun système de permissions n'a été trouvé.");
			set("Permissions.OP", "&aUtilisation des droits d'Opérateur pour les commandes.");
			
			set("Autobackup.BackupInProgress", "&ePlusieurs sauvegarde ont été tentées simultanément! L'intervalle d'AutoBackup est probablement trop courte.");
			set("Autobackup.NoPlayer", "&eSauvegarde ignorée : aucun joueur en ligne.");
			set("Autobackup.MultipleJars", "&cPlusieurs fichiers .JAR dans le dossier racine ont été détectés. Veuillez indiquer les noms dans le fichier config.yml!");
			set("Autobackup.NoJar", "&cAucun fichier .jar dans le dossier racine n'a été détecté! S'il vous plaît indiquer le chemin du fichier + son nom dans le config.yml!");
			set("Autobackup.JarDoesntExists", "&c%file% n'existe pas. Veuillez vérifier votre config.yml!");
			set("Autobackup.Compressing.Plugins", "&2Compression des plugins...");
			set("Autobackup.Compressing.World", "&2Compression du monde %world%...");
			set("Autobackup.Compressing.Bukkit", "&2Compression de %jar%...");
			set("Autobackup.Warn", "Sauvegarde automatique dans %timeleft% secondes.");
			set("Autobackup.Notification.Start", "&al'Autobackup a commencé.");
			set("Autobackup.Notification.Finish", "&al'Autobackup est terminée.");
			
			set("Autosave.Saving.Players", "&2Sauvegarde des joueurs...");
			set("Autosave.Saved.Players", "&aJoueurs sauvegardés.");
			set("Autosave.Saving.World", "&2Sauvegarde du monde %world%...");
			set("Autosave.Saved.Worlds", "&aMondes %worlds% sauvegardés.");
			set("Autosave.SaveInProgress", "&ePlusieurs sauvegardes ont été tentées simultanément! L'intervalle de sauvegarde automatique est probablement trop courte.");
			set("Autosave.NoPlayer", "&eEnregistrement ignorée : aucun joueur en ligne.");
			set("Autosave.Warn", "Autosave dans %timeleft% secondes.");
			set("Autosave.Notification.Start", "&aAutosave commencée.");
			set("Autosave.Notification.Finish", "&aAutosave finie.");
			
			set("Downloader.Downloading", "&2Téléchargement en cours de %file% (%filesize%)");
			set("Downloader.Downloaded", "&aTéléchargement de %file% terminé.");
			set("Downloader.Error", "&cUne erreur est survenue lors du téléchargement de : %file%.");
			
			set("Thread.Start", "&a%thread% commencé. L'intervalle est %interval% secondes");
			set("Thread.Stopping", "&2Arrêt de %thread%...");
			set("Thread.Stop", "&a%thread% est arrêté!");
			set("Thread.Error", "&cImpossible d'arrêter %thread%.");
			
			set("Command.FewArgs", "&cTrop peu d'arguments");
			set("Command.ManyArgs", "&cTrop d'arguments");
			set("Command.Usage", "Usage: %usage%");
			
			set("Command.NoPerm.Player", "&cErreur : Permissions insufisantes. Contactez l'administrateur.");
			set("Command.NoPerm.Console", "&e%player% a tenté d'exécuter &8%cmd%!");
			
			set("Command.Language.Get", "La langue est %lang%.");
			set("Command.Language.Set", "&aLe langue a été fixé à %lang%.");
			set("Command.Language.Error", "&cLa langue %lang% n'existe pas.");
			
			set("Command.Autobackup.Interval.Get", "L'intervalle actuelle d'Autobackup est: %interval%");
			set("Command.Autobackup.Interval.Set", "L'intervalle d'AutoBackup a étédéfini à: %interval_new%.");
			set("Command.Autobackup.Restart", "AutoBackup redémarré.");
			set("Command.Autobackup.Start", "Autobackup démarré.");
			set("Command.Autobackup.Stop", "AutoBackup arrêté.");
			
			set("Command.Autosave.Interval.Get", "L'intervalle de sauvegarde automatique est : %interval%");
			set("Command.Autosave.Interval.Set", "Intervalle de sauvegarde automatique défini à: %interval_new%!");
			set("Command.Autosave.Restart", "Autosave redémarré.");
			set("Command.Autosave.Start", "Autosave démarré.");
			set("Command.Autosave.Stop", "Autosave arrêté.");
			
			set("Command.Automessage.Interval.Get", "L'intervalle actuelle d'AutoMessage est: %interval%");
			set("Command.Automessage.Interval.Set", "Intervalle d'Automessage défini à: %interval_new%!");
			set("Command.Automessage.Restart", "Automessage Redémarré.");
			set("Command.Automessage.Start", "Automessage Démarré.");
			set("Command.Automessage.Stop", "Automessage Arrêté.");
			set("Command.Automessage.Add", "&aUn nouveau message a été ajouté à l'index %index%.");
			set("Command.Automessage.Remove", "&aLe message à index %index% a été supprimmé.");
			set("Command.Automessage.Get", "Le message à l'index %index% est:");
			
			set("Command.Bukkit.Config.NotFound", "&cL'entrée %entry% n'a pas été trouvé.");
			set("Command.Bukkit.Config.Get", "l'entrée %entry% a comme valeur: &8%value%.");
			set("Command.Bukkit.Config.Set", "&al'entrée %entry% a été défini à: &8%value%.");
			set("Command.Bukkit.Config.List", "Le server.properties contient: %items%");
			
			set("Command.Player.UnknownPlayer", "&cJoueur inconnu");
			set("Command.Player.SpecifyPlayer", "&cVeuillez spécifier un joueur");
			
			set("Command.Player.Cmd.Send", "&aCommande envoyé avec succès!");
			set("Command.Player.Cmd.Error", "&cErreur d'envoi de la commande!");
			
			set("Command.Player.Displayname.Get.Your", "Votre nom d'affichage est: %displayname%.");
			set("Command.Player.Displayname.Get.Other", "Le nom d'affichage de %player% est: %displayname%.");
			set("Command.Player.Displayname.Set.Your", "Votre nom d'affichage a été défini à: %displayname%.");
			set("Command.Player.Displayname.Set.Other", "Le nom d'affichage de %player% a été défini à: %displayname%.");
			set("Command.Player.Displayname.Reset.Your", "Votre nom d'affichage a été rétabli à: %displayname%.");
			set("Command.Player.Displayname.Reset.Other", "Le nom d'affichage de %player% a été rétabli à: %displayname%.");
			
			set("Command.Player.Listname.Get.Your", "Votre nom de liste est: %listname%.");
			set("Command.Player.Listname.Get.Other", "Le nom de liste de %player% est: %listname%.");
			set("Command.Player.Listname.Set.Your", "Votre nom de liste a été défini à: %listname%.");
			set("Command.Player.Listname.Set.Other", "Le nom de liste de %player% a été défini à: %listname%.");
			set("Command.Player.Listname.Reset.Your", "Votre nom de liste a été rétabli à: %listname%.");
			set("Command.Player.Listname.Reset.Other", "Le nom de liste de %player% a été rétabli àlistname%.");
			
			set("Command.Player.Exp.Error", "&cLa valeur EXP doit être un nombre entier!");
			set("Command.Player.Exp.Get.Your", "Votre valeur EXP est: %exp%.");a laest ré-initialisé pour
			set("Command.Player.Exp.Get.Other", "La valeur de EXP %player% est: %exp%.");
			set("Command.Player.Exp.Set.Your", "Votre valeur EXP a été défini à: %exp%.");
			set("Command.Player.Exp.Set.Other", "La valeur de EXP %player% a été défini à: %exp%.");
			set("Command.Player.Exp.Add.Your", "%exp%Exp a été ajouté votre EXP.");
			set("Command.Player.Exp.Add.Other", "%exp%Exp a été ajouté é la valeur EXP de %player%.");
			
			set("Command.Player.Health.Error", "&cLa valeur de la santé doit être un nombre entier!");
			set("Command.Player.Health.TooMuch", "&cVotre santé est trop grande ! Choisissez un nombre entre 1 et 20.");
			set("Command.Player.Health.Get.Your", "Votre santé est à: %health%.");
			set("Command.Player.Health.Get.Other", "La valeur de la santé %player% est: %health%.");
			set("Command.Player.Health.Set.Your", "Votre état de santé a été fixé à: %health%.");
			set("Command.Player.Health.Set.Other", "La santé de %player% a été défini à: %health%.");
			set("Command.Player.Health.Add.Your", "%health% a été ajouté à votre santé.");
			set("Command.Player.Health.Add.Other", "%health% a été ajouté à la santé de %player%.");
			set("Command.Player.Health.Remove.Your", "%health% PV ont été retirés de votre santé.");
			set("Command.Player.Health.Remove.Other", "%health% PV ont été retirés de la santé de %player%.");
			
			set("Command.Player.Hide.You", "Vous êtes désormais invisible.");
			set("Command.Player.Hide.Other", "%player% est désormais invisible.");
			set("Command.Player.Hide.ByOther", "Vous avez été dissimulé par %player%!");
			set("Command.Player.Hide.Already.You", "&eVous êtes déjà invisible!");
			set("Command.Player.Hide.Already.Other", "&e%player% est déjà invisible!");
			set("Command.Player.Hide.Console.You", "%player% s'est dissimulé!");
			set("Command.Player.Hide.Console.Other", "%causer% a dissimulé %player%!");
			
			set("Command.Player.Gamemode.Error", "&cLe Gamemode doit être un entier.");
			set("Command.Player.Gamemode.Unknown", "&cgamemode inconnu! (0 : Survival, 1 : Création, 2 : Aventure");
			
			set("Command.Player.Lastseen.Your", "Vous avez été vu pour la dernière fois le %lastseen_date% à %lastseen_time%");
			set("Command.Player.Lastseen.Other", "%player% a été vu la dernière fois le %lastseen_date% à %lastseen_time%");
			
			set("Command.Player.Firstseen.Your", "Vous avez été vu pour la première fois le %firstseen%");
			set("Command.Player.Firstseen.Other", "%player% a été vu pour la première fois le %firstseen%");
			
			set("Command.Player.Level.Error", "&cLe niveau doit être un entier!");
			set("Command.Player.Level.Get.Your", "Votre niveau est: %level%.");
			set("Command.Player.Level.Get.Other", "Le niveau de %player% est: %level%.");
			set("Command.Player.Level.Set.Your", "Votre niveau a été défini à: %level%.");
			set("Command.Player.Level.Set.Other", "Le niveau de %player% a été défini à: %level%.");
			
			set("Command.Player.Load", "&a%player%.dat chargé.");
			set("Command.Player.Save", "&a%player%.dat sauvé.");
			
			set("Command.Player.Show.You", "Vous êtes visible à nouveau.");
			set("Command.Player.Show.Other", "Vous avez rendu %player% visible.");
			set("Command.Player.Show.ByOther", "%player% vous a rendu visible.");
			set("Command.Player.Show.Already.You", "&eVous êtes déjà visible.");
			set("Command.Player.Show.Already.Other", "&e%player% est déjé visible");
			set("Command.Player.Show.Console.You", "%player% se montre!");
			set("Command.Player.Show.Console.Other", "%causer% à rendu %player% visible.");
			
			set("Command.Player.Time.Get.Your", "Votre temps est: %time%.");
			set("Command.Player.Time.Get.Other", "Le temps de %player% est: %time%.");
			set("Command.Player.Time.Set.Your", "Votre temps a été défini à: %time%.");
			set("Command.Player.Time.Set.Other", "Le temps de %player% a été défini à: %time%.");
			set("Command.Player.Time.Reset.Your", "Votre temps est ré-initialisé à : %time%.");
			set("Command.Player.Time.Reset.Other", "Le temps de %player% est ré-initialisé à: %time%.");
			
			set("Command.Plugin.DoesntExists", "&cCe plugin n'existe pas.");
			set("Command.Plugin.Available", "Plugins disponibles: %pluginlist%");
			
			set("Command.Plugin.Config.NoConfig", "&cCe plugin n'a pas de configuration!");
			set("Command.Plugin.Config.NoEntry", "&cCette entrée n'existe pas");
			set("Command.Plugin.Config.Get", "l'entrée %entry% a la valeur suivante: &8%value%&f.");
			set("Command.Plugin.Config.Set", "&al'entrée %entry% a été mis de: &8%value_old% &fà: &8%value_new%&f.");
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
