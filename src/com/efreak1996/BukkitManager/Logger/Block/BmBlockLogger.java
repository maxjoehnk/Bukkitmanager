package com.efreak1996.BukkitManager.Logger.Block;

import com.efreak1996.BukkitManager.Logger.BmLogger;

public abstract class BmBlockLogger extends BmLogger {
	public BmBlockLogger(String event) {
		super(event + "Logger", "Block");
	}
}
