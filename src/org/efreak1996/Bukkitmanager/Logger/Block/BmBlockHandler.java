package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;

import org.efreak1996.Bukkitmanager.Logger.BmHandler;


public abstract class BmBlockHandler extends BmHandler {
	
	public BmBlockHandler(File arg1File, BmBlockLogger arg2Logger, boolean arg3FileLogging, boolean arg4DatabaseLogging) {
		super(arg2Logger, arg3FileLogging, arg4DatabaseLogging);
		eventType = "Block";
		setupFile(arg1File);
	}
}
