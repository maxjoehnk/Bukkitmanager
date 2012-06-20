package org.efreak1996.Bukkitmanager.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.efreak1996.Bukkitmanager.BmConfiguration;
import org.efreak1996.Bukkitmanager.BmDatabase;
import org.efreak1996.Bukkitmanager.Util.BmIOManager;


public abstract class BmHandler extends Handler {
	
	public static BmConfiguration config;
	public static BmDatabase db;
	public static BmIOManager io;
	public FileOutputStream fileOutputStream;
	public PrintWriter printWriter;
	public File file;
	public File dir;
	public boolean fileLogging;
	public boolean dbLogging;
	public String eventType;

	public BmHandler(BmLogger arg2Logger, boolean arg3FileLogging, boolean arg4DatabaseLogging) {
		super();
		config = new BmConfiguration();
		db = new BmDatabase();
		io = new BmIOManager();
		fileLogging = arg3FileLogging;
		dbLogging = arg4DatabaseLogging;
		this.setFormatter(new BmLogFormatter());
	}
	
	public void setupFile(File arg1File) {
		dir = new File(BmLoggingManager.getLogDir(), eventType).getAbsoluteFile();
		if (!dir.exists()) dir.mkdirs();
		file = new File(BmLoggingManager.getLogDir() + File.separator + arg1File).getAbsoluteFile();
		try {
			if (!file.exists()) file.createNewFile();
			fileOutputStream = new FileOutputStream(file);
			printWriter = new PrintWriter(fileOutputStream, true);
		} catch(Exception e){
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	public void publish(LogRecord arg1record) {
		if (!isLoggable(arg1record)) return;
		if (fileLogging) 
			printWriter.println(
				this.getFormatter().format(arg1record));
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
