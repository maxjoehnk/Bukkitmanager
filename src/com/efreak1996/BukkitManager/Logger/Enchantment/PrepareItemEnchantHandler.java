package com.efreak1996.BukkitManager.Logger.Enchantment;

import java.io.File;
import java.util.Date;
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

	@Override
	public void logDb(HashMap<String, Object> values) {
		db.log("Log_PrepareItemEnchantEvent", "time, enchantBlock, cancelled", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
		//dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_PrepareItemEnchantEvent (time String NOT NULL, enchantBlock Block NOT NULL, cancelled Boolean NOT NULL, enchanter Player NOT NULL, enchantmentBonus Integer NOT NULL, expLevelCostsOffered Integer[] NOT NULL, inventory Inventory NOT NULL, item ItemStack NOT NULL, view InventoryView NOT NULL, viewers List NOT NULL);");
	}
}
