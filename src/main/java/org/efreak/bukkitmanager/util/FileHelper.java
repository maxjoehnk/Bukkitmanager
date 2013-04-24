package org.efreak.bukkitmanager.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

public class FileHelper {

    private static Configuration config;
    private static IOManager io;
    private static File bukkitDir, pluginsDir, pluginDir, updateDir, backupDir,
            tempBackupDir, scriptDir, tutorialsDir;
    private static File bukkitFile, pluginFile;

    static {
        config = Bukkitmanager.getConfiguration();
        io = Bukkitmanager.getIOManager();
        pluginDir = Bukkitmanager.getInstance().getDataFolder()
                .getAbsoluteFile();
        pluginFile = Bukkitmanager.getPluginFile();
        pluginsDir = pluginDir.getParentFile();
        bukkitDir = pluginsDir.getParentFile();
        if (new File(bukkitDir, "craftbukkit.jar").exists()) bukkitFile = new File(
                bukkitDir, "craftbukkit.jar");
        else if (config.contains("General.BukkitFile")) {
            if (new File(bukkitDir, config.getString("General.BukkitFile"))
                    .exists()) bukkitFile = new File(bukkitDir,
                    config.getString("General.BukkitFile"));
            else io.sendConsoleError(io.translate("Autobackup.JarDoesntExists")
                    .replaceAll("%file%",
                            config.getString("General.BukkitFile")));
        } else {
            File[] bukkitFolderFiles = bukkitDir.listFiles(new FileFilter(
                    ".jar"));
            if (bukkitFolderFiles.length > 1) io.sendConsoleError(io
                    .translate("Autobackup.MultipleJars"));
            else if (bukkitFolderFiles.length == 0) io.sendConsoleError(io
                    .translate("Autobackup.NoJar"));
            else bukkitFile = bukkitFolderFiles[0];
        }
        updateDir = Bukkitmanager.getInstance().getServer()
                .getUpdateFolderFile();
        // backupDir = new
        // File(parsePath(config.getString("Autobackup.BackupDir")));
        backupDir = new File(bukkitDir,
                config.getString("Autobackup.BackupDir"));
        // tempBackupDir = new
        // File(parsePath(config.getString("Autobackup.TempBackupDir")));
        tempBackupDir = new File(bukkitDir,
                config.getString("Autobackup.TempBackupDir"));
        scriptDir = new File(pluginDir, "scripts");
        tutorialsDir = new File(pluginDir, "tutorials");
    }

    public static void setupFolderStructure() {
        new File(pluginDir + File.separator + "lang").mkdir();
        backupDir.mkdirs();
        tempBackupDir.mkdirs();
        updateDir.mkdirs();
        scriptDir.mkdirs();
        // tutorialsDir.mkdirs();
        try {
            FileUtils.cleanDirectory(tempBackupDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String parsePath(String input) {
        input = input.replaceAll("%bukkitdir%", bukkitDir.getPath());
        input = input.replaceAll("%pluginsdir%", pluginsDir.getPath());
        input = input.replaceAll("%plugindir%", pluginDir.getPath());
        input = input.replaceAll("%updatedir%", updateDir.getPath());
        if (backupDir != null) input = input.replaceAll("%backupdir%",
                backupDir.getPath());
        if (tempBackupDir != null) input = input.replaceAll("%tempbackupdir%",
                backupDir.getPath());
        return input;
    }

    public static File getBukkitDir() {
        return bukkitDir;
    }

    public static File getPluginsDir() {
        return pluginsDir;
    }

    public static File getPluginDir() {
        return pluginDir;
    }

    public static File getUpdateDir() {
        return updateDir;
    }

    public static File getBackupDir() {
        return backupDir;
    }

    public static File getScriptsDir() {
        return scriptDir;
    }

    public static File getTutorialsDir() {
        return tutorialsDir;
    }

    public static File getTempBackupDir() {
        return tempBackupDir;
    }

    public static File getBukkitFile() {
        return bukkitFile;
    }

    public static File getPluginFile() {
        return pluginFile;
    }
}
