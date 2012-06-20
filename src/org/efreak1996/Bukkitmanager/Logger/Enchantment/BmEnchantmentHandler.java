package org.efreak1996.Bukkitmanager.Logger.Enchantment;

import java.io.File;

import org.efreak1996.Bukkitmanager.Logger.BmHandler;


public abstract class BmEnchantmentHandler extends BmHandler {
	
	public BmEnchantmentHandler(File arg1File, BmEnchantmentLogger arg2Logger, boolean arg3FileLogging, boolean arg4DatabaseLogging) {
		super(arg2Logger, arg3FileLogging, arg4DatabaseLogging);
		eventType = "Enchantment";
		setupFile(arg1File);
	}
}
