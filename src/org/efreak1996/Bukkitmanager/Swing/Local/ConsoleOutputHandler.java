package org.efreak1996.Bukkitmanager.Swing.Local;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.efreak1996.Bukkitmanager.Bukkitmanager;

public class ConsoleOutputHandler extends Handler {

	public static List<LogRecord> logs;
	
	public void initialize() {
		logs = new ArrayList<LogRecord>();
		Bukkitmanager.getInstance().getServer().getLogger().addHandler(this);
	}
	
	@Override
	public void publish(LogRecord record) {
		logs.add(record);
	}

	@Override
	public void flush() {}

	@Override
	public void close() throws SecurityException {}

}
