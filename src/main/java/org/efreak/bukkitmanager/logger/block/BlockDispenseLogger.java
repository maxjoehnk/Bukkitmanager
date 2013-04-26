package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDispenseEvent;

public class BlockDispenseLogger extends BlockLogger {
	
	public BlockDispenseLogger() {
		super("BlockDispense");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockDispense(BlockDispenseEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("Item", event.getItem());
		values.put("Velocity", event.getVelocity());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new BlockDispenseHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
