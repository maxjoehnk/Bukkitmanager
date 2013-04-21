package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockGrowEvent;

public class BlockGrowLogger extends BlockLogger {
	
	public BlockGrowLogger() {
		super("BlockGrow");
	}	

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockGrow(BlockGrowEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("NewState", event.getNewState());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new BlockGrowHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
