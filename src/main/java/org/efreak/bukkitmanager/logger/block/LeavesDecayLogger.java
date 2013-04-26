package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayLogger extends BlockLogger {
	
	public LeavesDecayLogger() {
		super("LeavesDecay");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeavesDecay(LeavesDecayEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new LeavesDecayHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
