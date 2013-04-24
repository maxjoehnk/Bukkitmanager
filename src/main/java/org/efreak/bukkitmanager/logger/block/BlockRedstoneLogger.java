package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstoneLogger extends BlockLogger {

    public BlockRedstoneLogger() {
        super("BlockRedstone");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockRedstone(BlockRedstoneEvent event) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("EventName", event.getEventName());
        values.put("Block", event.getBlock());
        values.put("NewCurrent", event.getNewCurrent());
        values.put("OldCurrent", event.getOldCurrent());
        info(values);
    }

    @Override
    public void setupLogger() {
        initialize(new BlockRedstoneHandler(this));
        fileLogging = handler.fileLogging;
        dbLogging = handler.dbLogging;
    }
}
