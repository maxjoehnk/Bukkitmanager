package org.efreak1996.Bukkitmanager.Logger.Block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormLogger extends BmBlockLogger {
	
	public BlockFormLogger() {
		super("BlockForm");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockForm(BlockFormEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("NewState", event.getNewState());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new BlockFormHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
