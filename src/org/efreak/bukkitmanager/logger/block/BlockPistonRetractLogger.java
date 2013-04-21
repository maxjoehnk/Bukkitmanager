package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class BlockPistonRetractLogger extends BlockLogger {
	
	public BlockPistonRetractLogger() {
		super("BlockPistonRetract");
	}	

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("RetractLocation", event.getRetractLocation());
		values.put("Sticky", event.isSticky());
		values.put("Direction", event.getDirection());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new BlockPistonRetractHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
