package org.efreak.bukkitmanager.logger.block;

import org.efreak.bukkitmanager.logger.BmLogger;

public abstract class BlockLogger extends BmLogger {
	public BlockLogger(String event) {
		super(event + "Logger", "Block");
	}
}
