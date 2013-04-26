package org.efreak.bukkitmanager.remoteserver;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Database;
import org.efreak.bukkitmanager.IOManager;

public class RemoteCommandHandler {

	protected static IOManager io;
	protected static Configuration config;
	protected static Database db;
	
	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
		db = Bukkitmanager.getDb();
	}
}
