package org.efreak.bukkitmanager.logger.block;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockFadeLogger extends BlockLogger {

    public BlockFadeLogger() {
        super("BlockFade");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockFade(BlockFadeEvent event) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("EventName", event.getEventName());
        values.put("Block", event.getBlock());
        values.put("Cancelled", event.isCancelled());
        values.put("NewState", event.getNewState());
        info(values);
    }

    @Override
    public void setupLogger() {
        initialize(new BlockFadeHandler(this));
        fileLogging = handler.fileLogging;
        dbLogging = handler.dbLogging;
    }
}
