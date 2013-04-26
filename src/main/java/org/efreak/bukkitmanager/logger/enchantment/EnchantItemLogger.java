package org.efreak.bukkitmanager.logger.enchantment;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantItemLogger extends EnchantmentLogger {
	
	public EnchantItemLogger() {
		super("EnchantItem");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEnchantItem(EnchantItemEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Cancelled", event.isCancelled());
		values.put("EnchantBlock", event.getEnchantBlock());
		values.put("Enchanter", event.getEnchanter());
		values.put("EnchantsToAdd", event.getEnchantsToAdd());
		values.put("ExpLevelCost", event.getExpLevelCost());
		values.put("Inventory", event.getInventory());
		values.put("Item", event.getItem());
		values.put("View", event.getView());
		values.put("Viewers", event.getViewers());
		values.put("Button", event.whichButton());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new EnchantItemHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
