package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;


public class BlockBreakLogger extends BlockLogger {
	
	public BlockBreakLogger() {
		super("BlockBreak");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("Player", event.getPlayer());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new BlockBreakHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
