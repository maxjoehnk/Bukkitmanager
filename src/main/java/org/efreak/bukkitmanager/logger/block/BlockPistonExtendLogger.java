package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class BlockPistonExtendLogger extends BlockLogger {
	
	public BlockPistonExtendLogger() {
		super("BlockPistonExtend");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("Blocks", event.getBlocks());
		values.put("Direction", event.getDirection());
		values.put("Length", event.getLength());
		values.put("Sticky", event.isSticky());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new BlockPistonExtendHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
