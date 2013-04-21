package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceLogger extends BlockLogger {
	
	public BlockPlaceLogger() {
		super("BlockPlace");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Cancelled", event.isCancelled());
		values.put("Player", event.getPlayer());
		values.put("BlockAgainst", event.getBlockAgainst());
		values.put("BlockPlaced", event.getBlockPlaced());
		values.put("BlockReplacedState", event.getBlockReplacedState());
		values.put("ItemInHand", event.getItemInHand());
		values.put("canBuild", event.canBuild());
		info(values);
	}
	
	@Override
	public void setupLogger() {
		initialize(new BlockPlaceHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
