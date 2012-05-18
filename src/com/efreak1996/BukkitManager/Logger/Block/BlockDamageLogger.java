package com.efreak1996.BukkitManager.Logger.Block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamageLogger extends BmBlockLogger {
	
	public BlockDamageLogger() {
		super("BlockDamage");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockDamage(BlockDamageEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("InstaBreak", event.getInstaBreak());
		values.put("ItemInHand", event.getItemInHand());
		values.put("Player", event.getPlayer());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new BlockDamageHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
