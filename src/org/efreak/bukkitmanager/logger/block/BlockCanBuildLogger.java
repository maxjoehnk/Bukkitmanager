package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockCanBuildEvent;


public class BlockCanBuildLogger extends BlockLogger {
	
	public BlockCanBuildLogger() {
		super("BlockCanBuild");
	}	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockCanBuild(BlockCanBuildEvent event) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("EventName", event.getEventName());
		values.put("Block", event.getBlock());
		values.put("Material", event.getMaterial());
		values.put("MaterialId", event.getMaterialId());
		values.put("Buildable", event.isBuildable());
		info(values);
	}

	@Override
	public void setupLogger() {
		initialize(new BlockCanBuildHandler(this));
		fileLogging = handler.fileLogging;
		dbLogging = handler.dbLogging;
	}
}
