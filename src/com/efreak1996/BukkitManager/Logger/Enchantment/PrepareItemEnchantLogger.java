package com.efreak1996.BukkitManager.Logger.Enchantment;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class PrepareItemEnchantLogger extends BmEnchantmentLogger {
	
	public PrepareItemEnchantLogger() {
		super("PrepareItemEnchant");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Cancelled", event.isCancelled());
		values.put("EnchantBlock", event.getEnchantBlock());
		values.put("Enchanter", event.getEnchanter());
		values.put("EnchantmentBonus", event.getEnchantmentBonus());
		values.put("ExpLevelCostsOffered", event.getExpLevelCostsOffered());
		values.put("Inventory", event.getInventory());
		values.put("Item", event.getItem());
		values.put("View", event.getView());
		values.put("Viewers", event.getViewers());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new PrepareItemEnchantHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
