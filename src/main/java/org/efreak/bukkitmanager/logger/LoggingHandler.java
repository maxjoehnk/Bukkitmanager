package org.efreak.bukkitmanager.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Database;
import org.efreak.bukkitmanager.IOManager;

public abstract class LoggingHandler extends Handler {

    public static Configuration config;
    public static Database db;
    public static IOManager io;
    public FileOutputStream fileOutputStream;
    public PrintWriter printWriter;
    public File file;
    public File dir;
    public boolean fileLogging;
    public boolean dbLogging;
    public String eventType;

    static {
        config = Bukkitmanager.getConfiguration();
        io = Bukkitmanager.getIOManager();
        db = Bukkitmanager.getDb();
    }

    public LoggingHandler(BmLogger arg1Logger, boolean arg2FileLogging,
            boolean arg3DatabaseLogging) {
        super();
        fileLogging = arg2FileLogging;
        dbLogging = arg3DatabaseLogging;
        this.setFormatter(new LogFormatter());
    }

    public void setupFile(File arg1File) {
        dir = new File(LoggingManager.getLogDir(), eventType).getAbsoluteFile();
        if (!dir.exists()) dir.mkdirs();
        file = new File(LoggingManager.getLogDir() + File.separator + arg1File)
                .getAbsoluteFile();
        try {
            if (!file.exists()) file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            printWriter = new PrintWriter(fileOutputStream, true);
        } catch (Exception e) {
            if (config.getDebug()) e.printStackTrace();
        }
    }

    public void publish(LogRecord arg1record) {
        if (!isLoggable(arg1record)) return;
        if (fileLogging) printWriter.println(this.getFormatter().format(
                arg1record));
    }

    public void flush() {
        printWriter.flush();
    }

    public void close() throws SecurityException {
        printWriter.close();
    }

    public abstract void logDb(HashMap<String, Object> values);

    public abstract String logFile(HashMap<String, Object> values);

    public void shutdown() {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (printWriter != null) {
            printWriter.flush();
            printWriter.close();
        }
    }
}
