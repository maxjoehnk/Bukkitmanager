package org.efreak1996.Bukkitmanager.Logger.Enchantment;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.efreak1996.Bukkitmanager.Logger.LoggerConfiguration;


public class EnchantItemHandler extends EnchantmentHandler {

	public EnchantItemHandler(EnchantItemLogger arg1logger) {
		super(new File("Enchantment" + File.separator + "EnchantItem.log"), arg1logger, LoggerConfiguration.get("Enchantment.EnchantItem.File"), LoggerConfiguration.get("Enchantment.EnchantItem.Database"));
	}
	
	public String logFile(HashMap<String, Object> values) {
		String msg = io.translate("Logger.Enchantment.EnchantItem").replaceAll("%eventname%", (String) values.get("EventName"));
		return msg;
	}

	@Override
	public void logDb(HashMap<String, Object> values) {
		//dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Log_EnchantItemEvent (time String NOT NULL, enchantBlock Block NOT NULL, cancelled Boolean NOT NULL, enchanter Player NOT NULL, enchantmentsToAdd Map NOT NULL, expLevelCost Integer NOT NULL, inventory Inventory NOT NULL, item ItemStack NOT NULL, view InventoryView NOT NULL, viewers List NOT NULL, button Integer NOT NULL);");
		db.log("Log_EnchantItemEvent", "time, enchantBlock, cancelled, enchanter, enchantmentsToAdd, expLevelCost, inventory, item, view, viewers, button", "'" + new Date().toGMTString() +  "', '" + (Block) values.get("Block") + "', '" + (Boolean) values.get("Cancelled") + "'");		
	}
}
