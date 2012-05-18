package com.efreak1996.BukkitManager.Logger.Enchantment;

import java.io.File;
import java.util.HashMap;

import org.bukkit.block.Block;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class PrepareItemEnchantHandler extends BmEnchantmentHandler {

	public PrepareItemEnchantHandler(PrepareItemEnchantLogger arg1logger) {
		super(new File("Enchantment" + File.separator + "PrepareItemEnchant.log"), arg1logger, BmLoggerConfiguration.get("Enchantment.PrepareItemEnchant.File"), BmLoggerConfiguration.get("Enchantment.PrepareItemEnchant.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Enchantment.PrepareItemEnchant").replaceAll("%eventname%", (String) values.get("EventName"));
		return msg;
	}
}
