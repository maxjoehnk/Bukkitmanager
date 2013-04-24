package org.efreak.bukkitmanager.logger.enchantment;

import java.io.File;

import org.efreak.bukkitmanager.logger.LoggingHandler;

public abstract class EnchantmentHandler extends LoggingHandler {

    public EnchantmentHandler(File arg1File, EnchantmentLogger arg2Logger,
            boolean arg3FileLogging, boolean arg4DatabaseLogging) {
        super(arg2Logger, arg3FileLogging, arg4DatabaseLogging);
        eventType = "Enchantment";
        setupFile(arg1File);
    }
}
