package com.efreak1996.BukkitManager.Logger.Enchantment;

import com.efreak1996.BukkitManager.Logger.BmLogger;

public abstract class BmEnchantmentLogger extends BmLogger {
	public BmEnchantmentLogger(String event) {
		super(event + "Logger", "Enchantment");
	}
}
