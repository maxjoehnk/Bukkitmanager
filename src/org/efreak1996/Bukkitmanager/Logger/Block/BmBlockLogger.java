package org.efreak1996.Bukkitmanager.Logger.Block;

import org.efreak1996.Bukkitmanager.Logger.BmLogger;

public abstract class BmBlockLogger extends BmLogger {
	public BmBlockLogger(String event) {
		super(event + "Logger", "Block");
	}
}
