package org.efreak1996.Bukkitmanager.Logger.Enchantment;

import org.efreak1996.Bukkitmanager.Logger.BmLogger;

public abstract class EnchantmentLogger extends BmLogger {
	public EnchantmentLogger(String event) {
		super(event + "Logger", "Enchantment");
	}
}
