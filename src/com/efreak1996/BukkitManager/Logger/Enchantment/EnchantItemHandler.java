package com.efreak1996.BukkitManager.Logger.Enchantment;

import java.io.File;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.efreak1996.BukkitManager.Logger.BmLoggerConfiguration;

public class EnchantItemHandler extends BmEnchantmentHandler {

	public EnchantItemHandler(EnchantItemLogger arg1logger) {
		super(new File("Enchantment" + File.separator + "EnchantItem.log"), arg1logger, BmLoggerConfiguration.get("Enchantment.EnchantItem.File"), BmLoggerConfiguration.get("Enchantment.EnchantItem.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Enchantment.EnchantItem").replaceAll("%eventname%", (String) values.get("EventName"));
		return msg;
	}
}
