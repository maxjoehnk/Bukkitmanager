package org.efreak1996.Bukkitmanager.Logger.Block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBurnEvent;


public class BlockBurnLogger extends BlockLogger {
	
	public BlockBurnLogger() {
		super("BlockBurn");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBurn(BlockBurnEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new BlockBurnHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
