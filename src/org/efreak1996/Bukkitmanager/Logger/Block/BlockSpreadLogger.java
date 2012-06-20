package org.efreak1996.Bukkitmanager.Logger.Block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockSpreadLogger extends BmBlockLogger {
	
	public BlockSpreadLogger() {
		super("BlockSpread");
	}	

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockSpread(BlockSpreadEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("NewState", event.getNewState());
		values.put("Source", event.getSource());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new BlockSpreadHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
