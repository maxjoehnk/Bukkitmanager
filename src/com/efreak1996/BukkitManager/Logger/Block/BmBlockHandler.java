package com.efreak1996.BukkitManager.Logger.Block;

import java.io.File;
import java.util.HashMap;

import com.efreak1996.BukkitManager.Logger.BmHandler;

public abstract class BmBlockHandler extends BmHandler {
	
	public BmBlockHandler(File arg1File, BmBlockLogger arg2Logger, boolean arg3FileLogging, boolean arg4DatabaseLogging) {
		super(arg2Logger, arg3FileLogging, arg4DatabaseLogging);
		eventType = "Block";
		setupFile(arg1File);
	}
	
	public void logDb(HashMap<String, Object> values) {
		db.logBlock(values);
	}
}
