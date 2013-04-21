package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToLogger extends BlockLogger {
	
	public BlockFromToLogger() {
		super("BlockFromTo");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockFromTo(BlockFromToEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("Face", event.getFace());
		values.put("ToBlock", event.getToBlock());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new BlockFromToHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
