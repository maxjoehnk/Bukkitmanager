package org.efreak.bukkitmanager.logger.block;

import java.io.File;

import org.efreak.bukkitmanager.logger.BmLogger;
import org.efreak.bukkitmanager.logger.LoggingHandler;

public abstract class BlockHandler extends LoggingHandler {

    public BlockHandler(File arg1File, BmLogger arg2Logger,
            boolean arg3FileLogging, boolean arg4DatabaseLogging) {
        super(arg2Logger, arg3FileLogging, arg4DatabaseLogging);
        eventType = "Block";
        setupFile(arg1File);
    }
}
