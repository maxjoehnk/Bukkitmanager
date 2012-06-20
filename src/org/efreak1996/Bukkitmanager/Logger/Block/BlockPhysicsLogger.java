package org.efreak1996.Bukkitmanager.Logger.Block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysicsLogger extends BmBlockLogger {
	
	public BlockPhysicsLogger() {
		super("BlockPhysics");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPhysics(BlockPhysicsEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("ChangedType", event.getChangedType());
		values.put("ChangedTypeId", event.getChangedTypeId());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new BlockPhysicsHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
