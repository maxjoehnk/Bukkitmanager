package com.efreak1996.BukkitManager.Logger.Enchantment;

import java.io.File;
import java.util.HashMap;

import com.efreak1996.BukkitManager.Logger.BmHandler;

public abstract class BmEnchantmentHandler extends BmHandler {
	
	public BmEnchantmentHandler(File arg1File, BmEnchantmentLogger arg2Logger, boolean arg3FileLogging, boolean arg4DatabaseLogging) {
		super(arg2Logger, arg3FileLogging, arg4DatabaseLogging);
		eventType = "Enchantment";
		setupFile(arg1File);
	}
	
	public void logDb(HashMap<String, Object> values) {
		db.logBlock(values);
	}
}
