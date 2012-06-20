package org.efreak1996.Bukkitmanager.Logger.Enchantment;

import org.efreak1996.Bukkitmanager.Logger.BmLogger;

public abstract class BmEnchantmentLogger extends BmLogger {
	public BmEnchantmentLogger(String event) {
		super(event + "Logger", "Enchantment");
	}
}
