package org.efreak.bukkitmanager.logger.enchantment;

import org.efreak.bukkitmanager.logger.BmLogger;

public abstract class EnchantmentLogger extends BmLogger {
	public EnchantmentLogger(String event) {
		super(event + "Logger", "Enchantment");
	}
}
