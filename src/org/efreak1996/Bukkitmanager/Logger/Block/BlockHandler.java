package org.efreak1996.Bukkitmanager.Logger.Block;

import java.io.File;

import org.efreak1996.Bukkitmanager.Logger.LoggingHandler;


public abstract class BlockHandler extends LoggingHandler {
	
	public BlockHandler(File arg1File, BlockLogger arg2Logger, boolean arg3FileLogging, boolean arg4DatabaseLogging) {
		super(arg2Logger, arg3FileLogging, arg4DatabaseLogging);
		eventType = "Block";
		setupFile(arg1File);
	}
}
