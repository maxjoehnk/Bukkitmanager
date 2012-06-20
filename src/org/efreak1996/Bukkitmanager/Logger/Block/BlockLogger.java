package org.efreak1996.Bukkitmanager.Logger.Block;

import org.efreak1996.Bukkitmanager.Logger.BmLogger;

public abstract class BlockLogger extends BmLogger {
	public BlockLogger(String event) {
		super(event + "Logger", "Block");
	}
}
